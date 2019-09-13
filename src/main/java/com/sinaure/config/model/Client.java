package com.sinaure.config.model;

import java.nio.charset.Charset;
import java.util.Random;

public class Client {
	public Client(){
		this.carType = CarType.standard;
		this.plaque = generateRandomString();
	}
	public Client(CarType carType, String plaque){
		this.carType = carType;
		this.plaque = plaque;
	}
	private CarType carType;
	private String plaque;
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	public String getPlaque() {
		return plaque;
	}
	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}
	
	private String generateRandomString() {
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	    return generatedString;		
	}
			

	
}
