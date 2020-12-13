package com.example.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.constants.Namespaces;

@XmlRootElement(name = "Osoba", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = { "ime", "prezime" })
public class Osoba {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String ime;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String prezime;
	
	public Osoba() {
		super();
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
