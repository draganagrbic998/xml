package com.example.demo.parser;

import org.w3c.dom.Node;

public interface Parser<T> {

	public T parse(Node node);
	
}
