package com.challenge.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class people {
	
	int id;
	
	@NotNull
	@Size(min=1, max=60)
	String handle, name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "people [id=" + id + ", handle=" + handle + ", name=" + name + "]";
	}
	
	
}
