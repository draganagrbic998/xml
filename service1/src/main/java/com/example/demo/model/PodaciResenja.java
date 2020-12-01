package com.example.demo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Podaci_resenja", namespace = "https://github.com/draganagrbic998/xml/resenje")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "odbrana", "dispozitiva", "obrazlozenje" })
public class PodaciResenja {

	@XmlElement(name = "Odbrana", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	private Odbrana odbrana;

	@XmlElementWrapper(name="Dispozitiva", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	@XmlElement(name = "Clan_dispozitive", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	private List<String> dispozitiva;
	
	@XmlElementWrapper(name="Obrazlozenje", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	@XmlElement(name = "Deo_obrazlozenja", namespace = "https://github.com/draganagrbic998/xml/resenje", required = true)
	private List<String> obrazlozenje;
	
	public PodaciResenja() {
		super();
	}

	public PodaciResenja(Odbrana odbrana, List<String> dispozitiva, List<String> obrazlozenje) {
		super();
		this.setOdbrana(odbrana);
		this.setDispozitiva(dispozitiva);
		this.setObrazlozenje(obrazlozenje);
	}
	
	@Override
	public String toString() {
		String suma = "PODACI PODATAKA RESENJA:\n";
		suma += odbrana.toString();
		
		suma += "Dispozitiva:\n";
		for (String d : dispozitiva)
			suma += d + "\n";

		suma += "Obrazlozenje:\n";
		for (String o : obrazlozenje)
			suma += o + "\n";
		
		suma += odbrana.toString();

		return suma;
	}

	public Odbrana getOdbrana() {
		return odbrana;
	}

	public void setOdbrana(Odbrana odbrana) {
		this.odbrana = odbrana;
	}

	public List<String> getDispozitiva() {
		return dispozitiva;
	}

	public void setDispozitiva(List<String> dispozitiva) {
		this.dispozitiva = dispozitiva;
	}

	public List<String> getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(List<String> obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}
	
}
