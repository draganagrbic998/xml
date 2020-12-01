package com.example.demo.model;

public class PodaciObavestenja {

	private Uvid uvid;
	private Kopija kopija;

	public PodaciObavestenja() {
		super();
	}

	public PodaciObavestenja(Uvid uvid, Kopija kopija) {
		super();
		this.uvid = uvid;
		this.kopija = kopija;
	}

	@Override
	public String toString() {
		String suma = "PODACI PODATAKA OBAVESTENJA:\n";
		suma += this.uvid.toString();
		suma += this.kopija.toString();
		return suma;
	}

	public Uvid getUvid() {
		return uvid;
	}

	public void setUvid(Uvid uvid) {
		this.uvid = uvid;
	}

	public Kopija getKopija() {
		return kopija;
	}

	public void setKopija(Kopija kopija) {
		this.kopija = kopija;
	}

}