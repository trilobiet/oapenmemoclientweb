package org.oapen.memoproject.clientweb.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString @AllArgsConstructor @NoArgsConstructor
@Table(name = "settings")
public class Setting implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String key;
	private String value;

}
