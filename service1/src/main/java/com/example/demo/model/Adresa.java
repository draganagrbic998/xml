package com.example.demo.model;

public class Adresa {
	
	private String mesto;
	private int postanskiBroj;
	private String ulica;
	private int broj;
	
	public Adresa() {
		super();
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public int getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(int postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

}
