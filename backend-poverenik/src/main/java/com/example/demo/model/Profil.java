package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "profil")
@XmlType(propOrder = { "token", "uloga", "mejl", "ime", "prezime" })
public class Profil {
	
	@XmlElement(required = true)
	private String token;
	
	@XmlElement(required = true)
	private String uloga;
	
	@XmlElement(required = true)
	private String mejl;
	
	@XmlElement(required = true)
	private String ime;
	
	@XmlElement(required = true)
	private String prezime;

	public Profil() {
		super();
	}
	
	public Profil(String token, String uloga, String mejl, String ime, String prezime) {
		super();
		this.token = token;
		this.uloga = uloga;
		this.mejl = mejl;
		this.ime = ime;
		this.prezime = prezime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public String getMejl() {
		return mejl;
	}

	public void setMejl(String mejl) {
		this.mejl = mejl;
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

}
