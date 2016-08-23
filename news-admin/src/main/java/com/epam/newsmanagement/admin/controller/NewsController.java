package com.epam.newsmanagement.admin.controller;

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
@Controller
public class NewsController {



    @RequestMapping(value = "/news", method = {GET, POST})
    public String getAllNews(){
        return "news";
    }



}
