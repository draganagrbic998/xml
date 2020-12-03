package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import jaxb.DateAdapter;

@XmlRootElement(name = "Odbrana", namespace = "https://github.com/draganagrbic998/xml/resenje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "datumPrijema", "odgovor" })
public class Odbrana {

	@XmlElement(name = "Datum_prijema", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date datumPrijema;
	
	@XmlElement(name = "Odgovor", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	private String odgovor;

	public Odbrana() {
		super();
	}

	public Odbrana(Date datumPrijema, String odgovor) {
		super();
		this.datumPrijema = datumPrijema;
		this.odgovor = odgovor;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ODBRANE:\n";
		if (datumPrijema != null) {
			suma += String.format("prijem zalbe: %s\n", format.format(this.datumPrijema));
		}
		suma += this.odgovor.toString();
		return suma;
	}

	public Date getDatumPrijema() {
		return datumPrijema;
	}

	public void setDatumPrijema(Date datumPrijema) {
		this.datumPrijema = datumPrijema;
	}

	public String getOdgovor() {
		return odgovor;
	}

	public void setOdgovor(String odgovor) {
		this.odgovor = odgovor;
	}

}
