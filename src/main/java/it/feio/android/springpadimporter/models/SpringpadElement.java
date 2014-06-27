package it.feio.android.springpadimporter.models;

import it.feio.android.springpadimporter.utils.Constants;
import it.feio.android.springpadimporter.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SpringpadElement implements Constants {

	private String name;
	private String text;
	private String description;
	private boolean linked;
	private boolean isPublic;
	private String type;
	private String url; // same as image?
	private float rating;
	private SpringpadAddresses addresses;
	private String created;
	private String modified;
	private List<String> tags = new ArrayList<String>();
	private List<String> notebooks = new ArrayList<String>();
	private List<SpringpadAttachment> attachments = new ArrayList<SpringpadAttachment>();
	private List<SpringpadComment> comments = new ArrayList<SpringpadComment>();
	// Album
	private String artist;
	// TV Shows
	private List<String> cast = new ArrayList<String>();
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
	private String manufacturer;
	// Wines
	private String varietal; // "varietal":"Sangiovese",
	private String wine_type; // "wine type":"Red Wine"
	private String region;
	private String vintage;
	// Book
	private String author;
	private String isbn;
	@SerializedName("publication date")
	private String publicationDate;
	// Recipes
	private String ingredients;
	private String directions;


	public String getName() {
		return name;
	}


	public List<String> getTags() {
		return tags;
	}


	public String getImage() {
		return image.trim();
	}


	public String getText() {
		return text.trim();
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
		return url.trim();
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


	public String getArtist() {
		return artist;
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


	public List<SpringpadAttachment> getAttachments() {
		return attachments;
	}


	public List<SpringpadComment> getComments() {
		return comments;
	}


	public List<SpringpadItem> getItems() {
		return items;
	}


	public String getAuthor() {
		return author;
	}


	public String getIsbn() {
		return isbn;
	}


	public String getPublicationDate() {
		return publicationDate;
	}


	public String getIngredients() {
		return ingredients;
	}


	public String getDirections() {
		return directions;
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


	public List<String> getCast() {
		return cast;
	}


	public SpringpadPhoneNumbers getPhoneNumbers() {
		return phoneNumbers;
	}


	public String getCategory() {
		return category;
	}


	public String getDescription() {
		return description;
	}


	public String getManufacturer() {
		return manufacturer;
	}

}
