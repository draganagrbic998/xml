package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "korisnik", namespace = "mama")
@XmlType(propOrder = { "uloga", "email", "lozinka", "ime", "prezime" })
public class Korisnik implements UserDetails {
	
	@XmlElement(namespace = "mama", required = true)
	private Uloga uloga;
	
	@XmlElement(namespace = "mama", required = true)
	private String email;
	
	@XmlElement(namespace = "mama", required = true)
	private String lozinka;
	
	@XmlElement(namespace = "mama", required = true)
	private String ime;
	
	@XmlElement(namespace = "mama", required = true)
	private String prezime;
	
	public Korisnik() {
		super();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(new Authority(this.uloga + ""));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.lozinka;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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

}
