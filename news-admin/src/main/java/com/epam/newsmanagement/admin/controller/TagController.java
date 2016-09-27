package com.epam.newsmanagement.admin.controller;

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

import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.TagService;

@Controller
@SessionAttributes({"tag", "tagsList"})
public class TagController {
	
	private final static Logger logger = LogManager.getLogger(TagController.class);
	
	@Autowired
	TagService tagService;
	
	/**
	 * redirect on addtag.jsp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addtag", method = RequestMethod.GET)
	public String getAddTagPage(Model model){
		List<Tag> tagsList = null;
		try{
			tagsList = tagService.getAll();
		}catch(ServiceException e){
			logger.error("Faile to get list tags");
			model.addAttribute("errorTitle", e);
    		return "error";
		}
		model.addAttribute("tagsList", tagsList);
		model.addAttribute("tag", new Tag());
		return "addtag";
	}
	
	/**
	 * create tag
	 * @param tag
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addtag/createtag", method = RequestMethod.POST)
	public String createTag(@Valid Tag tag, BindingResult result, Model model){
		if(result.hasErrors()){
			logger.info("Returning addtag.jsp");
			return "addtag";
		}
		try{
			tagService.create(tag);
		}catch(ServiceException e){
			logger.error("Failed to create tag=" + tag);
			model.addAttribute("errorTitle", e);
			return "error";
		}
		return getAddTagPage(model);
	}
	
	/**
	 * update tag
	 * @param tag
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addtag/updatetag", method = RequestMethod.POST)
	public String updateTag(@Valid Tag tag, BindingResult result, Model model){
		if(result.hasErrors()){
			logger.info("Returning addtag.jsp");
			return "addtag";
		}
		try{
			tagService.update(tag);
		}catch(ServiceException e){
			logger.error("Failed to create tag=" + tag);
			model.addAttribute("errorTitle", e);
			return "error";
		}
		return getAddTagPage(model);
	}
}
