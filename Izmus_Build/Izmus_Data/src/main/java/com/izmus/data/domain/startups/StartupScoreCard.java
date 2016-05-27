package com.izmus.data.domain.startups;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="STARTUP_SCORE_CARDS")
public class StartupScoreCard implements Serializable, Comparable<StartupScoreCard> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "SCORE_CARD_ID")
	private Integer scoreCardId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SCORE_CARD_DATE", nullable=false)
	private Date scoreCardDate;
	@Column(name = "FINAL_SCORE")
	private Integer finalScore;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STARTUP_ID", nullable = false)
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private Startup startup;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scoreCard", fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.JOIN)
	private Set<Measurement> measurements;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof Startup))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(StartupScoreCard otherScoreCard) {
		return toString().compareTo(otherScoreCard.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"scoreCardId\": " + getScoreCardId() + ", "
				+ "\"scoreCardDate: \"" + getScoreCardDate() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getScoreCardId() {
		return scoreCardId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setScoreCardId(Integer scoreCardId) {
		this.scoreCardId = scoreCardId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Date getScoreCardDate() {
		return scoreCardDate;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setScoreCardDate(Date scoreCardDate) {
		this.scoreCardDate = scoreCardDate;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Startup getStartup() {
		return startup;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setStartup(Startup startup) {
		this.startup = startup;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Set<Measurement> getMeasurements() {
		return measurements;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setMeasurements(Set<Measurement> measurements) {
		this.measurements = measurements;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Integer getFinalScore() {
		return finalScore;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}
	
}
