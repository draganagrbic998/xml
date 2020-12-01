package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Zalba", namespace = "https://github.com/draganagrbic998/xml/zalba")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "obrazlozenje", "zahtev", "obavestenje", "odbijanje" })
public class Zalba {

	@XmlAttribute(name = "broj", required = true)
	private String broj;
	
	@XmlAttribute(name = "datum", required = true)
	private Date datum;
	
	@XmlElement(name = "obrazlozenje", namespace = "https://github.com/draganagrbic998/xml/zalba", required = false)
	private String obrazlozenje;
	
	@XmlElement(name = "Zahtev", namespace = "https://github.com/draganagrbic998/xml/zahtev", required = true)
	private Zahtev zahtev;
	
	@XmlElement(name = "Obavestenje", namespace = "https://github.com/draganagrbic998/xml/obavestenje", required = false)
	private Obavestenje obavestenje;
	
	@XmlElement(name = "Odbijanje", namespace = "https://github.com/draganagrbic998/xml/odbijanje", required = false)
	private Odbijanje odbijanje;

	public Zalba() {
		super();
	}

	public Zalba(String broj, Date datum, Zahtev zahtev, Obavestenje obavestenje, Odbijanje odbijanje,
			String obrazlozenje) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.zahtev = zahtev;
		this.obavestenje = obavestenje;
		this.odbijanje = odbijanje;
		this.obrazlozenje = obrazlozenje;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String suma = "PODACI ZALBE:\n";
		suma += String.format("broj: %s, datum: %s, obrazlozenje: %s\n", this.broj, format.format(this.datum),
				this.obrazlozenje);
		if (this.zahtev != null) {
			suma += this.zahtev.toString();
		}
		if (this.obavestenje != null) {
			suma += this.obavestenje.toString();
		}
		if (this.odbijanje != null) {
			suma += this.odbijanje.toString();
		}
		return suma;
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

	public Zahtev getZahtev() {
		return zahtev;
	}

	public void setZahtev(Zahtev zahtev) {
		this.zahtev = zahtev;
	}

	public Obavestenje getObavestenje() {
		return obavestenje;
	}

	public void setObavestenje(Obavestenje obavestenje) {
		this.obavestenje = obavestenje;
	}

	public Odbijanje getOdbijanje() {
		return odbijanje;
	}

	public void setOdbijanje(Odbijanje odbijanje) {
		this.odbijanje = odbijanje;
	}

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

}
