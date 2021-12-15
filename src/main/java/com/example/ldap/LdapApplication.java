package com.example.ldap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class LdapApplication {

	public static void main(String[] args) {
		SpringApplication.run(LdapApplication.class, args);
	}
	@Autowired
	LdapService service;
	
	@EventListener(ApplicationReadyEvent.class)
	public void getAllList() {
	Optional<LdapPerson> name=service.search("chandan");
		System.out.println("Name ===> :"+name);
	}
}
