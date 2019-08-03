package com.mehmet.kursdunyasi.Models;

public class VideoUploadPojo{
	private Object message;

	public void setMessage(Object message){
		this.message = message;
	}

	public Object getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"VideoUploadPojo{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}
