package com.example.demo.model;

public class Korisnik {

	private String id;	//jel ovde mogu da stavim malo long???
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

	public Korisnik(String id, Uloga uloga, String email, String lozinka, String ime, String prezime, Adresa adresa,
			String potpis) {
		super();
		this.id = id;
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
		suma += String.format("id: %s, uloga: %s, email: %s, lozinka: %s, ime: %s, prezime: %s, potpis: %s\n", this.id, this.uloga, this.email, this.lozinka, this.ime, this.prezime, this.potpis);
		suma += this.adresa.toString();
		return suma;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
