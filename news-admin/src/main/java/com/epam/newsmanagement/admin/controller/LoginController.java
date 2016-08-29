package com.epam.newsmanagement.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * controller for login/logout admin
 * @author Andrei_Krukouski
 *
 */

@Controller
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginView(){
        return "login";
    }
        

}

