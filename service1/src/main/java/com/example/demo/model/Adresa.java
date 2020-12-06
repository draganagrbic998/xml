package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.common.Namespaces;

@XmlRootElement(name = "Adresa", namespace = Namespaces.OSNOVA)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "mesto", "postanskiBroj", "ulica", "broj" })
public class Adresa {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String mesto;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "postanski_broj")
	private String postanskiBroj;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String ulica;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String broj;
	
	public Adresa() {
		super();
	}

	public Adresa(String mesto, String postanskiBroj, String ulica, String broj) {
		super();
		this.mesto = mesto;
		this.postanskiBroj = postanskiBroj;
		this.ulica = ulica;
		this.broj = broj;
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
