package it.feio.android.springpadimporter.models;

import it.feio.android.springpadimporter.utils.Constants;
import it.feio.android.springpadimporter.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SpringpadElement implements Constants {

	private String name;
	private List<String> tags;
	private String text;
	private boolean linked;
	private boolean isPublic;
	private String type;
	private String url; // same as image?
	private float rating;
	private List<String> notebooks = new ArrayList<String>();
	private SpringpadAddresses addresses;
	private String created;
	private String modified;
	private List<SpringpadAttachment> attachments = new ArrayList<SpringpadAttachment>();
	private List<SpringpadComment> comments = new ArrayList<SpringpadComment>();
	// Business
	@SerializedName("phone numbers")
	SpringpadPhoneNumbers phoneNumbers;
	// Photos
	private String image;
	// CheckLists
	private List<SpringpadItem> items = new ArrayList<SpringpadItem>();
	// Notebooks
	private String uuid;
	// Tasks
	private boolean complete;
	// Events
	private String date;
	// Products
	private String category;
	private String price;
	private String description;
	private String manufacturer;
	// Wines
	private String varietal; // "varietal":"Sangiovese",
	private String wine_type; // "wine type":"Red Wine"
	private String region;
	private String vintage;


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


	public SpringpadAddresses getAddresses() {
		return addresses;
	}


	public Date getCreated() {
		return Utils.getDate(created);
	}


	public Date getModified() {
		return Utils.getDate(modified);
	}


	public Date getDate() {
		return Utils.getDate(date);
	}


	public String getVarietal() {
		return varietal;
	}


	public String getWine_type() {
		return wine_type;
	}


	public String getDATE_FORMAT() {
		return DATE_FORMAT;
	}


	public List<SpringpadAttachment> getAttachments() {
		return attachments;
	}


	public List<SpringpadComment> getComments() {
		return comments;
	}


	public List<SpringpadItem> getItems() {
		return items;
	}


	public String getPrice() {
		return price;
	}


	public String getRegion() {
		return region;
	}


	public String getVintage() {
		return vintage;
	}

}
