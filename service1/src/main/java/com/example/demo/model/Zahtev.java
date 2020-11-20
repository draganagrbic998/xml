package com.example.demo.model;

import java.util.Date;

public class Zahtev {
	
	private int broj;
	private TipZahteva tip;
	private Date datum;
	private OrganVlasti organVlasti;
	private Korisnik gradjanin;
	private PodaciZahteva podaci;
	
	public Zahtev() {
		super();
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	public TipZahteva getTip() {
		return tip;
	}

	public void setTip(TipZahteva tip) {
		this.tip = tip;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public OrganVlasti getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(OrganVlasti organVlasti) {
		this.organVlasti = organVlasti;
	}

	public Korisnik getGradjanin() {
		return gradjanin;
	}

	public void setGradjanin(Korisnik gradjanin) {
		this.gradjanin = gradjanin;
	}

	public PodaciZahteva getPodaci() {
		return podaci;
	}

	public void setPodaci(PodaciZahteva podaci) {
		this.podaci = podaci;
	}
	
}
