package com.sinaure.config.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
     
    @Column
    private Timestamp startAt;
    
    @Column
    private Timestamp endAt;
    
    @Column
    private String plaque;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
    


    
    
    
    
    
    
    
}
