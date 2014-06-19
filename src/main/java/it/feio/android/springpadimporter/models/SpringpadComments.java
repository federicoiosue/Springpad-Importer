package it.feio.android.springpadimporter.models;

import it.feio.android.springpadimporter.utils.Utils;
import java.util.Date;

public class SpringpadComments {

	private String commenter;
	private String date;
	private String comment;


	public String getCommenter() {
		return commenter;
	}


	public Date getDate() {
		return Utils.getDate(date);
	}


	public String getComment() {
		return comment;
	}
}
