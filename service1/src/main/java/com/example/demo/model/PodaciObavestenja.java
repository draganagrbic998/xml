package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Podaci_obavestenja", namespace = "https://github.com/draganagrbic998/xml/obavestenje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "uvid", "kopija" })
public class PodaciObavestenja {

	@XmlElement(name = "Uvid", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private Uvid uvid;
	
	@XmlElement(name = "Kopija", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private Kopija kopija;

	public PodaciObavestenja() {
		super();
	}

	public PodaciObavestenja(Uvid uvid, Kopija kopija) {
		super();
		this.uvid = uvid;
		this.kopija = kopija;
	}

	@Override
	public String toString() {
		String suma = "PODACI PODATAKA OBAVESTENJA:\n";
		suma += this.uvid.toString();
		suma += this.kopija.toString();
		return suma;
	}

	public Uvid getUvid() {
		return uvid;
	}

	public void setUvid(Uvid uvid) {
		this.uvid = uvid;
	}

	public Kopija getKopija() {
		return kopija;
	}

	public void setKopija(Kopija kopija) {
		this.kopija = kopija;
	}

}