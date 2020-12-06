package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.example.demo.common.Namespaces;
import com.example.demo.parser.JAXBDateAdapter;

@XmlRootElement(name = "Uvid", namespace = Namespaces.ODLUKA)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "adresa", "kancelarija", "uvidOd", "uvidDo"})
public class Uvid {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Adresa")
	private Adresa adresa;

	@XmlElement(namespace = Namespaces.ODLUKA, required = true)
	private String kancelarija;
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "uvid_od")
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date uvidOd;

	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "uvid_do")
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date uvidDo;
	
	public Uvid() {
		super();
	}

	public Uvid(Adresa adresa, String kancelarija, Date uvidOd, Date uvidDo) {
		super();
		this.adresa = adresa;
		this.kancelarija = kancelarija;
		this.uvidOd = uvidOd;
		this.uvidDo = uvidDo;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public String getKancelarija() {
		return kancelarija;
	}

	public void setKancelarija(String kancelarija) {
		this.kancelarija = kancelarija;
	}

	public Date getUvidOd() {
		return uvidOd;
	}

	public void setUvidOd(Date uvidOd) {
		this.uvidOd = uvidOd;
	}

	public Date getUvidDo() {
		return uvidDo;
	}

	public void setUvidDo(Date uvidDo) {
		this.uvidDo = uvidDo;
	}

}
