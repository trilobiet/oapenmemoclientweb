package org.oapen.memoproject.clientweb;

import java.io.File;
import java.util.Optional;

import org.oapen.memoproject.clientweb.jpa.Client;
import org.oapen.memoproject.clientweb.jpa.ClientRepository;
import org.oapen.memoproject.clientweb.jpa.Task;
import org.oapen.memoproject.clientweb.jpa.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.vavr.control.Either;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
public class FileService {
	
	public enum FailStatus {
		CLIENT_NOT_FOUND, FILE_NOT_FOUND, UNAUTHORIZED
	}
	
	@Autowired
	@Value("${application.filesroot}") 
	private String filesRoot; 
	
	@Autowired
	private TaskRepository taskRepository;
    
	@Autowired
	private ClientRepository clientRepository;

	
	public Either<FailStatus,File> getFile(String homedir, String fileName, String accessKey) {
		
    	Optional<Task> task = Optional.empty();
    	Optional<Client> client = clientRepository.findByUsername(homedir);
    	
		
		if (!client.isPresent()) {
			return Either.left(FailStatus.CLIENT_NOT_FOUND);
		}
		else { 
			
			File file = new File(filesRoot + client.get().getUsername() + "/" + fileName);
			
			if (!file.exists()) {
				return Either.left(FailStatus.FILE_NOT_FOUND);
			}
			else { 	
			
	    		task = taskRepository.findByClientAndFileName(client.get(), fileName);
	    		
	    		if (task.isPresent() && authorizeRequest(client.get(), task.get(), accessKey)) 
	    			return Either.right(file);
	    		else 
	    			return Either.left(FailStatus.UNAUTHORIZED);
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
