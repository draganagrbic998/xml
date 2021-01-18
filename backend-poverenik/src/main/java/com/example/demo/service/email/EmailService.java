package com.example.demo.service.email;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.MyException;
import com.example.demo.parser.JAXBParser;

@Service
public class EmailService {
	
	@Autowired
	private JAXBParser jaxbParser;

	private static final String EMAIL_URL = "http://localhost:8083/email";
	private static final String CONTENT_TYPE = "text/xml";
	private static final String URL_ENCODING = "utf-8";
	
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
	
}
