package com.example.demo.exist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtilities {

	@Value("${exist.user}")
	private String user;
	
	@Value("${exist.password}")
	private String password;
	
	@Value("${exist.host}")
	private String host;
	
	@Value("${exist.port}")
	private int port;
	
	@Value("${exist.driver}")
	private String driver;
	
	@Value("${exist.uri}")
	private String uri;

	public AuthenticationUtilities() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
