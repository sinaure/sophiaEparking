package com.sinaure.config.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "parking")
public class Parking {
 
    @Id
    private String id;
     
    @Column(nullable = false)
    private String parking_name;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParking_name() {
		return parking_name;
	}

	public void setParking_name(String parking_name) {
		this.parking_name = parking_name;
	}
 

}