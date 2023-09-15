package org.oapen.memoproject.clientweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.tika.Tika;
import org.oapen.memoproject.clientweb.jpa.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
	
	@Autowired
	@Value("${application.filesroot}") 
	private String filesRoot; 
	
    @GetMapping(value = "/file/{fileName}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(
    		@PathVariable String fileName,
    		@RequestParam(name = "download") Optional<String> download
    	) throws IOException {
    	
		Client user = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		File file = new File(filesRoot + user.getUsername() + "/" + fileName);
		InputStream in = new FileInputStream(file);
		
		Tika tika = new Tika();
		String mimeType = tika.detect(file);
		
		BodyBuilder bb = ResponseEntity.ok().header("Content-Type", mimeType);
		
		if (download.isPresent()) 
			bb.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
		return bb.body(new InputStreamResource(in));	
    }
    
}
