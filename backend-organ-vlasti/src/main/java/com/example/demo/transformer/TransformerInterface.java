package com.example.demo.transformer;

import org.springframework.core.io.Resource;

import com.example.demo.enums.MetadataType;

public interface TransformerInterface {
	
	public String html(String documentId);
	public Resource generateHtml(String documentId);
	public Resource generatePdf(String documentId);
	public Resource generateMetadata(String documentId, MetadataType type);

}
