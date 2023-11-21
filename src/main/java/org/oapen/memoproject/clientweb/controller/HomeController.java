package org.oapen.memoproject.clientweb.controller;

import java.util.List;
import java.util.Optional;

import org.oapen.memoproject.clientweb.jpa.Client;
import org.oapen.memoproject.clientweb.jpa.Setting;
import org.oapen.memoproject.clientweb.jpa.SettingRepository;
import org.oapen.memoproject.clientweb.jpa.Task;
import org.oapen.memoproject.clientweb.jpa.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	SettingRepository settingRepository;
	
	@Autowired
	@Value("${application.filesroot}") 
	private String filesRoot; 
	
    @GetMapping({"/","/home"})
    public String hello(Model model, @AuthenticationPrincipal Client user) {
    	
    	final String CONTACT_EMAIL = "clients.contact.email";
    	
    	//Client user = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	model.addAttribute("user", user);

    	List<Task> tasks = taskRepository.findByClient(user, Sort.by(Sort.Direction.ASC,"fileName"));
    	model.addAttribute("tasks", tasks);
    	
    	Optional<Setting> contactEmail = settingRepository.findByKey(CONTACT_EMAIL);
    	model.addAttribute("contactEmail", contactEmail.orElseGet(() -> new Setting("", "["+CONTACT_EMAIL+" unspecified]")));
    	
    	model.addAttribute("clientRoot", filesRoot + user.getUsername() + "/");
    	
    	return "homepage"; // src/main/resources/templates/homepage.html (thymeleaf)
    }
    
    @GetMapping("/explainer")
    public String explainer(Model model, @AuthenticationPrincipal Client user) {
    	
    	model.addAttribute("user", user);

    	return "explainer"; // src/main/resources/templates/explainer.html (thymeleaf)
    }
    
}