package com.example.demo.model.resenje;

import java.util.Date;
import java.util.List;

import com.example.demo.model.DocumentEntity;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Zalba;

public class Resenje implements DocumentEntity {
	
	private String broj;
	
	private Date datum;
	
	private StatusResenja status;
	
	private Korisnik poverenik;
	
	private Zalba zalba;

	private List<Pasus> dispozitiva;
	
	private List<Pasus> odbrana;
	
	private List<Pasus> odluka;
	
	private List<Pasus> tuzba;

	public Resenje() {
		super();
	}

	public Resenje(String broj, Date datum, StatusResenja status, Korisnik poverenik, Zalba zalba,
			List<Pasus> dispozitiva, List<Pasus> odbrana, List<Pasus> odluka, List<Pasus> tuzba) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.status = status;
		this.poverenik = poverenik;
		this.zalba = zalba;
		this.dispozitiva = dispozitiva;
		this.odbrana = odbrana;
		this.odluka = odluka;
		this.tuzba = tuzba;
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

	public StatusResenja getStatus() {
		return status;
	}

	public void setStatus(StatusResenja status) {
		this.status = status;
	}

	public Korisnik getPoverenik() {
		return poverenik;
	}

	public void setPoverenik(Korisnik poverenik) {
		this.poverenik = poverenik;
	}

	public Zalba getZalba() {
		return zalba;
	}

	public void setZalba(Zalba zalba) {
		this.zalba = zalba;
	}

	public List<Pasus> getDispozitiva() {
		return dispozitiva;
	}

	public void setDispozitiva(List<Pasus> dispozitiva) {
		this.dispozitiva = dispozitiva;
	}

	public List<Pasus> getOdbrana() {
		return odbrana;
	}

	public void setOdbrana(List<Pasus> odbrana) {
		this.odbrana = odbrana;
	}

	public List<Pasus> getOdluka() {
		return odluka;
	}

	public void setOdluka(List<Pasus> odluka) {
		this.odluka = odluka;
	}

	public List<Pasus> getTuzba() {
		return tuzba;
	}

	public void setTuzba(List<Pasus> tuzba) {
		this.tuzba = tuzba;
	}
	
	@Override
	public String getDocumentBroj() {
		return this.broj;
	}
	
	@Override
	public void setDocumentBroj(String broj) {
		this.broj = broj;
	}
	
}
