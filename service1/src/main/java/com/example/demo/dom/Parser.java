package com.example.demo.dom;

import java.text.ParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Parser<T> {

	public T parse(Element element) throws ParseException;
	public Element parse(T type, Document document);
	public String getSchema();

}
