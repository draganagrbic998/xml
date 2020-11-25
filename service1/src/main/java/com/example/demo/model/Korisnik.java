package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "uloga", "email", "lozinka", "ime", "prezime", "adresa", "potpis" })
@XmlRootElement(name = "Korisnik", namespace = "https://github.com/draganagrbic998/xml/korisnik")
public class Korisnik {

	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private Uloga uloga;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private String email;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private String lozinka;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private String ime;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private String prezime;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/osnova", required = true)
	private Adresa adresa;
	
	@XmlElement(namespace = "https://github.com/draganagrbic998/xml/korisnik", required = true)
	private String potpis;
	
	public Korisnik() {
		super();
		System.out.println("Korisnik created...");
	}

	public Korisnik(Uloga uloga, String email, String lozinka, String ime, String prezime, Adresa adresa,
			String potpis) {
		super();
		this.uloga = uloga;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.potpis = potpis;
	}
	
	@Override
	public String toString() {
		String suma = "PODACI KORISNIKA:\n";
		suma += String.format("uloga: %s, email: %s, lozinka: %s, ime: %s, prezime: %s, potpis: %s\n", this.uloga, this.email, this.lozinka, this.ime, this.prezime, this.potpis);
		//suma += this.adresa.toString();
		return suma;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public String getPotpis() {
		return potpis;
	}

	public void setPotpis(String potpis) {
		this.potpis = potpis;
	}
	
}
