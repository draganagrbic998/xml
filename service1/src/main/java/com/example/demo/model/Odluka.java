package com.example.demo.model;

import java.util.Date;

public class Odluka {
	
	private long broj;
	private TipOdluke tip;
	private Date datum;
	private Korisnik sluzbenik;
	private Zahtev zahtev;
	private PodaciOdluke podaci;
	
	public Odluka() {
		super();
	}

	public long getBroj() {
		return broj;
	}

	public void setBroj(long broj) {
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
