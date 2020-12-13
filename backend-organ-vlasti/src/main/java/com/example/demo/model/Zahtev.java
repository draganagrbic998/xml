package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import com.example.demo.constants.Namespaces;
import com.example.demo.parser.JAXBDateAdapter;

@XmlDiscriminatorNode("@xsi:type")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "osoba", "adresa", "detalji", "datum", "kontakt", "potpis", "odgovoreno", "organVlasti" })
@XmlSeeAlso({ZahtevUvid.class, ZahtevDostava.class})
public abstract class Zahtev {
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Osoba")
	private Osoba osoba;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Adresa")
	private Adresa adresa;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String detalji;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datum;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String kontakt;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String potpis;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private boolean odgovoreno;
	
	@XmlElement(namespace = Namespaces.ORGAN_VLASTI, required = true, name = "OrganVlasti")
	private OrganVlasti organVlasti;

	public Zahtev() {
		super();
	}

	public Osoba getOsoba() {
		return osoba;
	}

	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public String getDetalji() {
		return detalji;
	}

	public void setDetalji(String detalji) {
		this.detalji = detalji;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getKontakt() {
		return kontakt;
	}

	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}

	public String getPotpis() {
		return potpis;
	}

	public void setPotpis(String potpis) {
		this.potpis = potpis;
	}

	public boolean isOdgovoreno() {
		return odgovoreno;
	}

	public void setOdgovoreno(boolean odgovoreno) {
		this.odgovoreno = odgovoreno;
	}

	public OrganVlasti getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(OrganVlasti organVlasti) {
		this.organVlasti = organVlasti;
	}
	
}
