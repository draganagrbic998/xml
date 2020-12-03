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

@XmlRootElement(name = "Uvid", namespace = "https://github.com/draganagrbic998/xml/obavestenje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "adresa", "kancelarija", "uvidOd", "uvidDo"})
public class Uvid {
	
	@XmlElement(name = "Adresa", namespace = "https://github.com/draganagrbic998/xml/osnova", required = true)
	private Adresa adresa;

	@XmlElement(name = "kancelarija", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	private String kancelarija;
	
	@XmlElement(name = "uvid_od", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date uvidOd;

	@XmlElement(name = "uvid_do", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = true)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date uvidDo;
	
	public Uvid() {
		super();
	}
	
	public Uvid(Date uvidOd, Date uvidDo, Adresa adresa, String kancelarija) {
		super();
		this.uvidOd = uvidOd;
		this.uvidDo = uvidDo;
		this.adresa = adresa;
		this.kancelarija = kancelarija;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI UVIDA:\n";
		suma += String.format("od: %s, do: %s, kancelarija: %s\n", format.format(this.uvidOd), format.format(this.uvidDo), this.kancelarija);
		suma += this.adresa.toString();
		return suma;
	}

	public Date getUvidOd() {
		return uvidOd;
	}

	public void setUvidOd(Date uvidOd) {
		this.uvidOd = uvidOd;
	}

	public Date getUvidDo() {
		return uvidDo;
	}

	public void setUvidDo(Date uvidDo) {
		this.uvidDo = uvidDo;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public String getKancelarija() {
		return kancelarija;
	}

	public void setKancelarija(String kancelarija) {
		this.kancelarija = kancelarija;
	}

}
