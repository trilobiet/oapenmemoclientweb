package org.oapen.memoproject.clientweb;

import java.util.Optional;

import org.oapen.memoproject.clientweb.jpa.Client;
import org.oapen.memoproject.clientweb.jpa.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ClientDetailsService implements UserDetailsService {
	
	@Autowired
	private ClientRepository repo;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		Optional<Client> client = repo.findByUsername(name);
		System.out.println("CLIENT " + client);
		return client.orElseThrow(() ->  new UsernameNotFoundException("User '" + name + "' not found"));
	}

}
