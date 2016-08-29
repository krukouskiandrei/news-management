package com.epam.newsmanagement.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

<<<<<<< HEAD
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

=======
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
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
