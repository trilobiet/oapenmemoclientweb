package org.oapen.memoproject.clientweb.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginController {

    @GetMapping(value = {"/login","/clients/{homedir}"})
    public String login(
    	Model model, String error, 
    	@PathVariable Optional<String> homedir) {
    	
        if (error != null)
            model.addAttribute("error");
        
        // prefill username with name of homedir (user)
        // use urls like http://memo.oapen.org/login/comics-grid
        homedir.ifPresentOrElse(
        	v -> model.addAttribute("homedir", v), 
        	() -> model.addAttribute("homedir", "")
        );
        
        return "loginform"; // src/main/resources/templates/loginform.html (thymeleaf)
    }
    
    @GetMapping(value = "/logout")
    public String logout(Model model) {

        model.addAttribute("msglogout");

        return "loginform"; // src/main/resources/templates/login.html (thymeleaf)
    }
}