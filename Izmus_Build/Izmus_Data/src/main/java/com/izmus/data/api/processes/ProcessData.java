package com.izmus.data.api.processes;

import java.io.Serializable;

public class ProcessData implements Serializable, Comparable<ProcessData>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String base64Image;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof ProcessData))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(ProcessData otherProcess) {
		return toString().compareTo(otherProcess.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Process Id: " + getId();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public String getId() {
		return id;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setId(String id) {
		this.id = id;
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
	public String getBase64Image() {
		return base64Image;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
}
