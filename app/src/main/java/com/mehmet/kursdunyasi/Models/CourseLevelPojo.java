package com.mehmet.kursdunyasi.Models;

public class CourseLevelPojo{
	private String level;

	public CourseLevelPojo(String level) {
		this.level = level;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return level;
	}

	@Override
 	public String toString(){
		return 
			"CourseLevelPojo{" + 
			"level = '" + level + '\'' + 
			"}";
		}
}
