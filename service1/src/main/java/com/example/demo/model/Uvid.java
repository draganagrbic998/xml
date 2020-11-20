package com.example.demo.model;

import java.util.Date;

public class Uvid {
	
	private Date uvidOd;
	private Date uvidDo;
	private Adresa adresa;
	private int kancelarija;
	
	public Uvid() {
		super();
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

	public int getKancelarija() {
		return kancelarija;
	}

	public void setKancelarija(int kancelarija) {
		this.kancelarija = kancelarija;
	}

}
