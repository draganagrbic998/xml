package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Kopija", namespace = "https://github.com/draganagrbic998/xml/obavestenje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "iznos", "brojRacuna", "pozivNaBroj", "model"})
public class Kopija {

	@XmlElement(name = "iznos", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private String iznos;
	
	@XmlElement(name = "racun", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private String brojRacuna;
	
	@XmlElement(name = "poziv_na_broj", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private String pozivNaBroj;
	
	@XmlElement(name = "model", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private String model;
	
	public Kopija() {
		super();
	}

	public Kopija(String iznos, String brojRacuna, String pozivNaBroj, String model) {
		super();
		this.iznos = iznos;
		this.brojRacuna = brojRacuna;
		this.pozivNaBroj = pozivNaBroj;
		this.model = model;
	}
	
	@Override
	public String toString() {
		String suma = "PODACI KOPIJE:\n";
		suma += String.format("iznos: %s, racun: %s, poziv na broj: %s, model: %s\n", this.iznos, this.brojRacuna, this.pozivNaBroj, this.model);
		return suma;
	}

	public String getIznos() {
		return iznos;
	}

	public void setIznos(String iznos) {
		this.iznos = iznos;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public String getPozivNaBroj() {
		return pozivNaBroj;
	}

	public void setPozivNaBroj(String pozivNaBroj) {
		this.pozivNaBroj = pozivNaBroj;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}
