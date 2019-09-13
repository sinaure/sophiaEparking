package com.sinaure.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinaure.config.model.Client;
import com.sinaure.config.model.Log;
import com.sinaure.config.model.Parking;
import com.sinaure.config.model.Slot;
import com.sinaure.repository.LogRepository;

@Component
public class ParkingService {
	
	public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));  

	@Autowired
    private LogRepository logRepository;

	public Boolean availableSlotFor(Client client, Parking parking) {
		Slot s = parking.getSlots().stream().filter(slot -> client.getCarType().toString().equals(slot.getSlot_type()) && slot.isAvailable()).findAny().orElse(null);
		return s == null ?  false :  true;
	}
	
	public Slot getSlotFor(Client client, Parking parking) {
		return parking.getSlots().stream().filter(slot -> client.getPlaque().equals(slot.getPlaque())).findAny().orElse(null);
	}
	
	public Boolean canPark(Client client, Slot slot) {
		return slot.isAvailable() && slot.getSlot_type().equalsIgnoreCase(client.getCarType().toString())  ?  true :  false;
	}
	
	public BigDecimal calculateFee(Client client, Parking parking) {
		Log log = logRepository.findByPlaque(client.getPlaque());
		LocalDate now = LocalDate.now();
	    LocalDate parkingDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(log.getStartAt().getTime()), ZoneOffset.UTC).toLocalDate()  ;
	    long hours = ChronoUnit.HOURS.between(parkingDate, now);
	    Slot s = getSlotFor(client,parking);
		return s.getRule().getFix().add(s.getRule().getVariable().multiply(new BigDecimal(hours)));
	}
	
}
