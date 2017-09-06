package com.challenge.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Message {
	@NotNull
	int id;
	@NotNull
	int person_id;
	@NotNull
	@Size(min=1, max=60)
	String content;
	
	public Message() {
	}
		
	public Message(int id, int person_id, String content) {
		super();
		this.id = id;
		this.person_id = person_id;
		this.content = content;
	}
	
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "messages [id=" + id + ", person_id=" + person_id + ", content=" + content + "]";
	}

	
	
}
