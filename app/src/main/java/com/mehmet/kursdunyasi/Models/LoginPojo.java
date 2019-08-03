package com.mehmet.kursdunyasi.Models;

public class LoginPojo {
	private String lastVideo;
	private String userName;
	private String birthDate;
	private Object createdAt;
	private String lastEducation;
	private String token;
	private String password;
	private String lastActivity;
	private Object updatedAt;
	private String surname;
	private int authority;
	private String name;
	private int id;
	private String email;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "LoginPojo{" +
				"lastVideo='" + lastVideo + '\'' +
				", userName='" + userName + '\'' +
				", birthDate='" + birthDate + '\'' +
				", createdAt=" + createdAt +
				", lastEducation='" + lastEducation + '\'' +
				", token='" + token + '\'' +
				", password='" + password + '\'' +
				", lastActivity='" + lastActivity + '\'' +
				", updatedAt=" + updatedAt +
				", surname='" + surname + '\'' +
				", authority=" + authority +
				", name='" + name + '\'' +
				", id=" + id +
				", email='" + email + '\'' +
				", errorMessage='" + errorMessage + '\'' +
				'}';
	}

	public void setLastVideo(String lastVideo){
		this.lastVideo = lastVideo;
	}

	public String getLastVideo(){
		return lastVideo;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setBirthDate(String birthDate){
		this.birthDate = birthDate;
	}

	public String getBirthDate(){
		return birthDate;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setLastEducation(String lastEducation){
		this.lastEducation = lastEducation;
	}

	public String getLastEducation(){
		return lastEducation;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setLastActivity(String lastActivity){
		this.lastActivity = lastActivity;
	}

	public String getLastActivity(){
		return lastActivity;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return surname;
	}

	public void setAuthority(int authority){
		this.authority = authority;
	}

	public int getAuthority(){
		return authority;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

}
