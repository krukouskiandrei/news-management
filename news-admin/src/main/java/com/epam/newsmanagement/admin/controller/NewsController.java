package com.epam.newsmanagement.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.entity.NewsInfo;
import com.epam.newsmanagement.common.entity.SearchParameter;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.AuthorService;
import com.epam.newsmanagement.common.service.NewsService;
import com.epam.newsmanagement.common.service.TagService;

/**
 * controller for works news
 * @author Andrei_Krukouski
 *
 */

@Controller
public class NewsController {

	@Autowired
	NewsService newsService;
	@Autowired
	AuthorService authorService;
	@Autowired
	TagService tagService;
	
    @RequestMapping(value = "news", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllNews(Model model){
    	List<NewsInfo> listNewsInfo = null;
    	List<Author> listAuthor = null;
    	List<Tag> listTag = null;
    	Long countNews = null;
    	int numberNews = 3;
    	try{
    		listNewsInfo = newsService.paginationNews(1, numberNews);
    	}catch(ServiceException e){
    		
    	}
    	try{
    		listAuthor = authorService.getAll();
    	}catch(ServiceException e){
    		
    	}
    	try{
    		listTag = tagService.getAll();
    	}catch(ServiceException e){
    		
    	}
    	try{
    		countNews = newsService.countAll();
    	}catch(ServiceException e){
    		
    	}
    	model.addAttribute("countNews", countNews);
    	model.addAttribute("pageNum", 1);
    	model.addAttribute("listNewsInfo", listNewsInfo);
    	model.addAttribute("listAuthors", listAuthor);
    	model.addAttribute("searchParameter", new SearchParameter());
    	model.addAttribute("listTags", listTag);
    	return "news";
    }
    
    @RequestMapping(value = "/page/{numPage}", method = RequestMethod.GET)
    public String redirectOnPage(@PathVariable int numPage, Model model){
    	List<NewsInfo> listNewsInfo = null;
    	int numberNews = 3;
    	List<Author> listAuthor = null;
    	List<Tag> listTag = null;
    	Long countNews = null;
    	try{
    		listNewsInfo = newsService.paginationNews(numPage == 1 ? 1 :
    			(numPage-1) * numberNews + 1, numPage == 1 ? 
    					numberNews : (numPage-1) * numberNews + numberNews);
    	}catch(ServiceException e){
    		
    	}
    	try{
    		listAuthor = authorService.getAll();
    	}catch(ServiceException e){
    		
    	}
    	try{
    		listTag = tagService.getAll();
    	}catch(ServiceException e){
    		
    	}
    	try{
    		countNews = newsService.countAll();
    	}catch(ServiceException e){
    		
    	}
    	model.addAttribute("pageNum", numPage);
    	model.addAttribute("listNewsInfo", listNewsInfo);
    	model.addAttribute("countNews", countNews);
    	model.addAttribute("listAuthors", listAuthor);
    	model.addAttribute("searchParameter", new SearchParameter());
    	model.addAttribute("listTags", listTag);
    	
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
    
    @RequestMapping(value = "filternews", method = RequestMethod.POST)
    public String filteredNews(@ModelAttribute("searchParameter")SearchParameter searchParameter, 
    		BindingResult result, Model model){
    	if(result.hasErrors()){
    		return "error";
    	}
    	
    	return "showNews";
    }
    


}