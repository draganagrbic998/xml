package rs.ac.uns.ftn.examples.model;

public class Zahtev {

	private String broj;
	private String datum;
	private Korisnik korisnik;
	private OrganVlasti organ_vlasti;
	private PodaciZahteva podaci_zahteva;

	public Zahtev() {
		super();
	}
	
	@Override
	public String toString() {
		String suma = String.format("Broj: %s, datum: %s", this.broj, this.datum);
		suma += "\n====================\nPODACI O KORISNIKU\n";
		suma += this.korisnik.toString();
		suma += "\n====================\nPODACI O ORGANU VLASTI\n";
		suma += this.organ_vlasti.toString();
		suma += "\n====================\nPODACI O ORGANU ZAHTEVU\n";
		suma += this.podaci_zahteva.toString();
		return suma;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public OrganVlasti getOrgan_vlasti() {
		return organ_vlasti;
	}

	public void setOrgan_vlasti(OrganVlasti organ_vlasti) {
		this.organ_vlasti = organ_vlasti;
	}
	
	public PodaciZahteva getPodaci_zahteva() {
		return podaci_zahteva;
	}

	public void setPodaci_zahteva(PodaciZahteva podaci_zahteva) {
		this.podaci_zahteva = podaci_zahteva;
	}

}
