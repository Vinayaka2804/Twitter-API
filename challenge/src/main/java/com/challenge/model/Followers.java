package com.challenge.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Followers {

	@NotNull
	@Min(1)
	int id;
	
	@NotNull
	@Min(1)
	int person_id;
	
	@NotNull
	@Min(1)
	int follower_person_id;
	
	 @NotNull
	 @Valid
	  private List<Followers> addmymessage = new ArrayList<Followers>();
	
	public Followers(){
	}
	
	public Followers(int id, int person_id, int follower_person_id) {
		super();
		this.id = id;
		this.person_id = person_id;
		this.follower_person_id = follower_person_id;
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
	public int getFollower_person_id() {
		return follower_person_id;
	}
	public void setFollower_person_id(int follower_person_id) {
		this.follower_person_id = follower_person_id;
	}
	@Override
	public String toString() {
		return "Followers [id=" + id + ", person_id=" + person_id + ", follower_person_id=" + follower_person_id + "]";
	}
	
	
}
