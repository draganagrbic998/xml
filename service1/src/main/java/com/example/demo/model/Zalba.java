package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Zalba {

	private String broj;
	private Date datum;
	private Zahtev zahtev;
	private Obavestenje obavestenje;
	private Odbijanje odbijanje;
	private String obrazlozenje;

	public Zalba() {
		super();
	}

	public Zalba(String broj, Date datum, Zahtev zahtev, Obavestenje obavestenje, Odbijanje odbijanje,
			String obrazlozenje) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.zahtev = zahtev;
		this.obavestenje = obavestenje;
		this.odbijanje = odbijanje;
		this.obrazlozenje = obrazlozenje;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ZALBE:\n";
		suma += String.format("broj: %s, datum: %s, obrazlozenje: %s\n", this.broj, format.format(this.datum),
				this.obrazlozenje);
		if (this.zahtev != null) {
			suma += this.zahtev.toString();
		}
		if (this.obavestenje != null) {
			suma += this.obavestenje.toString();
		}
		if (this.odbijanje != null) {
			suma += this.odbijanje.toString();
		}
		return suma;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Zahtev getZahtev() {
		return zahtev;
	}

	public void setZahtev(Zahtev zahtev) {
		this.zahtev = zahtev;
	}

	public Obavestenje getObavestenje() {
		return obavestenje;
	}

	public void setObavestenje(Obavestenje obavestenje) {
		this.obavestenje = obavestenje;
	}

	public Odbijanje getOdbijanje() {
		return odbijanje;
	}

	public void setOdbijanje(Odbijanje odbijanje) {
		this.odbijanje = odbijanje;
	}

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

}
