package com.example.springstarbucks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class WebController {
	@Autowired 
	private JdbcTemplate jdbc;

	@GetMapping("/")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "login";
	}

	@PostMapping("/")
    public String login(){
		   String sql = "INSERT INTO user (username, pw) VALUES (?, ?)";
		   int result = jdbc.update(sql, "jason", "jason");
           return "login";
	}
}

