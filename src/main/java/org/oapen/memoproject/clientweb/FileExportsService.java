package org.oapen.memoproject.clientweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

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

	@Autowired
	private MimeTypeService mimeTypeService; 
	
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
	    			return Either.right(Pair.of(fis, mimeTypeService.getMimeTypeFromFileName(fileName)));
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
	
	
}
