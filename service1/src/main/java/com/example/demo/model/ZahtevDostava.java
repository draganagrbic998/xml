package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.common.Namespaces;
import com.example.demo.model.enums.TipDostave;

@XmlRootElement(name = "Zahtev", namespace = Namespaces.ZAHTEV)
@XmlType(propOrder = { "tipDostave", "opisDostave" })
@XmlDiscriminatorValue("zahtev:TZahtev_dostava")
public class ZahtevDostava extends Zahtev {
	
	@XmlElement(namespace = Namespaces.ZAHTEV, required = true, name = "tip_dostave")
	private TipDostave tipDostave;
	
	@XmlElement(namespace = Namespaces.ZAHTEV, required = true, name = "opis_dostave")
	private String opisDostave;

	public ZahtevDostava() {
		super();
	}

	public ZahtevDostava(String broj, Date datum, Korisnik gradjanin, OrganVlasti organVlasti,
			String trazenaInformacija, TipDostave tipDostave, String opisDostave) {
		super(broj, datum, gradjanin, organVlasti, trazenaInformacija);
		this.tipDostave = tipDostave;
		this.opisDostave = opisDostave;
	}

	public TipDostave getTipDostave() {
		return tipDostave;
	}

	public void setTipDostave(TipDostave tipDostave) {
		this.tipDostave = tipDostave;
	}

	public String getOpisDostave() {
		return opisDostave;
	}

	public void setOpisDostave(String opisDostave) {
		this.opisDostave = opisDostave;
	}

}
