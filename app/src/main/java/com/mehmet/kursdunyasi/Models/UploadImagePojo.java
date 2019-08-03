package com.mehmet.kursdunyasi.Models;

public class UploadImagePojo{
	private String picture;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPicture(String picture){
		this.picture = picture;
	}

	public String getPicture(){
		return picture;
	}

	@Override
	public String toString() {
		return "UploadImagePojo{" +
				"picture='" + picture + '\'' +
				", count=" + count +
				'}';
	}



}
