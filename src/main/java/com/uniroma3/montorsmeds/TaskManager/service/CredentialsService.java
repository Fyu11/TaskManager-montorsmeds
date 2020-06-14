package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniroma3.montorsmeds.TaskManager.authentication.AuthConfiguration;
import com.uniroma3.montorsmeds.TaskManager.model.Credentials;
import com.uniroma3.montorsmeds.TaskManager.repository.CredentialsRepository;


@Service 
public class CredentialsService {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;

	@Transactional
	public Credentials getCredentials(long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}

	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}

	@Transactional
	public List<Credentials> getAllCredentials(){
		List<Credentials> result = new ArrayList<>();
		Iterable<Credentials> iterable = this.credentialsRepository.findAll();
		for(Credentials c: iterable) {
			result.add(c);
		}
		return result;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
