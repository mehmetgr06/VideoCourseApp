package com.mehmet.kursdunyasi.Models;

public class RegisterPojo{
	private String message;
	private String error_message;

	@Override
	public String toString() {
		return "RegisterPojo{" +
				"message='" + message + '\'' +
				", error_message='" + error_message + '\'' +
				'}';
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
