package com.sinaure.config.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rule")
public class Rule {
	@Id
    private String id;
     
    @Column
    private BigDecimal fix;
    
    @Column
    private BigDecimal variable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
