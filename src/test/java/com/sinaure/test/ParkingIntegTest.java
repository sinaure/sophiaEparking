package com.sinaure.test;


import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sinaure.Application;
import com.sinaure.config.model.CarType;
import com.sinaure.config.model.Client;
import com.sinaure.config.model.Parking;
import com.sinaure.config.model.Rule;
import com.sinaure.config.model.Slot;
import com.sinaure.repository.LogRepository;
import com.sinaure.repository.ParkingRepository;
import com.sinaure.repository.RuleRepository;
import com.sinaure.repository.SlotRepository;
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
	private RuleRepository ruleRepository;
	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private ParkingService parkingService;
	
	@Before
	public void initData(){
		parkingRepository.deleteAll();
		
		Parking parking = new Parking();
		parking.setParking_name("saint philippe");
		parkingRepository.save(parking);
		
		Parking parking1 = new Parking();
		parking1.setParking_name("place massena");
		parkingRepository.save(parking1);
		
		Rule r = new Rule(new BigDecimal(2),new BigDecimal(1));
		Slot s = new Slot(CarType.standard,  r);
		s.setParking(parking);
		s = slotRepository.save(s);
		
		Rule r1 = new Rule(new BigDecimal(3),new BigDecimal(2));
		Slot s1 = new Slot(CarType.standard,  r1);
		s1.setParking(parking1);
		s1 = slotRepository.save(s1);
	}

	@Test
	public void checkMyParkingCreated() {
		assertNotNull(parkingRepository.findAll());
		assertNotNull(parkingRepository.findByName("saint philippe"));
	}
	
	@Test
	public void checkRuleUpdated() {
		Parking parking = parkingRepository.findByName("saint philippe");
		Rule r1 = new Rule(new BigDecimal(2),new BigDecimal(1));		
		parkingService.updateRule(r1, parking.getId());
		Parking parkingUpdated = parkingRepository.findByName("saint philippe");
		assertNotEquals(parkingUpdated.getSlots().iterator().next().getRule(), parking.getSlots().iterator().next().getRule());
	}
	@Test
	public void billClient() {
		//two parkings with different rules but same client
		Client cli = new Client(CarType.standard, "ZZ5676YY");
		Parking parkingSophia = parkingRepository.findByName("saint philippe");
		Parking parkingMassena = parkingRepository.findByName("place massena");
		
		
		Rule r = new Rule(new BigDecimal(4),new BigDecimal(1));
		Rule r1 = new Rule(new BigDecimal(4),new BigDecimal(8));
		
		Slot s = new Slot(CarType.standard,  r);
		s.setParking(parkingSophia);
		s = slotRepository.save(s);
		
		Slot s1 = new Slot(CarType.standard,  r1);
		s1.setParking(parkingMassena);
		s1 = slotRepository.save(s1);
		
		parkingService.park(cli, parkingSophia);
		
		BigDecimal fee = parkingService.calculateFee(cli, parkingSophia);
		
		parkingService.park(cli, parkingMassena);
		
		BigDecimal fee1 = parkingService.calculateFee(cli, parkingMassena);
		
		assertNotEquals(fee, fee1);
	}
	
	
}
