package com.example.demo.controller;

import com.example.demo.model.enums.StatusResenja;

public class ResenjeDTO {
	
	private String broj;
	private String datum;
	private StatusResenja status;
	private String organVlasti;
	
	public ResenjeDTO() {
		super();
	}

	public ResenjeDTO(String broj, String datum, StatusResenja status, String organVlasti) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.status = status;
		this.organVlasti = organVlasti;
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

	public StatusResenja getStatus() {
		return status;
	}

	public void setStatus(StatusResenja status) {
		this.status = status;
	}

	public String getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(String organVlasti) {
		this.organVlasti = organVlasti;
	}

}
