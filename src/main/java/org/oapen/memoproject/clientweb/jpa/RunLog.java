package org.oapen.memoproject.clientweb.jpa;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString 
@Table(name = "runlog")
public class RunLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	Integer id;
	String idTask;
	String message;
	Boolean isSuccess;
	LocalDate date; 
}
