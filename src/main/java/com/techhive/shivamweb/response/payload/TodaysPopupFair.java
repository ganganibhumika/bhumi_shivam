package com.techhive.shivamweb.response.payload;


public class TodaysPopupFair {

	private String imageTitle;

	private String image;
	
	private String type;
	
	

	public TodaysPopupFair(String imageTitle, String image, String type) {
		super();
		this.imageTitle = imageTitle;
		this.image = image;
		this.type = type;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	
}
