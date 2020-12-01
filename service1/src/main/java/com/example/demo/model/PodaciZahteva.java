package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Podaci_zahteva", namespace = "https://github.com/draganagrbic998/xml/zahtev")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "trazenaInformacija", "tipDostave", "opisDostave" })
public class PodaciZahteva {
	
	@XmlElement(name = "Trazena_informacija", namespace = "https://github.com/draganagrbic998/xml/zahtev", required = true)
	private String trazenaInformacija;
	
	@XmlElement(name = "Tip_dostave", namespace = "https://github.com/draganagrbic998/xml/zahtev", required = true)
	private TipDostave tipDostave;
	
	@XmlElement(name = "Opis_dostave", namespace = "https://github.com/draganagrbic998/xml/zahtev", required = true)
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
