package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.constants.Namespaces;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OrganVlasti", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = { "naziv", "sediste" })
public class OrganVlasti {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String naziv;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String sediste;

	public OrganVlasti() {
		super();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSediste() {
		return sediste;
	}

	public void setSediste(String sediste) {
		this.sediste = sediste;
	}

}
