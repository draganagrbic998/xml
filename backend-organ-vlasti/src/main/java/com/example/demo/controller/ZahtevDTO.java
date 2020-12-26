package com.example.demo.controller;

import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.model.enums.TipZahteva;

public class ZahtevDTO {
	
	private String broj;
	private String datum;
	private TipZahteva tipZahteva;
	private StatusZahteva status;
	
	public ZahtevDTO() {
		super();
	}

	public ZahtevDTO(String broj, String datum, TipZahteva tipZahteva, StatusZahteva status) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.tipZahteva = tipZahteva;
		this.status = status;
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

	public StatusZahteva getStatus() {
		return status;
	}

	public void setStatus(StatusZahteva status) {
		this.status = status;
	}
	
}
