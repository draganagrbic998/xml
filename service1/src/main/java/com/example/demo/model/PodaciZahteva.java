package com.example.demo.model;

public class PodaciZahteva {
	
	private String trazenaInformacija;
	private TipDostave tipDostave;
	private String opisDostave;
	
	public PodaciZahteva() {
		super();
	}

	public String getTrazenaInformacija() {
		return trazenaInformacija;
	}

	public void setTrazenaInformacija(String trazenaInformacija) {
		this.trazenaInformacija = trazenaInformacija;
	}

	public TipDostave getTipDostave() {
		return tipDostave;
	}

	public void setTipDostave(TipDostave tipDostave) {
		this.tipDostave = tipDostave;
	}

	public String getOpisDostave() {
		return opisDostave;
	}

	public void setOpisDostave(String opisDostave) {
		this.opisDostave = opisDostave;
	}
	
}
