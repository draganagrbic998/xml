package com.example.demo.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement(name = "IzvestajPodaci")
@XmlAccessorType(XmlAccessType.FIELD)
public class IzvestajPodaci {

	private String zalbeCutanje;

	private String zalbeOdbijanje;

	private String zalbeDelimicnost;

	public IzvestajPodaci() {
		super();
		this.zalbeCutanje = "0";
		this.zalbeOdbijanje = "0";
		this.zalbeDelimicnost = "0";
	}

	public String getZalbeCutanje() {
		return zalbeCutanje;
	}

	public void setZalbeCutanje(String zalbeCutanje) {
		this.zalbeCutanje = zalbeCutanje;
	}

	public String getZalbeOdbijanje() {
		return zalbeOdbijanje;
	}

	public void setZalbeOdbijanje(String zalbeOdbijanje) {
		this.zalbeOdbijanje = zalbeOdbijanje;
	}

	public String getZalbeDelimicnost() {
		return zalbeDelimicnost;
	}

	public void setZalbeDelimicnost(String zalbeDelimicnost) {
		this.zalbeDelimicnost = zalbeDelimicnost;
	}

}
