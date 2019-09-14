package com.sinaure.test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.sinaure.config.model.CarType;
import com.sinaure.config.model.Client;
import com.sinaure.config.model.Log;
import com.sinaure.config.model.Parking;
import com.sinaure.config.model.Rule;
import com.sinaure.config.model.Slot;
import com.sinaure.repository.LogRepository;
import com.sinaure.repository.ParkingRepository;
import com.sinaure.service.ParkingService;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ParkingServiceTest {
	
	@Mock
	private LogRepository logRepository;
	@Mock
	private ParkingRepository parkingRepository;
	@InjectMocks
	private ParkingService parkingService;
	
	Client clientStandard;
	Client clientElectric;
	Client clientElectricPowerful;
	Client willingToPayclient;

	@Before
	public void setUp() {
		clientStandard = new Client(CarType.standard, "AA5676YY"); // is parked at slot1
		clientElectric = new Client(CarType.electric20kw, "EE2090JJ"); // is parked at slot5
		clientElectricPowerful = new Client(CarType.electric50kw, "EE2090JJ");
		willingToPayclient = new Client(CarType.standard, "FF5676YY"); // is parked at slot6

		Parking parking = new Parking();
		parking.setParking_name("saint philippe");
		
		Rule rule = new Rule(new BigDecimal(2.5),new BigDecimal(1) );
		Rule expenciveRule = new Rule(new BigDecimal(5),new BigDecimal(3) );
		
		Slot slot1 = new Slot(CarType.standard, rule);
		slot1.setPlaque(clientStandard.getPlaque());
		parking.getSlots().add(slot1);
		
		Slot slot2 = new Slot(CarType.standard, rule);
		parking.getSlots().add(slot1);
		
		Slot slot3 = new Slot(CarType.standard, rule);
		parking.getSlots().add(slot1);
		
		Slot slot4 = new Slot(CarType.standard, rule);
		parking.getSlots().add(slot1);
		
		Slot slot5 = new Slot(CarType.electric20kw, rule);
		slot4.setPlaque(clientElectric.getPlaque());
		parking.getSlots().add(slot1);
		
		Slot slot6 = new Slot(CarType.standard, expenciveRule);
		slot6.setPlaque(willingToPayclient.getPlaque());
		parking.getSlots().add(slot6);
		
		parking.getSlots().add(slot1);
		parking.getSlots().add(slot2);
		parking.getSlots().add(slot3);
		parking.getSlots().add(slot4);
		parking.getSlots().add(slot5);
		parking.getSlots().add(slot6);
		
		//client1 stay 6hours
		Log logStandardCarCient1 = new Log();
		logStandardCarCient1.setPlaque(clientStandard.getPlaque());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime h6before = now.minusHours(6);
		logStandardCarCient1.setStartAt(Timestamp.valueOf(h6before));
		logStandardCarCient1.setEndAt(Timestamp.valueOf(now));
		logStandardCarCient1.setParking(parking);
		List<Log> loglist = new ArrayList<Log>();
		loglist.add(logStandardCarCient1);
		
		//client2 stay 3hours
		Log logElectricCarCient2 = new Log();
		logElectricCarCient2.setPlaque(clientElectric.getPlaque());
		LocalDateTime h3before = now.minusHours(3);
		logElectricCarCient2.setStartAt(Timestamp.valueOf(h3before));
		logElectricCarCient2.setEndAt(Timestamp.valueOf(now));
		logElectricCarCient2.setParking(parking);
		List<Log> loglist2 = new ArrayList<Log>();
		loglist2.add(logElectricCarCient2);
		
		//also client3 stay 3 hours but his slot use a different rule 
		Log logElectricCarCient3 = new Log();
		logElectricCarCient3.setPlaque(willingToPayclient.getPlaque());
		logElectricCarCient3.setStartAt(Timestamp.valueOf(h3before));
		logElectricCarCient3.setEndAt(Timestamp.valueOf(now));
		logElectricCarCient3.setParking(parking);
		List<Log> loglist3 = new ArrayList<Log>();
		loglist3.add(logElectricCarCient3);

		Mockito.when(logRepository.findByPlaque(clientStandard.getPlaque())).thenReturn(loglist);
		Mockito.when(logRepository.findByPlaque(clientElectric.getPlaque())).thenReturn(loglist2);
		Mockito.when(logRepository.findByPlaque(willingToPayclient.getPlaque())).thenReturn(loglist3);
		Mockito.when(parkingRepository.findById(new Long(1))).thenReturn(Optional.of(parking));
		
		Mockito.when(parkingRepository.save(Mockito.any())).thenReturn(new Log());

	}

	@Test
	public void standardClientCanPark() {
		Parking parking = parkingRepository.findById(new Long(1)).orElse(null);
		assertTrue(parkingService.availableSlotFor(clientStandard, parking));
	}
	@Test
	public void powerfulElectricCarsCanNotPark() {
		Parking parking = parkingRepository.findById(new Long(1)).orElse(null);
		assertFalse(parkingService.availableSlotFor(clientElectricPowerful, parking));
	}
	@Test
	public void checkThatFeeOfaCarThatStayLongerIsHigher() {
		Parking parking = parkingRepository.findById(new Long(1)).orElse(null);
		BigDecimal fee1 = parkingService.calculateFee(clientStandard, parking);
		BigDecimal fee2 = parkingService.calculateFee(clientElectric, parking);
		//Fee of client1 will be higher than client2 because stay the double amount of time
		assertTrue(fee1.compareTo(fee2) == 1);
	}
	
	@Test
	public void checkThatIfRuleIsDifferentAlsoFeeIs() {
		Parking parking = parkingRepository.findById(new Long(1)).orElse(null);
		BigDecimal fee1 = parkingService.calculateFee(willingToPayclient, parking);
		BigDecimal fee2 = parkingService.calculateFee(clientElectric, parking);
		//Fee of client3 will be higher than client2 even if they stay 3h both
		assertTrue(fee1.compareTo(fee2) == 1);
	}
	
	@Test
	public void checkCanParkVehicleInSpot() {
		Client cli = new Client(CarType.electric20kw, "ZZ5676YY"); // is parked at slot1
		Slot mySlot = new Slot(CarType.electric20kw);
		Slot mySlot1 = new Slot(CarType.electric50kw);
		assertTrue(this.parkingService.canPark(cli, mySlot));
		assertFalse(this.parkingService.canPark(cli, mySlot1));
	}
	
	@Test
	public void checkCantParkIfSpotUnavailable() {
		Client cli = new Client(CarType.electric20kw, "ZZ5676YY"); // is parked at slot1
		Slot mySlot = new Slot(CarType.electric20kw);
		mySlot.setAvailable(false);
		assertFalse(this.parkingService.canPark(cli, mySlot));
	}
	
	
}
