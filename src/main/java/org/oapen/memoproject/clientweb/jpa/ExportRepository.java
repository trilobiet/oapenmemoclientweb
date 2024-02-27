package org.oapen.memoproject.clientweb.jpa;

import java.util.Optional;

public interface ExportRepository extends ReadOnlyRepository<Export, Integer> {
	
	Optional<Export> findByTask(Task task);

}
