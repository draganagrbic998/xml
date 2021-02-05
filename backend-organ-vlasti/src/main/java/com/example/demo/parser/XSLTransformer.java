package com.example.demo.parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.MetadataType;
import com.example.demo.exception.MyException;
import com.example.demo.fuseki.SparqlUtil;
import com.example.demo.mapper.OdlukaMapper;
import com.example.demo.mapper.ZalbaMapper;

@Component
public class XSLTransformer {

	@Autowired
	private DOMParser domParser;

	private ByteArrayOutputStream plainHtml(Document document, String xslPath) {
		try {
			StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(document)));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Transformer transformer = this.domParser.getTransformerFactory()
					.newTransformer(new StreamSource(new File(xslPath)));
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			StreamResult output = new StreamResult(out);
			transformer.transform(in, output);
			return out;
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	private ByteArrayOutputStream plainPdf(Document document, String xslFoPath) {
		try {
			StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(document)));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Transformer transformer = this.domParser.getTransformerFactory()
					.newTransformer(new StreamSource(new File(xslFoPath)));
			FopFactory foFactory = FopFactory.newInstance(new File(Constants.FOP_CONF));
			FOUserAgent foUserAgent = foFactory.newFOUserAgent();
			Fop fop = foFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
			Result output = new SAXResult(fop.getDefaultHandler());
			transformer.transform(in, output);
			return out;
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	public String html(Document document, String xslPath) {
		return this.plainHtml(document, xslPath).toString();
	}

	public Resource pdf(Document document, String xslFoPath, String genPath) {
		try {
			Path file = Paths.get(genPath + Utils.getBroj(document) + ".pdf");
			Files.write(file, this.plainPdf(document, xslFoPath).toByteArray());
			return new UrlResource(file.toUri());
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	public byte[] byteHtml(Document document, String xslPath) {
		return this.plainHtml(document, xslPath).toByteArray();
	}

	public byte[] bytePdf(Document document, String xslFoPath) {
		return this.plainPdf(document, xslFoPath).toByteArray();
	}

	public String metadata(String subject, ResultSet results, MetadataType type) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (type.equals(MetadataType.xml)) {
			Model model = ModelFactory.createDefaultModel();
			model.setNsPrefix("base", Namespaces.BASE);
			model.setNsPrefix("pred", Namespaces.PREDIKAT);

			while (results.hasNext()) {
				QuerySolution qs = results.next();
				RDFNode object = qs.get("object");

				if (object.isResource()) {
					model.add(model.createResource(subject), model.createProperty(qs.get("predicate").asResource().getURI()),
							model.createResource(object.asResource().getURI()));
				}
				else {
					model.add(model.createResource(subject), model.createProperty(qs.get("predicate").asResource().getURI()),
							model.createLiteral(object.asLiteral().getString()));
				}
			}
			model.write(out, SparqlUtil.RDF_XML);
		} 
		
		else {
			ResultSetFormatter.outputAsJSON(out, results);
		}
		return out.toString();
	}

	public Model model(Document document) {
		try {
			StreamSource in = new StreamSource(new StringReader(this.domParser.buildXml(document)));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Transformer transformer = this.domParser.getTransformerFactory()
					.newTransformer(new StreamSource(new File(Constants.GRDDL_XSL)));
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult output = new StreamResult(out);
			transformer.transform(in, output);
			Model model = ModelFactory.createDefaultModel();
			model.read(new StringReader(out.toString()), null);
			this.setTypes(document, model);
			return model;
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	private void setTypes(Document document, Model model) {
		if (document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").getLength() > 0) {
			model.add(model.createStatement(model.createResource(Utils.getId(document)),
					model.createProperty(Namespaces.PREDIKAT + "tip"),
					model.createLiteral(ZalbaMapper.getTipZalbe(document) + "")));
		} else if (document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").getLength() > 0) {
			model.add(model.createStatement(model.createResource(Utils.getId(document)),
					model.createProperty(Namespaces.PREDIKAT + "tip"),
					model.createLiteral(OdlukaMapper.getTipOdluke(document) + "")));
		}
		model.add(model.createStatement(model.createResource(Utils.getId(document)),
				model.createProperty(Namespaces.RDF + "type"),
				model.createResource(document.getFirstChild().getNamespaceURI())));
	}

}
