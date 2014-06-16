package it.feio.android.springpadimporter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SpringpadNote {

	public final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"; // ex. 2014-06-17T08:08:47+0000

	private String name;
	private List<String> tags;
	private String image; // path
	private String text;
	private boolean complete;
	private boolean linked;
	private boolean isPublic;
	private String uuid;
	private String type;
	private String url; // same as image?
	private float rating;
	private List<String> notebooks;
	private Addresses addresses;
	private String created;
	private String modified;
	// Wines
	private String varietal; // "varietal":"Sangiovese",
	private String wine_type; // "wine type":"Red Wine"

	public String getName() {
		return name;
	}

	public List<String> getTags() {
		return tags;
	}

	public String getImage() {
		return image;
	}

	public String getText() {
		return text;
	}

	public boolean isComplete() {
		return complete;
	}

	public boolean isLinked() {
		return linked;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public String getUuid() {
		return uuid;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public float getRating() {
		return rating;
	}

	public List<String> getNotebooks() {
		return notebooks;
	}

	public Addresses getAddresses() {
		return addresses;
	}

	public Date getCreated() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date d;
		try {
			d = sdf.parse(created);
		} catch (ParseException e) {
			d = new Date();
			e.printStackTrace();
		}
		return d;
	}

	public Date getModified() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date d;
		try {
			d = sdf.parse(modified);
		} catch (ParseException e) {
			d = new Date();
			e.printStackTrace();
		}
		return d;
	}

	public String getVarietal() {
		return varietal;
	}

	public String getWine_type() {
		return wine_type;
	}

}

class Addresses {
	private String address;

	public String getAddress() {
		return address;
	}

}
