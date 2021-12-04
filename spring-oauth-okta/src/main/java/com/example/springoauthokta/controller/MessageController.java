package com.example.springoauthokta.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	@GetMapping("/")
	public String getMessage(Principal principal) {
		return "okta Authentication Successfull." + principal.getName();
	} 
}
