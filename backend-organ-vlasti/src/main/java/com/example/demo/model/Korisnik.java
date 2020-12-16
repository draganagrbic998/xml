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

import com.example.demo.constants.Namespaces;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Korisnik", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = {  "email", "lozinka", "gradjanin" })
public class Korisnik implements UserDetails {
		
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String email;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String lozinka;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Gradjanin")
	private Gradjanin gradjanin;

	public Korisnik() {
		super();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Authority> authorities = new ArrayList<>();
		String uloga = this.gradjanin == null ? "sluzbenik" : "gradjanin";
		authorities.add(new Authority(uloga));
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

	public Gradjanin getGradjanin() {
		return gradjanin;
	}

	public void setGradjanin(Gradjanin gradjanin) {
		this.gradjanin = gradjanin;
	}

}
