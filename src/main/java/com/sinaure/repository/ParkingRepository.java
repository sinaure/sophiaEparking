package com.sinaure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sinaure.config.model.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
	@Query("SELECT p FROM Parking p WHERE p.parking_name = ?1")
	Parking findByName(String name);
}