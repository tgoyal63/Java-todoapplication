package com.brainmentors.todo.dto;

import java.io.Serializable;
import java.util.Date;

import com.brainmentors.todo.utils.Constants;
import com.brainmentors.todo.utils.Utilities;

public class ToDoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String desc;
	private Date creationTime;
	private Date modifiedTime;
	private Date completionTime;
	private String status;

	private ToDoDTO() {
		creationTime = new Date();
		modifiedTime = new Date();
		status = Constants.PENDING;
	}

	public ToDoDTO(String name, String desc, Date completionTime) {
		this();
		this.name = name;
		this.desc = desc;
		this.completionTime = completionTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Date getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		var clt = Utilities.getCltInstance();
		clt.addRow(name, desc, Utilities.convertDateToString(creationTime), Utilities.convertDateToString(modifiedTime),
				Utilities.convertDateToString(completionTime), status);
		return "";
	}

}
