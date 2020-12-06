package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.common.Namespaces;

@XmlRootElement(name = "Odluka", namespace = Namespaces.ODLUKA)
@XmlType(propOrder = { "obrazlozenje" })
@XmlDiscriminatorValue("odluka:TOdbijanje")
public class Odbijanje extends Odluka {
	
	@XmlElement(namespace = Namespaces.ODLUKA, required = true)
	private String obrazlozenje;

	public Odbijanje() {
		super();
	}

	public Odbijanje(String broj, Date datum, Zahtev zahtev, String potpisSluzbenika,
			String obrazlozenje) {
		super(broj, datum, zahtev, potpisSluzbenika);
		this.obrazlozenje = obrazlozenje;
	}

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

}
