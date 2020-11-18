package rs.ac.uns.ftn.examples.dom;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rs.ac.uns.ftn.examples.model.Zahtev;

public class ZahtevParser {
	
	public Zahtev parseZahtev(Element element) {

		NamedNodeMap attributes = element.getAttributes();	//preskocicu atribute za sad
		String email = element.getElementsByTagName("osoba:email").item(0).getChildNodes().item(0).getNodeValue();
		String lozinka = element.getElementsByTagName("osoba:lozinka").item(0).getChildNodes().item(0).getNodeValue();
		String ime = element.getElementsByTagName("osoba:ime").item(0).getChildNodes().item(0).getNodeValue();
		String prezime = element.getElementsByTagName("osoba:prezime").item(0).getChildNodes().item(0).getNodeValue();
		
		Node mama = element.getElementsByTagName("osoba:Korisnik").item(0);
		System.out.println(mama);
		NodeList tata = element.getChildNodes();
		for (int i = 0; i < tata.getLength(); i++) {
			Node aChild = tata.item(i);
			System.out.println(aChild.getNodeName());
		}

		Element korisnik = (Element) element.getElementsByTagName("osoba:Korisnik").item(0);
		System.out.println(korisnik);
		String mesto = korisnik.getElementsByTagName("osoba:mesto").item(0).getChildNodes().item(0).getNodeValue();
		String postanski_broj = korisnik.getElementsByTagName("osoba:postanski_broj").item(0).getChildNodes().item(0).getNodeValue();
		String ulica = korisnik.getElementsByTagName("osoba:ulica").item(0).getChildNodes().item(0).getNodeValue();
		String broj = korisnik.getElementsByTagName("osoba:broj").item(0).getChildNodes().item(0).getNodeValue();
		
		String naziv = element.getElementsByTagName("osoba:naziv").item(0).getChildNodes().item(0).getNodeValue();
		String tip_prava = element.getElementsByTagName("osoba:tip_prava").item(0).getChildNodes().item(0).getNodeValue();
		String tip_dostave = element.getElementsByTagName("osoba:tip_dostave").item(0).getChildNodes().item(0).getNodeValue();
		String opis_dostave = element.getElementsByTagName("osoba:opis_dostave").item(0).getChildNodes().item(0).getNodeValue();
		
		Element organ_vlasti = (Element) element.getElementsByTagName("organ_vlasti:Organ_vlasti").item(0);
		String mestoORG = organ_vlasti.getElementsByTagName("osoba:mesto").item(0).getChildNodes().item(0).getNodeValue();
		String postanski_brojORG = organ_vlasti.getElementsByTagName("osoba:postanski_broj").item(0).getChildNodes().item(0).getNodeValue();
		String ulicaORG = organ_vlasti.getElementsByTagName("osoba:ulica").item(0).getChildNodes().item(0).getNodeValue();
		String brojORG = organ_vlasti.getElementsByTagName("osoba:broj").item(0).getChildNodes().item(0).getNodeValue();

		
		
		return null;
		
		/*
		if (attributes.getLength() > 0) {
			System.out.print(", ATTRIBUTES: ");
			for (int i = 0; i < attributes.getLength(); i++) {
				Node attribute = attributes.item(i);
				printNode(attribute);
				if (i < attributes.getLength()-1)
        			System.out.print(", ");
			}
		}
		
		System.out.println();
		
		NodeList children = element.getChildNodes();
		if (children != null) {
			for (int i = 0; i < children.getLength(); i++) {
				Node aChild = children.item(i);
				printNode(aChild);
			}
		}*/

	}

}
