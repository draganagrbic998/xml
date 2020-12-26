package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.example.demo.constants.Namespaces;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.model.enums.TipDostave;
import com.example.demo.model.enums.TipZahteva;
import com.example.demo.parser.JAXBDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Zahtev", namespace = Namespaces.DOKUMENT)
@XmlType(propOrder = {  "datum", "mesto", "gradjanin", "organVlasti", "detalji", 
		"email", "potpis", "status", "tipZahteva", "tipDostave", "opisDostave" })
public class Zahtev {
	
	@XmlElement(namespace = Namespaces.OSNOVA)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datum;
	
	@XmlElement(namespace = Namespaces.OSNOVA)
	private String mesto;
	
	@XmlElement(namespace = Namespaces.OSNOVA, name = "Gradjanin")
	private Gradjanin gradjanin;
	
	@XmlElement(namespace = Namespaces.OSNOVA, name = "OrganVlasti")
	private OrganVlasti organVlasti;
	
	private List<DeoDetalja> detalji;
	
	@XmlElement(namespace = Namespaces.OSNOVA)
	private String email;
	
	@XmlElement(namespace = Namespaces.OSNOVA)
	private String potpis;
	
	@XmlElement(namespace = Namespaces.OSNOVA)
	private StatusZahteva status;
	
	@XmlElement(namespace = Namespaces.DOKUMENT)
	private TipZahteva tipZahteva;
	
	@XmlElement(namespace = Namespaces.DOKUMENT)
	private TipDostave tipDostave;
	
	@XmlElement(namespace = Namespaces.DOKUMENT)
	private String opisDostave;
	
	public Zahtev() {
		super();
	}
	
	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public Gradjanin getGradjanin() {
		return gradjanin;
	}

	public void setGradjanin(Gradjanin gradjanin) {
		this.gradjanin = gradjanin;
	}

	public OrganVlasti getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(OrganVlasti organVlasti) {
		this.organVlasti = organVlasti;
	}

	public List<DeoDetalja> getDetalji() {
		return detalji;
	}

	public void setDetalji(List<DeoDetalja> detalji) {
		this.detalji = detalji;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPotpis() {
		return potpis;
	}

	public void setPotpis(String potpis) {
		this.potpis = potpis;
	}

	public StatusZahteva getStatus() {
		return status;
	}

	public void setStatus(StatusZahteva status) {
		this.status = status;
	}

	public TipZahteva getTipZahteva() {
		return tipZahteva;
	}

	public void setTipZahteva(TipZahteva tipZahteva) {
		this.tipZahteva = tipZahteva;
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
