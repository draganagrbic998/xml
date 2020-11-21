package com.example.demo.model;

public class Kopija {

	private String iznos;
	private String brojRacuna;
	private String pozivNaBroj;
	private String model;
	
	public Kopija() {
		super();
	}

	public Kopija(String iznos, String brojRacuna, String pozivNaBroj, String model) {
		super();
		this.iznos = iznos;
		this.brojRacuna = brojRacuna;
		this.pozivNaBroj = pozivNaBroj;
		this.model = model;
	}
	
	@Override
	public String toString() {
		String suma = "PODACI KOPIJE:\n";
		suma += String.format("iznos: %s, racun: %s, poziv na broj: %s, model: %s\n", this.iznos, this.brojRacuna, this.pozivNaBroj, this.model);
		return suma;
	}

	public String getIznos() {
		return iznos;
	}

	public void setIznos(String iznos) {
		this.iznos = iznos;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public String getPozivNaBroj() {
		return pozivNaBroj;
	}

	public void setPozivNaBroj(String pozivNaBroj) {
		this.pozivNaBroj = pozivNaBroj;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}
