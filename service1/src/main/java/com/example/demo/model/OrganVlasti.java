package com.example.demo.model;

public class OrganVlasti {
	
	private String naziv;
	private Adresa adresa;
	
	public OrganVlasti() {
		super();
	}

	public OrganVlasti(String naziv, Adresa adresa) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
	}

	@Override
	public String toString() {
		String suma = "PODACI ORGANA VLASTI:\n";
		suma += String.format("naziv: %s\n", this.naziv);
		suma += this.adresa.toString();
		return suma;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

}
