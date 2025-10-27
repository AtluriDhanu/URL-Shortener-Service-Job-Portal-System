package com.url_shortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.url_shortener.entity.Url;
import com.url_shortener.service.UrlService;

@Controller
public class UrlController {
	
	@Autowired
	private UrlService urlService;
	
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@GetMapping ("/home")
	public String home() {
		return "index";
	}
	
	 @PostMapping ("/shorten")
	 public String shortenUrl(@RequestParam String originalUrl, @RequestParam int maxClicks, @RequestParam int durationDays, Model model) {
	     Url url = urlService.generateShortUrl(originalUrl, maxClicks, durationDays);
	     model.addAttribute("shortUrl", url.getShortUrl());
	     model.addAttribute("qrPath", url.getQrCodePath());
	     return "result";
	 }
	 
	 @GetMapping("/{hash}")
	 public String redirect(@PathVariable String hash) {
	     String result = urlService.redirectToOriginalUrl(hash);
	     if (result.equals("error")) { 
	      	return "error";
	     }
	     if (result.equals("expired")) {
	       	return "error";
	     }
	     return "redirect:" + result;
	 }
}

