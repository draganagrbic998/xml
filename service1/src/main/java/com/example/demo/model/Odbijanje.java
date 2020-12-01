package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Odbijanje {
	
	private String broj;
	private Date datum;
	private Korisnik sluzbenik;
	private Zahtev zahtev;
	private String obrazlozenje;
	
	public Odbijanje() {
		super();
	}

	public Odbijanje(String broj, Date datum, Korisnik sluzbenik, Zahtev zahtev, String obrazlozenje) {
		this.broj = broj;
		this.datum = datum;
		this.sluzbenik = sluzbenik;
		this.zahtev = zahtev;
		this.obrazlozenje = obrazlozenje;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ODBIJANJA:\n";
		suma += String.format("broj: %s, datum: %s\n", this.broj, format.format(this.datum));
		suma += this.sluzbenik.toString();
		suma += this.zahtev.toString();
		suma += this.obrazlozenje;
		return suma;
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
	
	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setPodaci(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

}
