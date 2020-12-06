package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.common.Namespaces;
import com.example.demo.model.enums.TipObavestenja;

@XmlRootElement(name = "Zahtev", namespace = Namespaces.ZAHTEV)
@XmlType(propOrder = { "tipObavestenja" })
@XmlDiscriminatorValue("zahtev:TZahtev_uvid")
public class ZahtevUvid extends Zahtev {
	
	@XmlElement(namespace = Namespaces.ZAHTEV, required = true, name = "tip_obavestenja")
	private TipObavestenja tipObavestenja;

	public ZahtevUvid() {
		super();
	}

	public ZahtevUvid(String broj, Date datum, Korisnik gradjanin, OrganVlasti organVlasti,
			String trazenaInformacija, TipObavestenja tipObavestenja) {
		super(broj, datum, gradjanin, organVlasti, trazenaInformacija);
		this.tipObavestenja = tipObavestenja;
	}

	public TipObavestenja getTipObavestenja() {
		return tipObavestenja;
	}

	public void setTipObavestenja(TipObavestenja tipObavestenja) {
		this.tipObavestenja = tipObavestenja;
	}
	
}
