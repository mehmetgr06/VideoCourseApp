package com.mehmet.kursdunyasi.Models;

public class CategoriesPojo{
	private String categories;

	public CategoriesPojo(String categories) {
		this.categories = categories;
	}

	public void setCategories(String categories){
		this.categories = categories;
	}

	public String getCategories(){
		return categories;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesPojo{" + 
			"categories = '" + categories + '\'' + 
			"}";
		}
}
