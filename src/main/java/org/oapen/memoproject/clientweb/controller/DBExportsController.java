package org.oapen.memoproject.clientweb.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.oapen.memoproject.clientweb.ExportsService;
import org.oapen.memoproject.clientweb.ExportsService.FailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.vavr.control.Either;

// Obsolete
//@Controller
public class DBExportsController {
	
	@Autowired
	private ExportsService fileService;
	
    @GetMapping(value = "/file/{homedir}/{fileName}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(
    		@PathVariable String homedir,
    		@PathVariable String fileName,
    		@RequestParam(name = "key", defaultValue = "") String key,
    		@RequestParam(name = "download") Optional<String> download
    	) throws IOException {
    	
    	Either<FailStatus, Pair<InputStream, String>> fileOrError = fileService.getExport(homedir, fileName, key);

    	if (fileOrError.isRight()) {
    		
    		Pair<InputStream, String> p = fileOrError.get();
    		
    		InputStream in = p.getFirst();
    		
			BodyBuilder bb = ResponseEntity.ok()
				.header("Content-Type", p.getSecond() + ";charset=utf-8");
			
			if (download.isPresent()) 
				bb.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			// No need to close, Spring handles this
			InputStreamResource res = new InputStreamResource(in);
			
			return bb.body(res);
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
    
}
