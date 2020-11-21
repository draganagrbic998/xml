package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Zahtev {
	
	private String broj;
	private TipZahteva tipZahteva;
	private Date datum;
	private OrganVlasti organVlasti;
	private Korisnik gradjanin;
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
