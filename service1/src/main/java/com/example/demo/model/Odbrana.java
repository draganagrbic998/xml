package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Odbrana {

	private Date datumPrijema;
	private String odgovor;

	public Odbrana() {
		super();
	}

	public Odbrana(Date datumPrijema, String odgovor) {
		super();
		this.datumPrijema = datumPrijema;
		this.odgovor = odgovor;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ODBRANE:\n";
		if (datumPrijema != null) {
			suma += String.format("prijem zalbe: %s\n", format.format(this.datumPrijema));
		}
		suma += this.odgovor.toString();
		return suma;
	}

	public Date getDatumPrijema() {
		return datumPrijema;
	}

	public void setDatumPrijema(Date datumPrijema) {
		this.datumPrijema = datumPrijema;
	}

	public String getOdgovor() {
		return odgovor;
	}

	public void setOdgovor(String odgovor) {
		this.odgovor = odgovor;
	}

}
