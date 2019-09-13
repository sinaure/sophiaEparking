package com.sinaure.test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sinaure.Application;
import com.sinaure.config.model.Parking;
import com.sinaure.repository.LogRepository;
import com.sinaure.repository.ParkingRepository;
import com.sinaure.service.ParkingService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ParkingIntegTest {
	
	@Autowired
	private LogRepository logRepository;
	@Autowired
	private ParkingRepository parkingRepository;
	@Autowired
	private ParkingService parkingService;
	

	@Before
	public void setUp() {
		Parking parking = new Parking();
		parking.setId("ticketless parking");
		parking.setParking_name("saint philippe");
		parkingRepository.save(parking);
	}

	@Test
	public void parkMyCar() {
		Parking parking = parkingRepository.findById("ticketless parking").orElse(null);
		assertNotNull(parking);
		Parking parking1 = parkingRepository.findById("Place Massena parking").orElse(null);
		assertNull(parking1);
	}
	
	
	
	
}
