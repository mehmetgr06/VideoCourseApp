package com.mehmet.kursdunyasi.Models;

public class ListItem{
	private String duration;
	private int chapter;
	private String updatedAt;
	private int educationId;
	private String name;
	private Object rating;
	private String createdAt;
	private int id;
	private String title;

	public void setDuration(String duration){
		this.duration = duration;
	}

	public String getDuration(){
		return duration;
	}

	public void setChapter(int chapter){
		this.chapter = chapter;
	}

	public int getChapter(){
		return chapter;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setEducationId(int educationId){
		this.educationId = educationId;
	}

	public int getEducationId(){
		return educationId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setRating(Object rating){
		this.rating = rating;
	}

	public Object getRating(){
		return rating;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"ListItem{" + 
			"duration = '" + duration + '\'' + 
			",chapter = '" + chapter + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",education_id = '" + educationId + '\'' + 
			",name = '" + name + '\'' + 
			",rating = '" + rating + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}
