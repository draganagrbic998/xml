package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.common.Namespaces;

@XmlRootElement(name = "Odluka", namespace = Namespaces.ODLUKA)
@XmlType(propOrder = { "pasusi" })
@XmlDiscriminatorValue("odluka:TOdgovor")
public class Odgovor extends Odluka {

	@XmlElementWrapper(namespace = Namespaces.ODLUKA, required = true, name="Pasusi")
	@XmlElement(namespace = Namespaces.ODLUKA, required = true, name = "pasus")
	private List<String> pasusi;

	public Odgovor() {
		super();
	}

	public Odgovor(String broj, Date datum, Zahtev zahtev, String potpisSluzbenika,
			List<String> pasusi) {
		super(broj, datum, zahtev, potpisSluzbenika);
		this.pasusi = pasusi;
	}

	public List<String> getPasusi() {
		return pasusi;
	}

	public void setPasusi(List<String> pasusi) {
		this.pasusi = pasusi;
	}
	
}
