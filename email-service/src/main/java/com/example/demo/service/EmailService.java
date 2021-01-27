package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.demo.model.Email;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.utils.Utils;

@Service
public class EmailService {

	@Autowired
	private JavaMailSenderImpl sender;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private DOMParser domParser;
		
	@Async
	public void sendEmail(String xml) {
		Document document = this.domParser.buildDocument(xml);
		Email email = (Email) this.jaxbParser.unmarshalFromDoc(document, Email.class);

	    MimeMessagePreparator preparator = new MimeMessagePreparator() 
	    {
			@Override
	        public void prepare(MimeMessage mimeMessage) throws Exception 
	        {
	            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
	            mimeMessage.setSubject(email.getSubject());
	            mimeMessage.setText(email.getText());
	            
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	            helper.setText(email.getText());
	            
	            if (document.getElementsByTagName("pdf").getLength() > 0) {
		    		byte[] decodedString = Base64.getDecoder().decode(document.getElementsByTagName("pdf").item(0).getTextContent());
		    		File file = new File(Utils.nextDocumentId() + ".pdf");
		    		FileOutputStream fout = new FileOutputStream(file);
		    		fout.write(decodedString);
		    		fout.close();
	            	FileSystemResource resource = new FileSystemResource(file.getPath());
	            	try {
		            	helper.addAttachment(file.getPath(), resource);
	            	}
	            	catch(Exception e) {
	            		;
	            	}	
	            }
	            if (document.getElementsByTagName("html").getLength() > 0) {
		    		byte[] decodedString = Base64.getDecoder().decode(document.getElementsByTagName("html").item(0).getTextContent());
		    		File file = new File(Utils.nextDocumentId() + ".html");
		    		FileOutputStream fout = new FileOutputStream(file);
		    		fout.write(decodedString);
		    		fout.close();
	            	FileSystemResource resource = new FileSystemResource(file.getPath());
	            	try {
		            	helper.addAttachment(file.getPath(), resource);
	            	}
	            	catch(Exception e) {
	            		;
	            	}	
	            }
	            
	        }
	    };
	    try {
	    	this.sender.send(preparator);
	    }
	    catch(Exception e) {
	    	;
	    }
	}
}
