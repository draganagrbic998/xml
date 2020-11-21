package com.example.demo.model;

public class PodaciOdluke {

	private String odgovor;
	private Uvid uvid;
	private Kopija kopija;
	
	public PodaciOdluke() {
		super();
	}

	public PodaciOdluke(String odgovor, Uvid uvid, Kopija kopija) {
		super();
		this.odgovor = odgovor;
		this.uvid = uvid;
		this.kopija = kopija;
	}
	
	@Override
	public String toString() {
		String suma = "PODACI PODATAKA ODLUKE:\n";
		suma += String.format("odgovor: %s\n", this.odgovor);
		if (this.uvid != null) {
			suma += this.uvid.toString();
		}
		if (this.kopija != null) {
			suma += this.kopija.toString();
		}
		return suma;
	}

	public String getOdgovor() {
		return odgovor;
	}

	public void setOdgovor(String odgovor) {
		this.odgovor = odgovor;
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
