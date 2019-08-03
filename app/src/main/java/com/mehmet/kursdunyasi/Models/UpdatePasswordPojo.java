package com.mehmet.kursdunyasi.Models;

public class UpdatePasswordPojo {
	private Object errorMessage;
	private Object message;

	public void setErrorMessage(Object errorMessage){
		this.errorMessage = errorMessage;
	}

	public Object getErrorMessage(){
		return errorMessage;
	}

	public void setMessage(Object message){
		this.message = message;
	}

	public Object getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"UpdatePasswordPojo{" +
			"error_message = '" + errorMessage + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}
