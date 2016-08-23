package com.epam.newsmanagement.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * controller for login/logout admin
 */
@Controller
@RequestMapping({"/", "/login"})
public class LoginController {

    @RequestMapping(method = {GET, POST})
    public String loginView(){
        return "login";
    }


}
