package com.example.demo.service.email;

import java.util.Base64;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.transformer.ZahtevTransformer;

@Service
public class EmailService {
	
	@Autowired
	private JAXBParser jaxbParser;

	private static final String EMAIL_URL = "http://localhost:8083/email";
	private static final String CONTENT_TYPE = "text/xml";
	private static final String URL_ENCODING = "utf-8";
	
	@Autowired
	private ZahtevTransformer zahtevTransformer;
	
	@Autowired
	private DOMParser domParser;
	
	public void sendEmail(Email email) {
		try {			
			Document document = this.jaxbParser.marshalToDoc(email);
			/*
			Node pdf = document.createElement("pdf");
			pdf.setTextContent(Base64.getEncoder().encodeToString(this.zahtevTransformer.plainPdf("1")));
			document.getElementsByTagName("email").item(0).appendChild(pdf);
			Node xhtml = document.createElement("xhtml");
			xhtml.setTextContent(Base64.getEncoder().encodeToString(this.zahtevTransformer.plainHtml("1")));
			document.getElementsByTagName("email").item(0).appendChild(xhtml);
			*/
			
		    PostMethod post = new PostMethod(EMAIL_URL);
		    RequestEntity entity = new StringRequestEntity(this.domParser.buildXml(document), CONTENT_TYPE, URL_ENCODING);
		    post.setRequestEntity(entity);
		    HttpClient client = new HttpClient();
		    client.executeMethod(post);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
