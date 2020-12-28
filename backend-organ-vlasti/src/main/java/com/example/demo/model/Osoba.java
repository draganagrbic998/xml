package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.constants.Namespaces;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Osoba", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = { "mejl", "ime", "prezime" })
public class Osoba {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String mejl;

	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String ime;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String prezime;
	
	public Osoba() {
		super();
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
