package com.sinaure.config.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parking")
public class Parking {
	public Parking() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String parking_name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParking_name() {
		return parking_name;
	}

	public void setParking_name(String parking_name) {
		this.parking_name = parking_name;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parking", cascade = CascadeType.ALL)
	private Collection<Slot> slots = new LinkedHashSet<Slot>();

	public Collection<Slot> getSlots() {
		return slots;
	}

	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}
	


}