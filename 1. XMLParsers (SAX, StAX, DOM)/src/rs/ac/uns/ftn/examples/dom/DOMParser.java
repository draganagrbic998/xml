package rs.ac.uns.ftn.examples.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.apache.xerces.jaxp.JAXPConstants.*;

public class DOMParser implements ErrorHandler {

	private static DocumentBuilderFactory factory;
	private Document document;

	static {
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
	}
	
	public void buildDocument(String filePath) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(this);
		document = builder.parse(new File(filePath)); 
		if (document != null)
			System.out.println("[INFO] File parsed with no errors.");
		else
			System.out.println("[WARN] Document is null.");
	}

	public void printElement() {
		printNode(document);
	}

	private void printNode(Node node) {
		
		if (node == null)
			return;

		if (node instanceof Document) {
			System.out.println("START_DOCUMENT");
			Document doc = (Document) node;
			printNode(doc.getDocumentElement());
			
		} else if (node instanceof Element) {
			
			Element element = (Element) node;
			System.out.print("START_ELEMENT: " + element.getTagName());
			NamedNodeMap attributes = element.getAttributes();

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
			}
		} 	

		else if (node instanceof Attr) {
			Attr attr = (Attr) node;
			System.out.print(attr.getName() + "=" + attr.getValue());
		}
		
		else if (node instanceof Text) {
			Text text = (Text) node;
			if (text.getTextContent().trim().length() > 0)
				System.out.println("CHARACTERS: " + text.getTextContent().trim());
			
		}
		
		else if (node instanceof CDATASection) {
			System.out.println("CDATA: " + node.getNodeValue());
		}
		
		else if (node instanceof Comment) {
			System.out.println("COMMENT: " + node.getNodeValue());
		}
		
		else if (node instanceof ProcessingInstruction) {
			System.out.print("PROCESSING INSTRUCTION: ");
			ProcessingInstruction instruction = (ProcessingInstruction) node;
			System.out.print("data: " + instruction.getData());
			System.out.println(", target: " + instruction.getTarget());
		}
		
		else if (node instanceof Entity) {
			Entity entity = (Entity) node;
			System.out.println("ENTITY: " + entity.getNotationName());
		}
	}
	
	@Override
	public void error(SAXParseException err) throws SAXParseException {
		throw err;
	}

	@Override
	public void fatalError(SAXParseException err) throws SAXException {
		throw err;
	}
	
	@Override
    public void warning(SAXParseException err) throws SAXParseException {
    	System.out.println("[WARN] Warning, line: " + err.getLineNumber() + ", uri: " + err.getSystemId());
        System.out.println("[WARN] " + err.getMessage());
    }

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
		String filePath = "data/xml/zahtev.xml";
		DOMParser handler = new DOMParser();
		handler.buildDocument(filePath);
		handler.printElement();
	}
}
