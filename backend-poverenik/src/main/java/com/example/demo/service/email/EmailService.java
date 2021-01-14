package com.example.demo.service.email;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.parser.DOMParser;

@Service
public class EmailService {
	
	@Autowired
	private DOMParser domParser;

	private static final String EMAIL_URL = "http://localhost:8083/email";
	private static final String CONTENT_TYPE = "text/xml";
	private static final String URL_ENCODING = "utf-8";
	private static final String STUB_FILE = Constants.STUB_FOLDER + "email.xml";
	
	public void sendEmail(Email email) {
		try {
			Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
			document.getElementsByTagName("to").item(0).setTextContent(email.getTo());
			document.getElementsByTagName("subject").item(0).setTextContent(email.getSubject());
			document.getElementsByTagName("text").item(0).setTextContent(email.getText());
			
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
