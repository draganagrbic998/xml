package com.example.demo.controller;

public class ObavestenjeDTO {

	private String broj;
	private String datum;
	private String datumZahteva;
	
	public ObavestenjeDTO() {
		super();
	}

	public ObavestenjeDTO(String broj, String datum, String datumZahteva) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.datumZahteva = datumZahteva;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getDatumZahteva() {
		return datumZahteva;
	}

	public void setDatumZahteva(String datumZahteva) {
		this.datumZahteva = datumZahteva;
	}
	
}
