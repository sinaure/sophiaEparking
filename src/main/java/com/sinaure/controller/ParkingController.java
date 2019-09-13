package com.sinaure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinaure.config.model.Parking;
import com.sinaure.repository.ParkingRepository;

@RestController
public class ParkingController {
		@Autowired
	    private ParkingRepository parkingRepository;
	    @RequestMapping("/")
	    public List<Parking> getParkingList() {
	        return parkingRepository.findAll();
	    }
	    @RequestMapping("/parkingId")
	    public Parking getParkingListById(@RequestParam String parkingId) {
	        return parkingRepository.findById(parkingId).orElse(null);
	    }
}

