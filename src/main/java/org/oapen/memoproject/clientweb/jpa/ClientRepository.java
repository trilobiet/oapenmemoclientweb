package org.oapen.memoproject.clientweb.jpa;

import java.util.Optional;

public interface ClientRepository extends ReadOnlyRepository<Client, String> {
	
	Optional<Client> findByUsername(String userName);

}
