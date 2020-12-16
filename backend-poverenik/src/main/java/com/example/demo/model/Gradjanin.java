package com.example.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.constants.Namespaces;

@XmlRootElement(name = "Gradjanin", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = { "osoba", "adresa" })
public class Gradjanin {

	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Osoba")
	private Osoba osoba;

	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Adresa")
	private Adresa adresa;

	public Gradjanin() {
		super();
	}

	public Osoba getOsoba() {
		return osoba;
	}

	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
}
