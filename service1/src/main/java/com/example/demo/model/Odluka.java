package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Odluka {
	
	private String broj;
	private TipOdluke tip;
	private Date datum;
	private Korisnik sluzbenik;
	private Zahtev zahtev;
	private PodaciOdluke podaci;
	
	public Odluka() {
		super();
	}

	public Odluka(String broj, TipOdluke tip, Date datum, Korisnik sluzbenik, Zahtev zahtev, PodaciOdluke podaci) {
		super();
		this.broj = broj;
		this.tip = tip;
		this.datum = datum;
		this.sluzbenik = sluzbenik;
		this.zahtev = zahtev;
		this.podaci = podaci;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ODLUKE:\n";
		suma += String.format("broj: %s, tip: %s, datum: %s\n", this.broj, this.tip, format.format(this.datum));
		suma += this.sluzbenik.toString();
		suma += this.zahtev.toString();
		suma += this.podaci.toString();
		return suma;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public TipOdluke getTip() {
		return tip;
	}

	public void setTip(TipOdluke tip) {
		this.tip = tip;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Korisnik getSluzbenik() {
		return sluzbenik;
	}

	public void setSluzbenik(Korisnik sluzbenik) {
		this.sluzbenik = sluzbenik;
	}

	public Zahtev getZahtev() {
		return zahtev;
	}

	public void setZahtev(Zahtev zahtev) {
		this.zahtev = zahtev;
	}

	public PodaciOdluke getPodaci() {
		return podaci;
	}

	public void setPodaci(PodaciOdluke podaci) {
		this.podaci = podaci;
	}

}
