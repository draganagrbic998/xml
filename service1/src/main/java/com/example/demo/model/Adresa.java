package com.example.demo.model;

public class Adresa {
	
	private String mesto;
	private String postanskiBroj;
	private String ulica;
	private String broj;
	
	public Adresa() {
		super();
	}

	public Adresa(String mesto, String postanskiBroj, String ulica, String broj) {
		super();
		this.mesto = mesto;
		this.postanskiBroj = postanskiBroj;
		this.ulica = ulica;
		this.broj = broj;
	}
	
	@Override
	public String toString() {
		String suma = "PODACI ADRESE:\n";
		suma += String.format("mesto: %s, postanski broj: %s, ulica: %s, broj: %s\n", this.mesto, this.postanskiBroj, this.ulica, this.broj);
		return suma;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

}
