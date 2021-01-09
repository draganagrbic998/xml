package com.example.demo.service.email;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.example.demo.common.MyException;
import com.example.demo.parser.DOMParser;

@Service
public class EmailService {
	
	@Autowired
	private DOMParser domParser;

	private static final String EMAIL_URL = "http://localhost:8083/email";
	private static final String CONTENT_TYPE = "text/xml";
	private static final String URL_ENCODING = "utf-8";
	
	public void sendEmail(Email email) {
		try {
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
		    HttpClient client = new HttpClient();
		    client.executeMethod(post);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
