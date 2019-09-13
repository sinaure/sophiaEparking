package com.sinaure.test;


import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.sinaure.config.model.CarType;
import com.sinaure.config.model.Client;
import com.sinaure.config.model.Log;
import com.sinaure.config.model.Parking;
import com.sinaure.config.model.Slot;
import com.sinaure.repository.LogRepository;
import com.sinaure.repository.ParkingRepository;
import com.sinaure.service.ParkingService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingServiceTest {
	
	@Mock
	private LogRepository logRepository;
	@Mock
	private ParkingRepository parkingRepository;
	@Autowired
	private ParkingService parkingService;
	
	Client clientStandard;
	Client clientElectric;
	Client clientElectricPowerful;
	

	@Before
	public void setUp() {
		clientStandard = new Client(CarType.standard, "AA5676YY");
		clientElectric = new Client(CarType.electric20kw, "EE2090JJ");
		clientElectricPowerful = new Client(CarType.electric50kw, "EE2090JJ");

		Parking parking = new Parking();
		parking.setId("ticketless parking");
		parking.setParking_name("saint philippe");
		Slot slot1 = new Slot();
		slot1.setSlot_type(CarType.standard.toString());
		slot1.setParking(parking);
		Slot slot2 = new Slot();
		slot2.setSlot_type(CarType.standard.toString());
		slot2.setParking(parking);
		Slot slot3 = new Slot();
		slot3.setSlot_type(CarType.standard.toString());
		slot3.setParking(parking);
		Slot slot4 = new Slot();
		slot4.setSlot_type(CarType.standard.toString());
		slot4.setParking(parking);
		Slot slot5 = new Slot();
		slot5.setSlot_type(CarType.electric20kw.toString());
		slot5.setParking(parking);
		parking.getSlots().add(slot1);
		parking.getSlots().add(slot2);
		parking.getSlots().add(slot3);
		parking.getSlots().add(slot4);
		parking.getSlots().add(slot5);

		Log logElectricCarCient = new Log();
		logElectricCarCient.setPlaque(clientElectric.getPlaque());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime before = now.minusHours(6);
		logElectricCarCient.setStartAt(Timestamp.valueOf(before));
		logElectricCarCient.setEndAt(Timestamp.valueOf(now));

		Mockito.when(logRepository.findByPlaque(clientElectric.getPlaque())).thenReturn(logElectricCarCient);
		Mockito.when(logRepository.findByPlaque(clientStandard.getPlaque())).thenReturn(logElectricCarCient);
		Mockito.when(parkingRepository.findById("ticketless parking").orElse(null)).thenReturn(parking);

	}

	

	@Test
	public void clientCanPark() {
		Parking parking = parkingRepository.findById("ticketless parking").orElse(null);
		assertTrue(parkingService.availableSlotFor(clientStandard, parking));
	}
	@Test
	public void clientCanNotPark() {
		Parking parking = parkingRepository.findById("ticketless parking").orElse(null);
		assertTrue(parkingService.availableSlotFor(clientElectricPowerful, parking));
	}
}
