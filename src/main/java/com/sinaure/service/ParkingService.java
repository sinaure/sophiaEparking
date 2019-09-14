package com.sinaure.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinaure.config.model.Client;
import com.sinaure.config.model.Log;
import com.sinaure.config.model.Parking;
import com.sinaure.config.model.Rule;
import com.sinaure.config.model.Slot;
import com.sinaure.repository.LogRepository;
import com.sinaure.repository.ParkingRepository;

@Component
public class ParkingService {
	
	public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));  

	@Autowired
    private LogRepository logRepository;
	
	@Autowired
    private ParkingRepository parkingRepository;

	public Boolean availableSlotFor(Client client, Parking parking) {
		Slot s = parking.getSlots().stream().filter(
				slot ->	client.getCarType().name().equals(slot.getSlot_type()) && slot.isAvailable()
		).findAny().orElse(null);
		return s == null ?  false :  true;
	}
	
	public Slot getOccupiedSlotByClient(Client client, Parking parking) {
		return parking.getSlots().stream().filter(slot -> client.getPlaque().equals(slot.getPlaque())).findAny().orElse(null);
	}
	
	public List<Slot> getAvailableSlotsFor(Client client, Parking parking) {
		return parking.getSlots().stream().filter(slot -> client.getCarType().name().equals(slot.getSlot_type()) && slot.isAvailable()).collect(Collectors.toList());
	}
	
	public Boolean canPark(Client client, Slot slot) {
		return slot.isAvailable() && slot.getSlot_type().equalsIgnoreCase(client.getCarType().name())  ?  true :  false;
	}
	public void parkToSlot(Slot s, Client client) {
		s.setAvailable(false);
		s.setPlaque(client.getPlaque());
		
		// write to logs
		Log log = new Log();
		log.setPlaque(client.getPlaque());
		LocalDateTime now = LocalDateTime.now();
		log.setStartAt(Timestamp.valueOf(now));
		log.setParking(s.getParking());
		logRepository.save(log);
	}
	public void updateParkingSpotsReferential(Parking parking, Slot s) {
		List<Slot> slots = parking.getSlots().stream().filter(slot -> s.getId().equals(slot.getId())).map(slot -> slot = s ).collect(Collectors.toList());
		parking.setSlots(slots);
		parkingRepository.save(parking);
	}
	public Boolean park(Client client, Parking parking) {
		if(!availableSlotFor(client,parking)) {
			System.out.println("There are not availables parking slots for "+ client.getCarType().name());
			return false;
		}
		//if a car already parked return 
		if(parking.getSlots().stream().anyMatch(s -> s.getPlaque()!= null && s.getPlaque().equalsIgnoreCase(client.getPlaque()))) {
			System.out.println("a car with this plate has already been parked");
			return false;
		}
		// find a slot in parking mark as occupied and write the plaque 
		Slot s = parking.getSlots().stream().filter(slot -> slot.getSlot_type().equalsIgnoreCase(client.getCarType().name()) && slot.isAvailable()).findFirst().orElse(null);
		if (s == null) 
				return false;
		parkToSlot(s, client);
		updateParkingSpotsReferential(parking, s);
		return true;
	}
	
	public BigDecimal calculateFee(Client client, Parking parking) {
		Log log = logRepository.findByPlaque(client.getPlaque()).stream().filter(l -> l.getParking().getParking_name().equalsIgnoreCase(parking.getParking_name())).collect(Collectors.toList()).iterator().next();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime parkingDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(log.getStartAt().getTime()), ZoneOffset.UTC)  ;
	    long hours = ChronoUnit.HOURS.between(parkingDate, now);
	    Slot s = getOccupiedSlotByClient(client,parking);
		return s.getRule().getFix().add(s.getRule().getVariable().multiply(new BigDecimal(hours)));
	}
	
	public Parking updateRule(Rule rule, Long parkingId) {
		return parkingRepository.findById(parkingId).map(parking -> {
			parking.getSlots().forEach(slot -> slot.setRule(rule));
            return parkingRepository.save(parking);
        }).orElse(null);
		//return parkingRepository.save(parking);
	}
	
}
