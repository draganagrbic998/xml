package com.example.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.constants.Namespaces;
import com.example.demo.model.enums.TipCutanja;

@XmlRootElement(name = "Zalba", namespace = Namespaces.POVERENIK)
@XmlType(propOrder = { "tipCutanja" })
@XmlDiscriminatorValue("poverenik:TZalbaCutanje")
public class ZalbaCutanje extends Zalba {
	
	@XmlElement(namespace = Namespaces.POVERENIK, required = true)
	private TipCutanja tipCutanja;

	public ZalbaCutanje() {
		super();
	}

	public ZalbaCutanje(TipCutanja tipCutanja) {
		super();
		this.tipCutanja = tipCutanja;
	}

	public TipCutanja getTipCutanja() {
		return tipCutanja;
	}

	public void setTipCutanja(TipCutanja tipCutanja) {
		this.tipCutanja = tipCutanja;
	}

}
