package org.oapen.memoproject.clientweb.controller;

import java.util.List;

import org.oapen.memoproject.clientweb.jpa.Client;
import org.oapen.memoproject.clientweb.jpa.Task;
import org.oapen.memoproject.clientweb.jpa.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	@Value("${application.filesroot}") 
	private String filesRoot; 
	
    @GetMapping("/")
    public String hello(Model model) {
    	
    	Client user = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	model.addAttribute("user", user);

    	List<Task> tasks = taskRepository.findByClient(user);
    	model.addAttribute("tasks", tasks);
    	
    	model.addAttribute("clientRoot", filesRoot + user.getUsername() + "/");
    	
    	return "homepage"; // src/main/resources/templates/homepage.html (thymeleaf)
    }
    
    @GetMapping("/explainer")
    public String explainer(Model model) {
    	
    	return "explainer"; // src/main/resources/templates/explainer.html (thymeleaf)
    }
    

}