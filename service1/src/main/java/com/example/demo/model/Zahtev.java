package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Zahtev", namespace = "https://github.com/draganagrbic998/xml/zahtev")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "gradjanin", "organVlasti", "podaci" })
public class Zahtev {

	@XmlAttribute(name = "tip_zahteva", required = true)
	private TipZahteva tipZahteva;
	
	@XmlAttribute(name = "broj", required = true)
	private String broj;
	
	@XmlAttribute(name = "datum", required = true)
	private Date datum;
	
	@XmlElement(name = "Korisnik", namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private Korisnik gradjanin;
	
	@XmlElement(name = "Organ_vlasti", namespace = "https://github.com/draganagrbic998/xml/organ_vlasti", required = true)
	private OrganVlasti organVlasti;
	
	@XmlElement(name = "Podaci_zahteva", namespace = "https://github.com/draganagrbic998/xml/zahtev", required = true)
	private PodaciZahteva podaci;
	
	public Zahtev() {
		super();
	}

	public Zahtev(String broj, TipZahteva tipZahteva, Date datum, OrganVlasti organVlasti, Korisnik gradjanin,
			PodaciZahteva podaci) {
		super();
		this.broj = broj;
		this.tipZahteva = tipZahteva;
		this.datum = datum;
		this.organVlasti = organVlasti;
		this.gradjanin = gradjanin;
		this.podaci = podaci;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ZAHTEVA:\n";
		suma += String.format("broj: %s, tip: %s, datum: %s\n", this.broj, this.tipZahteva, format.format(this.datum));
		suma += this.organVlasti.toString();
		suma += this.gradjanin.toString();
		suma += this.podaci.toString();
		return suma;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public TipZahteva getTipZahteva() {
		return tipZahteva;
	}

	public void setTipZahteva(TipZahteva tipZahteva) {
		this.tipZahteva = tipZahteva;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public OrganVlasti getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(OrganVlasti organVlasti) {
		this.organVlasti = organVlasti;
	}

	public Korisnik getGradjanin() {
		return gradjanin;
	}

	public void setGradjanin(Korisnik gradjanin) {
		this.gradjanin = gradjanin;
	}

	public PodaciZahteva getPodaci() {
		return podaci;
	}

	public void setPodaci(PodaciZahteva podaci) {
		this.podaci = podaci;
	}
	
}
