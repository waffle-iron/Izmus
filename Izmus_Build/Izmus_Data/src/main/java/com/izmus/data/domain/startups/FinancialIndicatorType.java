package com.izmus.data.domain.startups;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FINANCIAL_INDICATOR_TYPES")
public class FinancialIndicatorType implements Serializable, Comparable<FinancialIndicatorType> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "TYPE_ID")
	private Integer typeId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ORDER_IN_REPORT")
	private Integer orderInReport;
	@Column(name = "REPORT_NAME")
	private String reportName;
	@Column(name = "CHILDREN_TYPES")
	@ElementCollection(targetClass = FinancialIndicatorType.class, fetch = FetchType.EAGER)
	private Set<FinancialIndicatorType> childrenTypes;

	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof FinancialIndicatorType))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(FinancialIndicatorType otherType) {
		return toString().compareTo(otherType.toString());
	}

	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}

	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"id\": " + getTypeId() + ", "
				+ "\"name: \"" + getName() + "\", "
				+ "\"reportName: \"" + getReportName() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getTypeId() {
		return typeId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getName() {
		return name;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setName(String name) {
		this.name = name;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getOrderInReport() {
		return orderInReport;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setOrderInReport(Integer orderInReport) {
		this.orderInReport = orderInReport;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getReportName() {
		return reportName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<FinancialIndicatorType> getChildrenTypes() {
		return childrenTypes;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setChildrenTypes(Set<FinancialIndicatorType> childrenTypes) {
		this.childrenTypes = childrenTypes;
	}
	
}
