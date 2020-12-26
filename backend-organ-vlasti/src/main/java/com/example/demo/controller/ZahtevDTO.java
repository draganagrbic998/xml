package com.example.demo.controller;

import com.example.demo.model.enums.TipZahteva;

public class ZahtevDTO {
	
	private String broj;
	private String datum;
	private TipZahteva tipZahteva;
	
	public ZahtevDTO() {
		super();
	}

	public ZahtevDTO(String broj, String datum, TipZahteva tipZahteva) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.tipZahteva = tipZahteva;
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

	public TipZahteva getTipZahteva() {
		return tipZahteva;
	}

	public void setTipZahteva(TipZahteva tipZahteva) {
		this.tipZahteva = tipZahteva;
	}

}
