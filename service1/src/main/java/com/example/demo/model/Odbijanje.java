package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import jaxb.DateAdapter;

@XmlRootElement(name = "Odbijanje", namespace = "https://github.com/draganagrbic998/xml/odbijanje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "sluzbenik", "zahtev", "obrazlozenje" })
public class Odbijanje {
	
	@XmlAttribute(name = "broj", namespace = "https://github.com/draganagrbic998/xml/odbijanje", required = true)
	private String broj;
	
	@XmlAttribute(name = "datum", namespace = "https://github.com/draganagrbic998/xml/odbijanje", required = true)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date datum;
	
	@XmlElement(name = "Korisnik", namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private Korisnik sluzbenik;
	
	@XmlElement(name = "Zahtev", namespace = "https://github.com/draganagrbic998/xml/zahtev", required = true)
	private Zahtev zahtev;
	
	@XmlElement(name = "Obrazlozenje", namespace = "https://github.com/draganagrbic998/xml/odbijanje", required = true)
	private String obrazlozenje;
	
	public Odbijanje() {
		super();
	}

	public Odbijanje(String broj, Date datum, Korisnik sluzbenik, Zahtev zahtev, String obrazlozenje) {
		this.broj = broj;
		this.datum = datum;
		this.sluzbenik = sluzbenik;
		this.zahtev = zahtev;
		this.obrazlozenje = obrazlozenje;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ODBIJANJA:\n";
		suma += String.format("broj: %s, datum: %s\n", this.broj, format.format(this.datum));
		suma += this.sluzbenik.toString();
		suma += this.zahtev.toString();
		suma += this.obrazlozenje;
		return suma;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Korisnik getSluzbenik() {
		return sluzbenik;
	}

	public void setSluzbenik(Korisnik sluzbenik) {
		this.sluzbenik = sluzbenik;
	}

	public Zahtev getZahtev() {
		return zahtev;
	}

	public void setZahtev(Zahtev zahtev) {
		this.zahtev = zahtev;
	}
	
	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setPodaci(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

}
