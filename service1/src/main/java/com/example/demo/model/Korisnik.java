package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.common.Namespaces;
import com.example.demo.model.enums.Uloga;

@XmlRootElement(name = "Korisnik", namespace = Namespaces.KORISNIK)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "uloga", "ime", "prezime", "adresa", "email", "potpis", "jmbg", "lozinka" })
public class Korisnik {
	
	@XmlAttribute(namespace = Namespaces.XSI, required = true)
	private String type;

	@XmlElement(namespace = Namespaces.KORISNIK, required = true)
	private String jmbg;
	
	@XmlElement(namespace = Namespaces.KORISNIK, required = true)
	private Uloga uloga;
	
	@XmlElement(namespace = Namespaces.KORISNIK, required = true)
	private String email;
	
	@XmlElement(namespace = Namespaces.KORISNIK, required = true)
	private String lozinka;
	
	@XmlElement(namespace = Namespaces.KORISNIK, required = true)
	private String ime;
	
	@XmlElement(namespace = Namespaces.KORISNIK, required = true)
	private String prezime;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Adresa")
	private Adresa adresa;
	
	@XmlElement(namespace = Namespaces.KORISNIK, required = true)
	private String potpis;
	
	public Korisnik() {
		super();
	}

	public Korisnik(Uloga uloga, String ime, String prezime) {
		super();
		this.uloga = uloga;
		this.ime = ime;
		this.prezime = prezime;
	}

	public Korisnik(Uloga uloga, String ime, String prezime, Adresa adresa, String email, String potpis) {
		this(uloga, ime, prezime);
		this.adresa = adresa;
		this.email = email;
		this.potpis = potpis;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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
