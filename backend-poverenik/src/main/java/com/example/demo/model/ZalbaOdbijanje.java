package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.constants.Namespaces;
import com.example.demo.parser.JAXBDateAdapter;

@XmlRootElement(name = "Zalba", namespace = Namespaces.POVERENIK)
@XmlType(propOrder = { "brojOdluke", "datumOdluke" })
@XmlDiscriminatorValue("poverenik:TZalbaOdbijanje")
public class ZalbaOdbijanje extends Zalba {
	
	@XmlElement(namespace = Namespaces.POVERENIK, required = true)
	private String brojOdluke;

	@XmlElement(namespace = Namespaces.POVERENIK, required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datumOdluke;

	public ZalbaOdbijanje() {
		super();
	}

	public String getBrojOdluke() {
		return brojOdluke;
	}

	public void setBrojOdluke(String brojOdluke) {
		this.brojOdluke = brojOdluke;
	}

	public Date getDatumOdluke() {
		return datumOdluke;
	}

	public void setDatumOdluke(Date datumOdluke) {
		this.datumOdluke = datumOdluke;
	}

}
