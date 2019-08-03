package com.mehmet.kursdunyasi.Models;

public class FolderItem{
	private String folder;

	public void setFolder(String folder){
		this.folder = folder;
	}

	public String getFolder(){
		return folder;
	}

	@Override
 	public String toString(){
		return 
			"FolderItem{" + 
			"folder = '" + folder + '\'' + 
			"}";
		}
}
