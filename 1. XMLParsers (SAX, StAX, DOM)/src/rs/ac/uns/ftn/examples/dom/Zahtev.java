package rs.ac.uns.ftn.examples.dom;

public class Zahtev {

	private String broj;
	private String datum;
	private Korisnik korisnik;
	private OrganVlasti organ_vlasti;
	private PodaciZahteva podaci_zahteva;
	
	public PodaciZahteva getPodaci_zahteva() {
		return podaci_zahteva;
	}

	public void setPodaci_zahteva(PodaciZahteva podaci_zahteva) {
		this.podaci_zahteva = podaci_zahteva;
	}

	public Zahtev() {
		super();
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

	
}
