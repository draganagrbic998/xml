package com.example.demo.model;

import java.util.Date;

public class Zalba {

	private long broj;
	private TipZalbe tip;
	private Date datum;
	private Zahtev zahtev;
	private Odluka odluka;
	private String obrazlozenje;
	
	public Zalba() {
		super();
	}

	public long getBroj() {
		return broj;
	}

	public void setBroj(long broj) {
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
