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
@XmlType(propOrder = { "broj", "datum", "gradjanin", "organVlasti", "trazenaInformacija" })
@XmlSeeAlso({ZahtevUvid.class, ZahtevDostava.class})
public abstract class Zahtev implements DocumentEntity {
			
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String broj;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datum;
	
	@XmlElement(namespace = Namespaces.KORISNIK, required = true, name = "Korisnik")
	private Korisnik gradjanin;
	
	@XmlElement(namespace = Namespaces.ORGAN_VLASTI, required = true, name = "Organ_vlasti")
	private OrganVlasti organVlasti;
	
	@XmlElement(namespace = Namespaces.ZAHTEV, required = true, name = "trazena_informacija")
	private String trazenaInformacija;
	
	public Zahtev() {
		super();
	}

	public Zahtev(String broj, Date datum, Korisnik gradjanin, OrganVlasti organVlasti, String trazenaInformacija) {
		super();
		this.broj = broj;
		this.datum = datum;
		this.gradjanin = gradjanin;
		this.organVlasti = organVlasti;
		this.trazenaInformacija = trazenaInformacija;
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

	public Korisnik getGradjanin() {
		return gradjanin;
	}

	public void setGradjanin(Korisnik gradjanin) {
		this.gradjanin = gradjanin;
	}

	public OrganVlasti getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(OrganVlasti organVlasti) {
		this.organVlasti = organVlasti;
	}

	public String getTrazenaInformacija() {
		return trazenaInformacija;
	}

	public void setTrazenaInformacija(String trazenaInformacija) {
		this.trazenaInformacija = trazenaInformacija;
	}
	
	@Override
	public String getDocumentBroj() {
		return this.broj;
	}
	
	@Override
	public void setDocumentBroj(String broj) {
		this.broj = broj;
	}
		
}
