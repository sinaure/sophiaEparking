package com.sinaure.config.model;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "slot")
public class Slot {
	public Slot() {
		this.id = new Random().nextLong();
		this.available = true;
	}
	public Slot(CarType carType) {
		this.id = new Random().nextLong();
		this.available = true;
		this.slot_type = carType.toString();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column
    private String slot_type;
    
    @Column(columnDefinition = "boolean default true")
    private Boolean available;
    
    @Column
    private String plaque;
    
    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;
    
    @ManyToOne
    @JoinColumn(name = "parking_code")
    private Parking parking;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSlot_type() {
		return slot_type;
	}

	public void setSlot_type(String slot_type) {
		this.slot_type = slot_type;
	}

	public Boolean isAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getPlaque() {
		return plaque;
	}

	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}


    
    
    
    
    
    
    
}
