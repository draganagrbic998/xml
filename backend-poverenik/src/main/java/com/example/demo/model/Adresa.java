package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.constants.Namespaces;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Adresa", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = { "mesto", "ulica", "broj" })
public class Adresa {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String mesto;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String ulica;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String broj;
	
	public Adresa() {
		super();
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
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
