package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Adresa", namespace = "https://github.com/draganagrbic998/xml/osnova")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "mesto", "postanskiBroj", "ulica", "broj" })
public class Adresa {
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/osnova", required = true)
	private String mesto;
	
	@XmlElement(name = "postanski_broj", namespace = "https://github.com/draganagrbic998/xml/osnova", required = true)
	private String postanskiBroj;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/osnova", required = true)
	private String ulica;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/osnova", required = true)
	private String broj;
	
	public Adresa() {
		super();
		System.out.println("Adresa created...");
	}

	public Adresa(String mesto, String postanskiBroj, String ulica, String broj) {
		super();
		this.mesto = mesto;
		this.postanskiBroj = postanskiBroj;
		this.ulica = ulica;
		this.broj = broj;
	}
	
	@Override
	public String toString() {
		String suma = "PODACI ADRESE:\n";
		suma += String.format("mesto: %s, postanski broj: %s, ulica: %s, broj: %s\n", this.mesto, this.postanskiBroj, this.ulica, this.broj);
		return suma;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

}
