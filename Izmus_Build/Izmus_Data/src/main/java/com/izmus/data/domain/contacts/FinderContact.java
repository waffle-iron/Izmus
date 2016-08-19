package com.izmus.data.domain.contacts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="FINDER_CONTACTS")
public class FinderContact extends IzmusContact{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Column(name = "ENTITY_ID")
	private Integer entityId;
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getEntityId() {
		return entityId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
}
