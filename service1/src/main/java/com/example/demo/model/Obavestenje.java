package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.common.Namespaces;

@XmlRootElement(name = "Odluka", namespace = Namespaces.ODLUKA)
@XmlType(propOrder = { "uvid", "kopija" })
@XmlDiscriminatorValue("odluka:TObavestenje")
public class Obavestenje extends Odluka {
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "Uvid")
	private Uvid uvid;
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "Kopija")
	private Kopija kopija;

	public Obavestenje() {
		super();
	}

	public Obavestenje(String broj, Date datum, Zahtev zahtev, String potpisSluzbenika,
			Uvid uvid, Kopija kopija) {
		super(broj, datum, zahtev, potpisSluzbenika);
		this.uvid = uvid;
		this.kopija = kopija;
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
