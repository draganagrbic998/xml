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

@Service
public class EmailService {

	private static final String EMAIL_URL = "http://localhost:8083/email";
	private static final String CONTENT_TYPE = "text/xml";
	private static final String URL_ENCODING = "utf-8";

	@Autowired
	private JAXBParser jaxbParser;

	@Autowired
	private DOMParser domParser;
			
	public void sendEmail(Email email) {
		try {			
		    PostMethod post = new PostMethod(EMAIL_URL);
		    RequestEntity entity = new StringRequestEntity(this.jaxbParser.marshalToXml(email), CONTENT_TYPE, URL_ENCODING);
		    post.setRequestEntity(entity);
		    HttpClient client = new HttpClient();
		    client.executeMethod(post);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public void sendEmail(Email email, byte[] html, byte[] pdf) {
		try {
			Document document = this.jaxbParser.marshalToDoc(email);
			Node htmlNode = document.createElement("html");
			htmlNode.setTextContent(Base64.getEncoder().encodeToString(html));
			document.getElementsByTagName("email").item(0).appendChild(htmlNode);
			Node pdfNode = document.createElement("pdf");
			pdfNode.setTextContent(Base64.getEncoder().encodeToString(pdf));
			document.getElementsByTagName("email").item(0).appendChild(pdfNode);
			
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
