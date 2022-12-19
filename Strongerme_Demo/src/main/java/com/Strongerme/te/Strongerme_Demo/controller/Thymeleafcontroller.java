package com.Strongerme.te.Strongerme_Demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Thymeleafcontroller")
public class Thymeleafcontroller {
	
	
	@GetMapping("/hello")
	public String Demothymeleaf(Model model) {
		model.addAttribute("name","Anup");
		model.addAttribute("Date",new Date().toLocaleString());
		model.addAttribute("a","5");
		model.addAttribute("b","1");
		
		List<String> names = List.of("ankit","anup","rajesh","somit");
		
		model.addAttribute("list",names);
		
		
		return "thymleaf";
	}

}
