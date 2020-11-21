package com.example.demo.model;

public class PodaciZahteva {
	
	private String trazenaInformacija;
	private TipDostave tipDostave;
	private String opisDostave;
	
	public PodaciZahteva() {
		super();
	}

	public PodaciZahteva(String trazenaInformacija, TipDostave tipDostave, String opisDostave) {
		super();
		this.trazenaInformacija = trazenaInformacija;
		this.tipDostave = tipDostave;
		this.opisDostave = opisDostave;
	}
	
	@Override
	public String toString() {
		String suma = "PODACI PODATAKA ZAHTEVA:\n";
		suma += String.format("trazena informacija: %s, tip dostave: %s, opis dostave: %s\n", this.trazenaInformacija, this.tipDostave, this.opisDostave);
		return suma;
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
