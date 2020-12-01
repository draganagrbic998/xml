package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Obavestenje", namespace = "https://github.com/draganagrbic998/xml/obavestenje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "sluzbenik", "zahtev", "podaci" })
public class Obavestenje {
	
	@XmlAttribute(name = "broj", required = true)
	private String broj;
	
	@XmlAttribute(name = "datum", required = true)
	private Date datum;
	
	@XmlElement(name = "Korisnik", namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private Korisnik sluzbenik;
	
	@XmlElement(name = "Zahtev", namespace = "https://github.com/draganagrbic998/xml/zahtev", required = true)
	private Zahtev zahtev;
	
	@XmlElement(name = "Podaci_obavestenja", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private PodaciObavestenja podaci;
	
	public Obavestenje() {
		super();
	}

	public Obavestenje(String broj, Date datum, Korisnik sluzbenik, Zahtev zahtev, PodaciObavestenja podaci) {
		this.broj = broj;
		this.datum = datum;
		this.sluzbenik = sluzbenik;
		this.zahtev = zahtev;
		this.podaci = podaci;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI OBAVESTENJA:\n";
		suma += String.format("broj: %s, datum: %s\n", this.broj, format.format(this.datum));
		suma += this.sluzbenik.toString();
		suma += this.zahtev.toString();
		suma += this.podaci.toString();
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

	public PodaciObavestenja getPodaci() {
		return podaci;
	}

	public void setPodaci(PodaciObavestenja podaci) {
		this.podaci = podaci;
	}

}
