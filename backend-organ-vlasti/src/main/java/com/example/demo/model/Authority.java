package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class Authority implements GrantedAuthority {

	private String name;

	public Authority() {
		super();
	}

	public Authority(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
