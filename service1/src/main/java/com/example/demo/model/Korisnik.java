package com.example.demo.model;

public class Korisnik {

	private long id;	//jel ovde mogu da stavim malo long???
	private Uloga uloga;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private Adresa adresa;
	private String potpis;
	
	public Korisnik() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
