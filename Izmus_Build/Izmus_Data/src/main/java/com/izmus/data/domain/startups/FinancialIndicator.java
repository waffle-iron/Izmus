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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "FINANCIAL_INDICATOR",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"TYPE_ID","STARTUP_ID"})})
public class FinancialIndicator implements Serializable, Comparable<FinancialIndicator> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "INDICATOR_ID")
	private Integer indicatorId;
	@Column(name = "STARTUP_ID")
	private Integer startupId;
	@Column(name = "TYPE_ID")
	private Integer typeId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "financialIndicator", fetch = FetchType.LAZY, orphanRemoval = true)
	@Fetch(value = FetchMode.JOIN)
	private Set<IndicatorPoint> points;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof FinancialIndicator))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(FinancialIndicator otherIndicator) {
		return toString().compareTo(otherIndicator.toString());
	}

	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}

	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"id\": " + getIndicatorId() + ", "
				+ "\"name: " + getStartupId() + ", "
				+ "\"reportName: " + getTypeId() + "}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getIndicatorId() {
		return indicatorId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getStartupId() {
		return startupId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setStartupId(Integer startupId) {
		this.startupId = startupId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getTypeId() {
		return typeId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<IndicatorPoint> getPoints() {
		return points;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPoints(Set<IndicatorPoint> points) {
		this.points = points;
	}
}
