package org.oapen.memoproject.clientweb.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString 
@Table(name = "homedir")
public class Client implements UserDetails, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String id;	
	private String username, name;
	
    @JsonProperty(access = Access.WRITE_ONLY) // exclude for display! 
	private String password;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return null;
	}
	
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
