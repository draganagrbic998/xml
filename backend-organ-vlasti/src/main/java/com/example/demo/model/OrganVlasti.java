package com.example.demo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.example.demo.common.Namespaces;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OrganVlasti", namespace = Namespaces.OSNOVA)
@XmlType(propOrder = { "naziv", "adresa" })
public class OrganVlasti {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String naziv;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Adresa")
	private Adresa adresa;

	public OrganVlasti() {
		super();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

}
