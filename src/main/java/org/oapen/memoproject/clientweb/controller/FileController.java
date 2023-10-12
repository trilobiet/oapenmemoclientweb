package org.oapen.memoproject.clientweb.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.tika.Tika;
import org.oapen.memoproject.clientweb.FileService;
import org.oapen.memoproject.clientweb.FileService.FailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.vavr.control.Either;

@Controller
public class FileController {
	
	@Autowired
	@Value("${application.filesroot}") 
	private String filesRoot; 
	
	@Autowired
	private FileService fileService;
	
    @GetMapping(value = "/file/{homedir}/{fileName}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(
    		@PathVariable String homedir,
    		@PathVariable String fileName,
    		@RequestParam(name = "key", defaultValue = "") String key,
    		@RequestParam(name = "download") Optional<String> download
    	) throws IOException {
    	
    	Either<FailStatus, File> fileOrError = fileService.getFile(homedir, fileName, key);

    	if (fileOrError.isRight()) {
			
    		File file = fileOrError.get();
    		InputStream in = new FileInputStream(file);
    		
    		// System.out.println(getMimeType(file));
    		
			BodyBuilder bb = ResponseEntity.ok()
				.header("Content-Type", getMimeType(file) + ";charset=utf-8");
			
			if (download.isPresent()) 
				bb.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			//else
			//	bb.header("Content-Disposition", "inline; filename=\"" + fileName + "\"");
			
			return bb.body(new InputStreamResource(in));
    	} 
    	else {
    		
    		// NOT FOUND OR AUTHORIZED
    		FailStatus failStatus = fileOrError.getLeft();
    		HttpStatus httpStatus = failStatus.equals(FailStatus.UNAUTHORIZED) ? HttpStatus.UNAUTHORIZED : HttpStatus.NOT_FOUND; 
    		InputStream stream = new ByteArrayInputStream(
    				failStatus.toString().replace("_", " ").getBytes(StandardCharsets.UTF_8));
    		return ResponseEntity.status(httpStatus).body(new InputStreamResource(stream));
    	}
    }
    
    
    private String getMimeType(File file) {
    	
    	/* Tika detects RSS as "application/rss+xml"
    	   which is not recognized by most browsers. */
    	if (file.getName().toLowerCase().endsWith(".rss")) 
    		return "text/xml"; 
    	
		Tika tika = new Tika();
		
		try {
			return tika.detect(file);
		} catch (IOException e) {
			return "application/octet-stream";
		}

    }
    
}
