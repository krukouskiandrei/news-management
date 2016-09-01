package com.epam.newsmanagement.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.newsmanagement.common.entity.NewsInfo;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.NewsService;

/**
 * controller for works news
 * @author Andrei_Krukouski
 *
 */

@Controller
public class NewsController {

	@Autowired
	NewsService newsService;

    @RequestMapping(value = "news", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllNews(Model model){
    	List<NewsInfo> listNewsInfo = null;
    	try{
    		listNewsInfo = newsService.getAllNewsWithInfo();    	
    	}catch(ServiceException e){
    		
    	}
    	model.addAttribute("listNewsInfo", listNewsInfo);
    	return "news";
    }
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainPage(Model model) {
		return "redirect:/login";
	}
    
    @RequestMapping(value = "addnews", method = RequestMethod.GET)
    public String getAddNewsPage(){
    	return "addnews";
    }
    


}