package com.example.demo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Pretraga")
@XmlType(propOrder = { "fraze", "kljucneReci" })
public class ObicnaPretraga {

	@XmlElementWrapper(name = "Fraze")
	@XmlElement(name = "fraza")
	private List<String> fraze;
	
	@XmlElement(name = "kljucne_reci")
	private String kljucneReci;

	public ObicnaPretraga() {
		super();
	}

	public List<String> getFraze() {
		return fraze;
	}

	public void setFraze(List<String> fraze) {
		this.fraze = fraze;
	}

	public String getKljucneReci() {
		return kljucneReci;
	}

	public void setKljucneReci(String kljucneReci) {
		this.kljucneReci = kljucneReci;
	}
	
}
