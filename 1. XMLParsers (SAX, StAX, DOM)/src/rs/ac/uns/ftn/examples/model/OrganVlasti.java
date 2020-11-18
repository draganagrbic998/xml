package rs.ac.uns.ftn.examples.model;

public class OrganVlasti {
	
	private String naziv;
	private Adresa adresa;
	
	public OrganVlasti() {
		super();
	}
	
	@Override
	public String toString() {
		return String.format("Naziv: %s, adresa: %s", this.naziv, this.adresa);
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

}
