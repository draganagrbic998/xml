package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.common.Namespaces;

@XmlRootElement(name = "Kopija", namespace = Namespaces.ODLUKA)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "iznos", "brojRacuna", "pozivNaBroj", "model"})
public class Kopija {

	@XmlElement(namespace = Namespaces.ODLUKA, required = true)
	private String iznos;
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "racun")
	private String brojRacuna;
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "poziv_na_broj")
	private String pozivNaBroj;
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true)
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
