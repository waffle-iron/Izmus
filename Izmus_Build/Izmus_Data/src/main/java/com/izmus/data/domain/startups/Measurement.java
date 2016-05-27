package com.izmus.data.domain.startups;

import java.io.Serializable;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="STARTUP_MEASUREMENT")
public class Measurement implements Serializable, Comparable<Measurement> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "MEASUREMENT_ID")
	private Integer measurementId;
	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(524288)")
	private String description;
	@Column(name = "DESCRIPTION_LOCALE")
	private String descriptionLocale;
	@Column(name = "ASSESSMENT", columnDefinition = "VARCHAR(524288)")
	private String assessment;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "TITLE_LOCALE")
	private String titleLocale;
	@Column(name = "SCORE")
	private Integer score;
	@Column(name = "FINAL_SCORE")
	private Integer finalScore;
	@Column(name = "FINAL_SCORE_RATIO")
	private Double finalScoreRatio;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SCORE_CARD_ID", nullable = false)
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private StartupScoreCard scoreCard;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "measurement", fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.JOIN)
	private Set<MeasurementQuestion> measurementQuestions;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof Startup))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(Measurement otherMeasurement) {
		return toString().compareTo(otherMeasurement.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"measurementId\": " + getMeasurementId() + ", "
				+ "\"scoreCardDate: \"" + getTitle() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getMeasurementId() {
		return measurementId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setMeasurementId(Integer measurementId) {
		this.measurementId = measurementId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getDescription() {
		return description;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setDescription(String description) {
		this.description = description;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getAssessment() {
		return assessment;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getTitle() {
		return title;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getScore() {
		return score;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setScore(Integer score) {
		this.score = score;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public StartupScoreCard getScoreCard() {
		return scoreCard;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setScoreCard(StartupScoreCard scoreCard) {
		this.scoreCard = scoreCard;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Set<MeasurementQuestion> getMeasurementQuestions() {
		return measurementQuestions;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setMeasurementQuestions(Set<MeasurementQuestion> measurementQuestions) {
		this.measurementQuestions = measurementQuestions;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getDescriptionLocale() {
		return descriptionLocale;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setDescriptionLocale(String descriptionLocale) {
		this.descriptionLocale = descriptionLocale;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getTitleLocale() {
		return titleLocale;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setTitleLocale(String titleLocale) {
		this.titleLocale = titleLocale;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Integer getFinalScore() {
		return finalScore;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Double getFinalScoreRatio() {
		return finalScoreRatio;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setFinalScoreRatio(Double finalScoreRatio) {
		this.finalScoreRatio = finalScoreRatio;
	}
	
}
