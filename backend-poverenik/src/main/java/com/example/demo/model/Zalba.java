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
@XmlType(propOrder = { "gradjanin", "datum", "detalji", "kontakt", "potpis", "odgovoreno", "organVlasti", "datumZahteva", "kopijaZahteva", "kopijaOdluke" })
@XmlSeeAlso({ZalbaCutanje.class, ZalbaOdbijanje.class})
public abstract class Zalba {

	@XmlElement(namespace = Namespaces.OSNOVA, required = true, name = "Gradjanin")
	private Gradjanin gradjanin;
		
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datum;

	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String detalji;
		
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String kontakt;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private String potpis;
	
	@XmlElement(namespace = Namespaces.OSNOVA, required = true)
	private boolean odgovoreno;
	
	@XmlElement(namespace = Namespaces.POVERENIK, required = true)
	private String organVlasti;

	@XmlElement(namespace = Namespaces.POVERENIK, required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Date datumZahteva;

	@XmlElement(namespace = Namespaces.POVERENIK, required = true)
	private String kopijaZahteva;

	@XmlElement(namespace = Namespaces.POVERENIK, required = false)
	private String kopijaOdluke;

	public Zalba() {
		super();
	}

	public Gradjanin getGradjanin() {
		return gradjanin;
	}

	public void setGradjanin(Gradjanin gradjanin) {
		this.gradjanin = gradjanin;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getDetalji() {
		return detalji;
	}

	public void setDetalji(String detalji) {
		this.detalji = detalji;
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

	public String getOrganVlasti() {
		return organVlasti;
	}

	public void setOrganVlasti(String organVlasti) {
		this.organVlasti = organVlasti;
	}

	public Date getDatumZahteva() {
		return datumZahteva;
	}

	public void setDatumZahteva(Date datumZahteva) {
		this.datumZahteva = datumZahteva;
	}

	public String getKopijaZahteva() {
		return kopijaZahteva;
	}

	public void setKopijaZahteva(String kopijaZahteva) {
		this.kopijaZahteva = kopijaZahteva;
	}

	public String getKopijaOdluke() {
		return kopijaOdluke;
	}

	public void setKopijaOdluke(String kopijaOdluke) {
		this.kopijaOdluke = kopijaOdluke;
	}
	
}
