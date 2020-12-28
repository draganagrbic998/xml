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

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Korisnik", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = {  "aktivan", "lozinka", "osoba", "adresa" })
public class Korisnik implements UserDetails {
		
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private boolean aktivan;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String lozinka;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Osoba")
	private Osoba osoba;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Adresa")
	private Adresa adresa;

	public Korisnik() {
		super();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(new Authority(this.getUloga()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.lozinka;
	}

	@Override
	public String getUsername() {
		return this.osoba.getMejl();
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
		return this.aktivan;
	}
	
	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Osoba getOsoba() {
		return osoba;
	}

	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public String getUloga() {
		if (this.adresa == null) {
			return Constants.SLUZBENIK;
		}
		return Constants.GRADJANIN;
	}

}
