package com.epam.newsmanagement.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * controller for works news
 * @author Andrei_Krukouski
 *
 */

@Controller
public class NewsController {



    @RequestMapping(value = "/news", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllNews(){
        return "news";
    }
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainPage(Model model) {
		return "redirect:/login";
	}


}