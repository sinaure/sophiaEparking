package com.sinaure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinaure.config.model.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {

}