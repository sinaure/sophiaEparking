package com.sinaure.config.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "log")
public class Log {
	public Log() {
		this.id = new Random().nextLong();
		LocalDateTime now = LocalDateTime.now();
		this.startAt = Timestamp.valueOf(now);
	}
	public Log(String plaque) {
		this.id = new Random().nextLong();
		LocalDateTime now = LocalDateTime.now();
		this.startAt = Timestamp.valueOf(now);
		this.plaque = plaque;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column
    private Timestamp startAt;
    
    @Column
    private Timestamp endAt;
    
    @Column
    private String plaque;
    
    @ManyToOne
    @JoinColumn(name = "parking_code")
    private Parking parking;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getStartAt() {
		return startAt;
	}

	public void setStartAt(Timestamp startAt) {
		this.startAt = startAt;
	}

	public Timestamp getEndAt() {
		return endAt;
	}

	public void setEndAt(Timestamp endAt) {
		this.endAt = endAt;
	}

	public String getPlaque() {
		return plaque;
	}

	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}
    

	@JsonIgnore
	public Parking getParking() {
		return parking;
	}
	@JsonIgnore
	public void setParking(Parking parking) {
		this.parking = parking;
	}
    
    
    
    
    
    
    
}
