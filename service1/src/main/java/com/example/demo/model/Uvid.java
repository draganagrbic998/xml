package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Uvid {
	
	private Date uvidOd;
	private Date uvidDo;
	private Adresa adresa;
	private String kancelarija;
	
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
