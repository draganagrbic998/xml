package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Zalba {

	private String broj;
	private TipZalbe tip;
	private Date datum;
	private Zahtev zahtev;
	private Odluka odluka;
	private String obrazlozenje;
	
	public Zalba() {
		super();
	}
	
	public Zalba(String broj, TipZalbe tip, Date datum, Zahtev zahtev, Odluka odluka, String obrazlozenje) {
		super();
		this.broj = broj;
		this.tip = tip;
		this.datum = datum;
		this.zahtev = zahtev;
		this.odluka = odluka;
		this.obrazlozenje = obrazlozenje;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ZALBE:\n";
		suma += String.format("broj: %s, tip: %s, datum: %s, obrazlozenje: %s\n", this.broj, this.tip, format.format(this.datum), this.obrazlozenje);
		suma += this.zahtev.toString();
		if (this.odluka != null) {
			suma += this.odluka.toString();
		}
		return suma;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public TipZalbe getTip() {
		return tip;
	}

	public void setTip(TipZalbe tip) {
		this.tip = tip;
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

	public Odluka getOdluka() {
		return odluka;
	}

	public void setOdluka(Odluka odluka) {
		this.odluka = odluka;
	}

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}
	
}
