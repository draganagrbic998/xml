package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import com.example.demo.common.Namespaces;
import com.example.demo.parser.JAXBDateAdapter;

@XmlDiscriminatorNode("@xsi:type")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "broj", "datum", "zahtev", "potpisSluzbenika" })
@XmlSeeAlso({Obavestenje.class, Odgovor.class, Odbijanje.class})
public class Odluka {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String broj;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datum;
		
	@XmlElement(namespace = Namespaces.ZAHTEV, required = true, name = "Zahtev")
	private Zahtev zahtev;
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "potpis_sluzbenika")
	private String potpisSluzbenika;

	public Odluka() {
		super();
	}

	public Odluka(String broj, Date datum, Zahtev zahtev, String potpisSluzbenika) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.zahtev = zahtev;
		this.potpisSluzbenika = potpisSluzbenika;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Zahtev getZahtev() {
		return zahtev;
	}

	public void setZahtev(Zahtev zahtev) {
		this.zahtev = zahtev;
	}

	public String getPotpisSluzbenika() {
		return potpisSluzbenika;
	}

	public void setPotpisSluzbenika(String potpisSluzbenika) {
		this.potpisSluzbenika = potpisSluzbenika;
	}
	
}
