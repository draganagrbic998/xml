package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import com.example.demo.common.Namespaces;
import com.example.demo.parser.JAXBDateAdapter;

@XmlDiscriminatorNode("@xsi:type")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "broj", "datum" })
@XmlSeeAlso({ZalbaCutanje.class, ZalbaDelimicnost.class})
public abstract class Zalba {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String broj;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datum;
	
	public Zalba() {
		super();
	}

	public Zalba(String broj, Date datum) {
		super();
		this.broj = broj;
		this.datum = datum;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

}
