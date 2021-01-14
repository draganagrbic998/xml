package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pretraga")
@XmlType(propOrder = { "operacija", "datum", "mesto", "mestoIzdavanja", "organVlasti", "tip", "stanje" })
public class ZalbaPretraga implements Pretraga {

	@XmlElement(required = true)
	private String operacija;
	
	@XmlElement(required = true)
	private String datum;
	
	@XmlElement(required = true)
	private String mesto;
	
	@XmlElement(required = true)
	private String mestoIzdavanja;
	
	@XmlElement(required = true)
	private String organVlasti;
	
	@XmlElement(required = true)
	private String tip;
	
	@XmlElement(required = true)
	private String stanje;

	public ZalbaPretraga() {
		super();
	}

	public String getOperacija() {
		return operacija;
	}

	public void setOperacija(String operacija) {
		this.operacija = operacija;
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

	public String getMestoIzdavanja() {
		return mestoIzdavanja;
	}

	public void setMestoIzdavanja(String mestoIzdavanja) {
		this.mestoIzdavanja = mestoIzdavanja;
	}

	public String getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(String organVlasti) {
		this.organVlasti = organVlasti;
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
