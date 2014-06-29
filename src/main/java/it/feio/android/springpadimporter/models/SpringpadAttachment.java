package it.feio.android.springpadimporter.models;

public class SpringpadAttachment {
	private String image;
	private String type;
	private String url;
	private float duration;	// audio
	private String description;// audio

	public String getImage() {
		return image;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {	
		return url;
	}

	public float getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}
}
