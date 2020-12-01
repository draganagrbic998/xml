package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Resenje", namespace = "https://github.com/draganagrbic998/xml/resenje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "poverenik", "zalba", "podaci" })
public class Resenje {

	@XmlAttribute(name = "tip_resenja", required = true)
	private TipResenja tip;
	
	@XmlAttribute(name = "broj", required = true)
	private String broj;
	
	@XmlAttribute(name = "datum", required = true)
	private Date datum;
	
	@XmlElement(name = "Korisnik", namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private Korisnik poverenik;
	
	@XmlElement(name = "Zalba", namespace = "https://github.com/draganagrbic998/xml/zalba", required = true)
	private Zalba zalba;
	
	@XmlElement(name = "Podaci_resenja", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	private PodaciResenja podaci;
	
	public Resenje() {
		super();
	}
	
	public Resenje(String broj, Date datum, TipResenja tip, Korisnik poverenik, Zalba zalba, PodaciResenja podaci) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.tip = tip;
		this.poverenik = poverenik;
		this.zalba = zalba;
		this.podaci = podaci;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI RESENJA:\n";
		suma += String.format("broj: %s, tip: %s, datum: %s\n", this.broj, this.tip, format.format(this.datum));
		suma += this.poverenik.toString();
		suma += this.zalba.toString();
		suma += this.podaci.toString();
		return suma;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public TipResenja getTip() {
		return tip;
	}

	public void setTip(TipResenja tip) {
		this.tip = tip;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Korisnik getPoverenik() {
		return poverenik;
	}

	public void setPoverenik(Korisnik poverenik) {
		this.poverenik = poverenik;
	}

	public Zalba getZalba() {
		return zalba;
	}

	public void setZalba(Zalba zalba) {
		this.zalba = zalba;
	}

	public PodaciResenja getPodaci() {
		return podaci;
	}

	public void setPodaci(PodaciResenja podaci) {
		this.podaci = podaci;
	}

	
}
