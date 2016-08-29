package com.epam.newsmanagement.admin.controller;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * controller for works news
 * @author Andrei_Krukouski
 *
 */

=======
import com.epam.newsmanagement.common.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * controller for works news
 */
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
@Controller
public class NewsController {



<<<<<<< HEAD
    @RequestMapping(value = "/news", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllNews(){
        return "news";
    }
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainPage(Model model) {
		return "redirect:/login";
	}
=======
    @RequestMapping(value = "/news", method = {GET, POST})
    public String getAllNews(){
        return "news";
    }

>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53


}
