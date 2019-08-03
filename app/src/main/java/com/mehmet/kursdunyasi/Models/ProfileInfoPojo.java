package com.mehmet.kursdunyasi.Models;

public class ProfileInfoPojo{
	private Object twitter;
	private Object github;
	private Object personalWebsite;
	private Object shortBio;
	private Object facebook;
	private Object location;
	private Object company;
	private Object dribbble;
	private Object linkedin;
	private Object jobTitle;

	public void setTwitter(Object twitter){
		this.twitter = twitter;
	}

	public Object getTwitter(){
		return twitter;
	}

	public void setGithub(Object github){
		this.github = github;
	}

	public Object getGithub(){
		return github;
	}

	public void setPersonalWebsite(Object personalWebsite){
		this.personalWebsite = personalWebsite;
	}

	public Object getPersonalWebsite(){
		return personalWebsite;
	}

	public void setShortBio(Object shortBio){
		this.shortBio = shortBio;
	}

	public Object getShortBio(){
		return shortBio;
	}

	public void setFacebook(Object facebook){
		this.facebook = facebook;
	}

	public Object getFacebook(){
		return facebook;
	}

	public void setLocation(Object location){
		this.location = location;
	}

	public Object getLocation(){
		return location;
	}

	public void setCompany(Object company){
		this.company = company;
	}

	public Object getCompany(){
		return company;
	}

	public void setDribbble(Object dribbble){
		this.dribbble = dribbble;
	}

	public Object getDribbble(){
		return dribbble;
	}

	public void setLinkedin(Object linkedin){
		this.linkedin = linkedin;
	}

	public Object getLinkedin(){
		return linkedin;
	}

	public void setJobTitle(Object jobTitle){
		this.jobTitle = jobTitle;
	}

	public Object getJobTitle(){
		return jobTitle;
	}

	@Override
 	public String toString(){
		return 
			"ProfileInfoPojo{" + 
			"twitter = '" + twitter + '\'' + 
			",github = '" + github + '\'' + 
			",personal_website = '" + personalWebsite + '\'' + 
			",short_bio = '" + shortBio + '\'' + 
			",facebook = '" + facebook + '\'' + 
			",location = '" + location + '\'' + 
			",company = '" + company + '\'' + 
			",dribbble = '" + dribbble + '\'' + 
			",linkedin = '" + linkedin + '\'' + 
			",job_title = '" + jobTitle + '\'' + 
			"}";
		}
}
