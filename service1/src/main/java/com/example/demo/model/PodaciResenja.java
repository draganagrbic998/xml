package com.example.demo.model;

import java.util.List;

public class PodaciResenja {

	private Odbrana odbrana;
	private List<String> dispozitiva;
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
