package com.sinaure.config.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rule")
public class Rule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column
    private BigDecimal fix;
    
    @Column
    private BigDecimal variable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getFix() {
		return fix;
	}

	public void setFix(BigDecimal fix) {
		this.fix = fix;
	}

	public BigDecimal getVariable() {
		return variable;
	}

	public void setVariable(BigDecimal variable) {
		this.variable = variable;
	}
    
    
}
