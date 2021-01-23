package com.example.demo.transformer;

import org.springframework.core.io.Resource;

import com.example.demo.enums.MetadataType;

public interface TransformerInterface {
	
	public String html(String documentId);
	public Resource pdf(String documentId);
	public byte[] byteHtml(String documentId);
	public byte[] bytePdf(String documentId);
	public Resource metadata(String documentId, MetadataType type);

}
