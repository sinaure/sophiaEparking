package com.sinaure.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinaure.config.model.Client;
import com.sinaure.config.model.Parking;
import com.sinaure.config.model.Rule;
import com.sinaure.config.model.Slot;
import com.sinaure.repository.ParkingRepository;
import com.sinaure.service.ParkingService;

@RestController
@RequestMapping("parking")
public class ParkingController {
		@Autowired
	    private ParkingRepository parkingRepository;
		@Autowired
	    private ParkingService parkingService;
		@GetMapping("")
	    public ResponseEntity<List<Parking>> getParkingList() {
			return new ResponseEntity<List<Parking>>(
	        		parkingRepository.findAll(), 
	        	      HttpStatus.OK);
	    }
		@GetMapping("/{parkingId}")
	    public Parking getParkingListById(@PathVariable Long parkingId) {
	        return parkingRepository.findById(parkingId).orElse(null);
	    }
	    @PostMapping("/{parkingId}/checkavailability")
	    public ResponseEntity<List<Slot>> checkAvailability(@RequestBody Client client, @PathVariable Long parkingId) {	        
	    	List<Slot> slots = parkingService.getAvailableSlotsFor(client, parkingRepository.findById(parkingId).orElse(null));
	    	if(slots.size()==0) {
	    		return new ResponseEntity<List<Slot>>(
		        		slots, 
		        	      HttpStatus.EXPECTATION_FAILED);
	    	}
	    	return new ResponseEntity<List<Slot>>(
	        		slots, 
	        	      HttpStatus.OK);
	    }
	    @PostMapping("/{parkingId}/updateRule")
	    public ResponseEntity<Parking> updateRule(@RequestBody Rule client, @PathVariable Long parkingId) {	
	    	return new ResponseEntity<Parking>(
	    			parkingRepository.save(parkingRepository.findById(parkingId).orElse(null)), 
	        	      HttpStatus.OK);
	    }  
	    
	    @PostMapping("/{parkingId}/bill")
	    public ResponseEntity<String> billClient(@RequestBody Client client, @PathVariable Long parkingId) {	
	    	return new ResponseEntity<String>(
	    			"Hey dude the bill is "+parkingService.calculateFee(client, parkingRepository.findById(parkingId).orElse(null)).toPlainString()+ " Au revoir!", 
	        	      HttpStatus.OK);
	    }  
	    @PostMapping("/{parkingId}/park")
	    public ResponseEntity<String> parkClient(@RequestBody Client client, @PathVariable Long parkingId) {	
	    	if(parkingService.getAvailableSlotsFor(client, parkingRepository.findById(parkingId).orElse(null)).size()==0) {
	    		return new ResponseEntity<String>(
		    			"no parking slots availables", 
		        	      HttpStatus.EXPECTATION_FAILED);
	    	}
	    	if(parkingService.park(client, parkingRepository.findById(parkingId).orElse(null))) {
	    		return new ResponseEntity<String>(
		    			"OK", 
		        	      HttpStatus.OK);
	    	}
	    	return new ResponseEntity<String>(
	    			"no parking slots availables", 
	        	      HttpStatus.EXPECTATION_FAILED);
	    	
	    }  
	    
}

