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
@Table(name = "INDICATOR_POINT")
public class IndicatorPoint implements Serializable, Comparable<IndicatorPoint> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "POINT_ID")
	private Integer pointId;
	@Column(name = "PERIOD")
	private String period;
	@Column(name = "VALUE")
	private Double value;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INDICATOR_ID")
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private FinancialIndicator financialIndicator;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof IndicatorPoint))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(IndicatorPoint otherPoint) {
		return toString().compareTo(otherPoint.toString());
	}

	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}

	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"id\": " + getPointId() + ", "
				+ "\"period\": \"" + getPeriod() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public String getPeriod() {
		return period;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getPointId() {
		return pointId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPeriod(String period) {
		this.period = period;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Double getValue() {
		return value;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setValue(Double value) {
		this.value = value;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public FinancialIndicator getFinancialIndicator() {
		return financialIndicator;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setFinancialIndicator(FinancialIndicator financialIndicator) {
		this.financialIndicator = financialIndicator;
	}
	
}
