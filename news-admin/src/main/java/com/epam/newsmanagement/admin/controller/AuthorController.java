package com.epam.newsmanagement.admin.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.AuthorService;

@Controller
@SessionAttributes({"author", "authorsList"})
public class AuthorController {
	
	private final static Logger logger = LogManager.getLogger(AuthorController.class);
	
	@Autowired
	AuthorService authorService;
	
	@RequestMapping(value = "addauthor", method = RequestMethod.GET)
	public String getAuthorAddPage(Model model){
		List<Author> authorsList = null;
		try{
			authorsList = authorService.getAll();
		}catch(ServiceException e){
			logger.error("Faile to get list authors");
			model.addAttribute("errorTitle", e);
    		return "error";
		}
		model.addAttribute("authorsList", authorsList);
		model.addAttribute("author", new Author());
		return "addauthor";
	}
	
	@RequestMapping(value = "createauthor", method = RequestMethod.POST)
	public String createAuthor(@Valid Author author, BindingResult result, Model model){
		if(result.hasErrors()){
			logger.info("Returning addauthod.jsp page");
			return "addauthor";
		}
		Date currentDate = new Date();
		author.setExpiredDate(new Timestamp(currentDate.getTime()));
		try{
			authorService.create(author);
		}catch(ServiceException e){
			logger.error("Failed to create author=" + author);
			model.addAttribute("errorTitle", e);
			return "error";
		}
		return getAuthorAddPage(model);
	}
	
	@RequestMapping(value = "updateAuthor", method = RequestMethod.POST)
	public String updateAuthor(@Valid Author author, BindingResult result, Model model){
		if(result.hasErrors()){
			logger.info("Returning addauthod.jsp page");
			return "addauthor";
		}
		try{
			authorService.update(author);
		}catch(ServiceException e){
			logger.error("Failed to update author=" + author);
			model.addAttribute("errorTitle", e);
			return "error";
		}
		return getAuthorAddPage(model);
	}
}
