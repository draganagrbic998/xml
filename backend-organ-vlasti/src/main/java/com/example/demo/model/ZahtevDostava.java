package com.example.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.constants.Namespaces;
import com.example.demo.model.enums.TipDostave;

@XmlRootElement(name = "Zahtev", namespace = Namespaces.ORGAN_VLASTI)
@XmlType(propOrder = { "tipDostave", "opisDostave" })
@XmlDiscriminatorValue("organ_vlasti:TZahtevDostava")
public class ZahtevDostava extends Zahtev {

	@XmlElement(namespace = Namespaces.ORGAN_VLASTI, required = true)
	private TipDostave tipDostave;
	
	@XmlElement(namespace = Namespaces.ORGAN_VLASTI, required = true)
	private String opisDostave;
	
	public ZahtevDostava() {
		super();
	}

	public ZahtevDostava(TipDostave tipDostave, String opisDostave) {
		super();
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
