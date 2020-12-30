package com.example.demo.service.email;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.example.demo.parser.DOMParser;

@Service
public class EmailService {
	
	private static final String EMAIL_URL = "http://localhost:8083/email";
	private static final String CONTENT_TYPE = "text/xml";
	private static final String URL_ENCODING = "utf-8";
	
	@Autowired
	private DOMParser domParser;

	public void sendEmail(Email email) throws ParserConfigurationException, TransformerException, HttpException, IOException {
		Document document = this.domParser.emptyDocument();
		Node emailNode = document.createElement("email");
		document.appendChild(emailNode);
		Node to = document.createElement("to");
		to.setTextContent(email.getTo());
		emailNode.appendChild(to);
		Node subject = document.createElement("subject");
		subject.setTextContent(email.getSubject());
		emailNode.appendChild(subject);
		Node text = document.createElement("text");
		text.setTextContent(email.getText());
		emailNode.appendChild(text);
		
	    PostMethod post = new PostMethod(EMAIL_URL);
	    RequestEntity entity = new StringRequestEntity(this.domParser.buildXml(document), CONTENT_TYPE, URL_ENCODING);
	    post.setRequestEntity(entity);
	    HttpClient httpclient = new HttpClient();
	    httpclient.executeMethod(post);
	}
	
}
