package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import com.example.demo.common.Namespaces;
import com.example.demo.model.enums.TipDelimicnosti;

@XmlRootElement(name = "Zalba", namespace = Namespaces.ZALBA)
@XmlType(propOrder = { "odluka", "tipDelimicnosti", "obrazlozenje" })
@XmlDiscriminatorValue("zalba:TZalba_delimicnost")
public class ZalbaDelimicnost extends Zalba {

	@XmlElement(name = "Odluka", namespace = Namespaces.ODLUKA, required = true)
	private Odluka odluka;
	
	@XmlElement(name = "tip_delimicnosti", namespace = Namespaces.ZALBA, required = true)
	private TipDelimicnosti tipDelimicnosti;
	
	@XmlElement(namespace = Namespaces.ZALBA, required = true)
	private String obrazlozenje;

	public ZalbaDelimicnost() {
		super();
	}

	public ZalbaDelimicnost(String broj, Date datum, 
			Odluka odluka, TipDelimicnosti tipDelimicnosti, String obrazlozenje) {
		super(broj, datum);
		this.odluka = odluka;
		this.tipDelimicnosti = tipDelimicnosti;
		this.obrazlozenje = obrazlozenje;
	}

	public Odluka getOdluka() {
		return odluka;
	}

	public void setOdluka(Odluka odluka) {
		this.odluka = odluka;
	}

	public TipDelimicnosti getTipDelimicnosti() {
		return tipDelimicnosti;
	}

	public void setTipDelimicnosti(TipDelimicnosti tipDelimicnosti) {
		this.tipDelimicnosti = tipDelimicnosti;
	}

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

}
