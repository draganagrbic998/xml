package com.example.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.constants.Namespaces;
import com.example.demo.model.enums.TipUvida;

@XmlRootElement(name = "Zahtev", namespace = Namespaces.ORGAN_VLASTI)
@XmlType(propOrder = { "tipUvida" })
@XmlDiscriminatorValue("organ_vlasti:TZahtevUvid")
public class ZahtevUvid extends Zahtev {

	@XmlElement(namespace = Namespaces.ORGAN_VLASTI, required = true)
	private TipUvida tipUvida;

	public ZahtevUvid() {
		super();
	}

	public TipUvida getTipUvida() {
		return tipUvida;
	}

	public void setTipUvida(TipUvida tipUvida) {
		this.tipUvida = tipUvida;
	}
	
}
