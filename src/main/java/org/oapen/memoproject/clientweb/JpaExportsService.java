package org.oapen.memoproject.clientweb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import org.oapen.memoproject.clientweb.jpa.Client;
import org.oapen.memoproject.clientweb.jpa.ClientRepository;
import org.oapen.memoproject.clientweb.jpa.Export;
import org.oapen.memoproject.clientweb.jpa.ExportRepository;
import org.oapen.memoproject.clientweb.jpa.Task;
import org.oapen.memoproject.clientweb.jpa.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import io.vavr.control.Either;

@Component @Primary
public class JpaExportsService implements ExportsService {
	
	@Autowired
	private TaskRepository taskRepository;
    
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ExportRepository exportRepository;
	
	@Override
	public Either<FailStatus, Pair<InputStream, String>> getExport(String homedir, String fileName, String accessKey) {
		
    	Optional<Task> task = Optional.empty();
    	Optional<Client> client = clientRepository.findByUsername(homedir);
		
		if (!client.isPresent()) {
			return Either.left(FailStatus.CLIENT_NOT_FOUND);
		}
		else { 

    		task = taskRepository.findByClientAndFileName(client.get(), fileName);
    		
    		if (task.isPresent() && authorizeRequest(client.get(), task.get(), accessKey)) {
    			
    			Optional<Export> oExport = exportRepository.findByTask(task.get());
    			
    			if (oExport.isPresent()) {
    				Export export = oExport.get();
    				return Either.right(Pair.of(stringToStream(export.getContent()), export.getMimetype()));
    			}
    			else return Either.left(FailStatus.FILE_NOT_FOUND);
    			
    		}
    		else return Either.left(FailStatus.UNAUTHORIZED);	
    			
		}
	}
	
	
	private Boolean authorizeRequest(Client client, Task task, String accessKey) {
		
		if (task.isPublic() || client.getAccessKey().equals(accessKey))
			return true;
		else
			return false;
	}
	
	
	private ByteArrayInputStream stringToStream(String string) {
		
		if (string != null)
			return new ByteArrayInputStream(string.getBytes());
		else return 
			new ByteArrayInputStream("".getBytes());
	}
	
	
}
