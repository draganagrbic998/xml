package com.example.demo.controller;

import com.example.demo.model.enums.StatusZalbe;
import com.example.demo.model.enums.TipZalbe;

public class ZalbaDTO {

	private TipZalbe tipZalbe;
	private String broj;
	private String datum;
	private String organVlasti;
	private StatusZalbe status;
	
	public ZalbaDTO() {
		super();
	}

	public ZalbaDTO(TipZalbe tipZalbe, String broj, String datum, String organVlasti, StatusZalbe status) {
		super();
		this.tipZalbe = tipZalbe;
		this.broj = broj;
		this.datum = datum;
		this.organVlasti = organVlasti;
		this.status = status;
	}

	public TipZalbe getTipZalbe() {
		return tipZalbe;
	}

	public void setTipZalbe(TipZalbe tipZalbe) {
		this.tipZalbe = tipZalbe;
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

	public String getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(String organVlasti) {
		this.organVlasti = organVlasti;
	}

	public StatusZalbe getStatus() {
		return status;
	}

	public void setStatus(StatusZalbe status) {
		this.status = status;
	}

}
