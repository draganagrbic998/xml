package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Organ_vlasti", namespace = "https://github.com/draganagrbic998/xml/organ_vlasti")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "naziv", "adresa" })
public class OrganVlasti {
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/organ_vlasti", required = true)
	private String naziv;
	
	@XmlElement(name = "Adresa", namespace = "https://github.com/draganagrbic998/xml/osnova", required = true)
	private Adresa adresa;
	
	public OrganVlasti() {
		super();
	}

	public OrganVlasti(String naziv, Adresa adresa) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
	}

	@Override
	public String toString() {
		String suma = "PODACI ORGANA VLASTI:\n";
		suma += String.format("naziv: %s\n", this.naziv);
		suma += this.adresa.toString();
		return suma;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

}
