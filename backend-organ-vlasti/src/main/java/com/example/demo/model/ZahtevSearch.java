package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pretraga")
@XmlType(propOrder = { "datum", "mesto", "tip", "stanje" })
public class ZahtevSearch {
	
	@XmlElement(required = true)
	private String datum;
	
	@XmlElement(required = true)
	private String mesto;
	
	@XmlElement(required = true)
	private String tip;
	
	@XmlElement(required = true)
	private String stanje;
	
	public ZahtevSearch() {
		super();
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getStanje() {
		return stanje;
	}

	public void setStanje(String stanje) {
		this.stanje = stanje;
	}

}
