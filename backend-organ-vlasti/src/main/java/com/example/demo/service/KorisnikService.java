package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.demo.repository.KorisnikRepository;

@Service
public class KorisnikService implements UserDetailsService {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return this.korisnikRepository.findByEmail(username);
	}
	
}
