package com.mehmet.kursdunyasi.Models;

public class CoverNameItem{
	private int educationId;
	private String coverName;
	private String title;

	public void setEducationId(int educationId){
		this.educationId = educationId;
	}

	public int getEducationId(){
		return educationId;
	}

	public void setCoverName(String coverName){
		this.coverName = coverName;
	}

	public String getCoverName(){
		return coverName;
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
			"CoverNameItem{" + 
			"education_id = '" + educationId + '\'' + 
			",cover_name = '" + coverName + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}
