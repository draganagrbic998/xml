package com.example.demo.controller;

public class TokenDTO {

	private String token;
	private String uloga;

	public TokenDTO() {
		super();
	}

	public TokenDTO(String token, String uloga) {
		super();
		this.token = token;
		this.uloga = uloga;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}
	
}
