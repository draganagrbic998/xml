package rs.ac.uns.ftn.examples.model;

public class Korisnik {
	
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private Adresa adresa;
	
	public Korisnik() {
		super();
	}
	
	@Override
	public String toString() {
		return String.format("Email: %s, lozinka: %s, ime: %s, prezime: %s, adresa: %s", this.email, this.lozinka, this.ime, this.prezime, this.adresa);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

}
