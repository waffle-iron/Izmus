package com.izmus.data.api.navbar;

import java.io.Serializable;
import java.util.List;

public class NavbarItem implements Serializable, Comparable<NavbarItem>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private String label;
	private String icon;
	private String href;
	private String type;
	private List<NavbarItem> subItems;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof NavbarItem))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(NavbarItem otherNavbarItem) {
		return toString().compareTo(otherNavbarItem.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Item Label: " + getLabel();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public String getLabel() {
		return label;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLabel(String label) {
		this.label = label;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getIcon() {
		return icon;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getHref() {
		return href;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setHref(String href) {
		this.href = href;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getType() {
		return type;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setType(String type) {
		this.type = type;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public List<NavbarItem> getSubItems() {
		return subItems;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setSubItems(List<NavbarItem> subItems) {
		this.subItems = subItems;
	}
	
}
