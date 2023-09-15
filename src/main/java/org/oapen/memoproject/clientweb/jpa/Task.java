package org.oapen.memoproject.clientweb.jpa;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
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
    
	private String  fileName, extension, frequency;
	private LocalDate startDate;
	private Boolean isActive, isPublic;
	
	@ManyToOne()
	@JoinColumn(name = "id_homedir")
	private Client client;
	
	public LocalDate getNextUpdate() {
		
		LocalDate d = latestLog.getDate();

		switch (frequency) {
		
			case "W": return d.plusWeeks(1);
			case "M": return d.plusMonths(1);
			case "Y": return d.plusYears(1);
			default: return d.plusDays(1);
		}
	}

	public String getFrequencyAsText() {
		
		switch (frequency) {
		
			case "W": return "weekly";
			case "M": return "monthly";
			case "Y": return "yearly";
			default: return "daily";
		}
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinFormula("(SELECT rl.id FROM runlog rl WHERE rl.id_task = id ORDER BY rl.date DESC LIMIT 1)")
	private RunLog latestLog;
	
}
