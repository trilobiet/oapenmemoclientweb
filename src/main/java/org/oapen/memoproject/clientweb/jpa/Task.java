package org.oapen.memoproject.clientweb.jpa;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.JoinFormula;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString
@Table(name = "task")
public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    private String id; 
    
	private String  fileName, extension, description;
	@Enumerated(EnumType.STRING)
	private TaskFrequency frequency;
	private LocalDate startDate; 
	private boolean isActive, isPublic;
	
	@ManyToOne()
	@JoinColumn(name = "id_homedir")
	private Client client;
	
	
	public LocalDate getNextUpdate() {
		
		LocalDate lastRunDay = latestLog.getDate();
		long unitsBetween = frequency.getChronoUnit().between(startDate, lastRunDay) + 1;
		LocalDate dt = startDate.plus(unitsBetween, frequency.getChronoUnit());
		return dt;
	}
	

	public String getFrequencyAsText() {
		
		return frequency.name();
	}

	
	public String getPath() {
		
		return client.getUsername() + "/" + fileName;
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinFormula("(SELECT rl.id FROM runlog rl WHERE rl.id_task = id ORDER BY rl.date DESC LIMIT 1)")
	private RunLog latestLog;
	
}
