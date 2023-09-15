package org.oapen.memoproject.clientweb.jpa;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends ReadOnlyRepository<Task, String> {
	
	/* Find all tasks for a client */
	List<Task> findByClient(Client c);
	
	/* Find the task with filename for a client */
	Optional<Task> findByClientAndFileName(Client c, String filename);

}
