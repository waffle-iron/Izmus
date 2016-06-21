package com.izmus.data.domain.startups;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="STARTUP_MEASUREMENT_QUESTION")
public class MeasurementQuestion implements Serializable, Comparable<MeasurementQuestion> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "QUESTION_ID")
	private Integer questionId;
	@Column(name = "QUESTION")
	private String question;
	@Column(name = "ANSWER")
	private String answer;
	@Column(name = "SCORE")
	private Integer score;
	@Column(name = "QUESTION_LOCALE")
	private String questionLocale;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEASUREMENT_ID", nullable = false)
	@Fetch(value = FetchMode.SELECT)
	@JsonBackReference
	private Measurement measurement;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof Startup))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(MeasurementQuestion otherQuestion) {
		return toString().compareTo(otherQuestion.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"measurementId\": " + getQuestionId() + ", "
				+ "\"scoreCardDate: \"" + getQuestion() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getQuestionId() {
		return questionId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getQuestion() {
		return question;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setQuestion(String question) {
		this.question = question;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getAnswer() {
		return answer;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getQuestionLocale() {
		return questionLocale;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setQuestionLocale(String questionLocale) {
		this.questionLocale = questionLocale;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Measurement getMeasurement() {
		return measurement;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Integer getScore() {
		return score;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setScore(Integer score) {
		this.score = score;
	}
	
}
