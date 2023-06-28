package org.oapen.memoproject.clientweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/home")
    @ResponseBody	
    public String hello() {
        return "Spring Boot MVC!";
    }

}