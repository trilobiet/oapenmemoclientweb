package org.oapen.memoproject.clientweb.jpa;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString 
@Table(name = "export")
public class Export implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private String mimetype, content;
	private LocalDate timestamp;
	
	@OneToOne
	@JoinColumn(name="id_task")
	private Task task;
	
}
