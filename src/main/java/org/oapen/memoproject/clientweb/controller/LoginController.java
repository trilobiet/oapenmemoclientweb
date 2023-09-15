package org.oapen.memoproject.clientweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login(Model model, String error) {
    	
        if (error != null)
            model.addAttribute("error");
        
        return "loginform"; // src/main/resources/templates/loginform.html (thymeleaf)
    }
    
    @GetMapping(value = "/logout")
    public String logout(Model model) {

        model.addAttribute("msglogout");

        return "loginform"; // src/main/resources/templates/login.html (thymeleaf)
    }
}