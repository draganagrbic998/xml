package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.common.Namespaces;
import com.example.demo.model.enums.TipCutanja;

@XmlRootElement(name = "Zalba", namespace = Namespaces.ZALBA)
@XmlType(propOrder = { "zahtev", "tipCutanja" })
@XmlDiscriminatorValue("zalba:TZalba_cutanje")
public class ZalbaCutanje extends Zalba {

	@XmlElement(namespace = Namespaces.ZAHTEV, required = true, name = "Zahtev")
	private Zahtev zahtev;
	
	@XmlElement(namespace = Namespaces.ZALBA, required = true, name = "tip_cutanja")
	private TipCutanja tipCutanja;

	public ZalbaCutanje() {
		super();
	}

	public ZalbaCutanje(String broj, Date datum, 
			Zahtev zahtev, TipCutanja tipCutanja) {
		super(broj, datum);
		this.zahtev = zahtev;
		this.tipCutanja = tipCutanja;
	}

	public Zahtev getZahtev() {
		return zahtev;
	}

	public void setZahtev(Zahtev zahtev) {
		this.zahtev = zahtev;
	}

	public TipCutanja getTipCutanja() {
		return tipCutanja;
	}

	public void setTipCutanja(TipCutanja tipCutanja) {
		this.tipCutanja = tipCutanja;
	}
	
}
