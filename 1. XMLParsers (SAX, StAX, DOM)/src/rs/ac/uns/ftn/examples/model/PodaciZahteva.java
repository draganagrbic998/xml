package rs.ac.uns.ftn.examples.model;

public class PodaciZahteva {
	
	private TipPrava tip_prava;
	private TipDostave tip_dostave;
	private String opis_dostave;
	
	public PodaciZahteva() {
		super();
	}
	
	@Override
	public String toString() {
		return String.format("Tip prava: %s, tip dostave: %s, opis dostave: %s", this.tip_prava, this.tip_dostave, this.opis_dostave);
	}

	public TipPrava getTip_prava() {
		return tip_prava;
	}

	public void setTip_prava(TipPrava tip_prava) {
		this.tip_prava = tip_prava;
	}

	public TipDostave getTip_dostave() {
		return tip_dostave;
	}

	public void setTip_dostave(TipDostave tip_dostave) {
		this.tip_dostave = tip_dostave;
	}

	public String getOpis_dostave() {
		return opis_dostave;
	}

	public void setOpis_dostave(String opis_dostave) {
		this.opis_dostave = opis_dostave;
	}

}
