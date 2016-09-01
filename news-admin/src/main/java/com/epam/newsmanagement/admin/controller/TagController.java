package com.epam.newsmanagement.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TagController {

	@RequestMapping(value = "addtag", method = RequestMethod.GET)
	public String getAddTagPage(){
		return "addtag";
	}
	
}
