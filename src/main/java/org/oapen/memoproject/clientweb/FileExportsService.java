package org.oapen.memoproject.clientweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Optional;

import org.apache.tika.Tika;
import org.oapen.memoproject.clientweb.jpa.Client;
import org.oapen.memoproject.clientweb.jpa.ClientRepository;
import org.oapen.memoproject.clientweb.jpa.Task;
import org.oapen.memoproject.clientweb.jpa.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import io.vavr.control.Either;

@Component
public class FileExportsService implements ExportsService {
	
	@Autowired
	@Value("${application.filesroot}") 
	private String filesRoot; 
	
	@Autowired
	private TaskRepository taskRepository;
    
	@Autowired
	private ClientRepository clientRepository; 

	
	@Override
	public Either<FailStatus, Pair<InputStream, String>> getExport(String homedir, String fileName, String accessKey) {
		
    	Optional<Task> task = Optional.empty();
    	Optional<Client> client = clientRepository.findByUsername(homedir);
		
		if (!client.isPresent()) {
			return Either.left(FailStatus.CLIENT_NOT_FOUND);
		}
		else { 
			
			File file = new File(filesRoot + client.get().getUsername() + "/" + fileName);
			
			System.out.println(file);
			
			FileInputStream fis;
			try {
				
				fis = new FileInputStream(file);
	    		task = taskRepository.findByClientAndFileName(client.get(), fileName);
	    		
	    		if (task.isPresent() && authorizeRequest(client.get(), task.get(), accessKey)) 
	    			return Either.right(Pair.of(fis, getMimeType(fileName, fis)));
	    		else {
	    			fis.close();
	    			return Either.left(FailStatus.UNAUTHORIZED);
	    		}	
				
			} catch (IOException e) {
				
				return Either.left(FailStatus.FILE_NOT_FOUND);
			}
			
		}
	}
	
	
	private Boolean authorizeRequest(Client client, Task task, String accessKey) {
		
		if (task.isPublic() || client.getAccessKey().equals(accessKey))
			return true;
		else
			return false;
	}
	
	
    private String getMimeType(String fileName, InputStream is) {
    	
    	/* Tika detects RSS as "application/rss+xml"
    	   which is not recognized by most browsers. */
    	if (fileName.toLowerCase().endsWith(".rss")) 
    		return "text/xml"; 
    	
		Tika tika = new Tika();
		
		try {
			FileInputStream fis = (FileInputStream) is;
			// Use this channel to reset the inputstream
			// after tika inspected it 
			// https://stackoverflow.com/questions/1094703/java-file-input-with-rewind-reset-capability
			FileChannel ch = fis.getChannel(); 
			String mt = tika.detect(is);
			ch.position(0);
			return mt;
		} catch (IOException e) {
			return "application/octet-stream";
		}

    }
	
	
	
}
