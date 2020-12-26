package com.example.demo.model;

import com.example.demo.model.enums.TipDetalja;

public class DeoDetalja {
	
	private TipDetalja tip;
	private String vrednost;
	
	public DeoDetalja() {
		super();
	}

	public DeoDetalja(TipDetalja tip, String vrednost) {
		super();
		this.tip = tip;
		this.vrednost = vrednost;
	}

	public TipDetalja getTip() {
		return tip;
	}

	public void setTip(TipDetalja tip) {
		this.tip = tip;
	}

	public String getVrednost() {
		return vrednost;
	}

	public void setVrednost(String vrednost) {
		this.vrednost = vrednost;
	}

}
