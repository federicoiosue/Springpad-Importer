package it.feio.android.springpadimporter;

import it.feio.android.springpadimporter.models.SpringpadAttachment;
import it.feio.android.springpadimporter.models.SpringpadNote;
import it.feio.android.springpadimporter.utils.ZipUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.google.gson.Gson;

public class Importer {

	private final static String JSON = "export.json";

	public static void main(String[] args) {
		Importer importer = new Importer();
		importer.doImport("/home/alfresco/Scaricati/federico.iosue-export.zip");
		List<SpringpadNote> list = importer.getSpringpadNotes();
		for (SpringpadNote springpadNote : list) {
			if (springpadNote.getImage() != null || springpadNote.getAttachments().size() > 0) {
				System.out.println("\n");
			}
			
			if (springpadNote.getImage() != null) {
				System.out.println("Image: " + importer.getWorkingPath() + springpadNote.getImage());
			}
			for (SpringpadAttachment springpadAttachment : springpadNote.getAttachments()) {
				if (springpadAttachment.getUrl().equals(springpadAttachment.getImage())) {
					System.out.println("Image again!: " + importer.getWorkingPath() + springpadAttachment.getUrl());
				} else {
					System.out.println("Attachment: " + importer.getWorkingPath() + springpadAttachment.getUrl());
				}
			}
		}
		
	}

	private List<SpringpadNote> list;
	private String outputTemporaryFolder;
	private String workingPath;

	public void doImport(String zipExport) {
		File json = getJson(zipExport);
		if (json != null) {
			parseJson(json);
		}
		clean();
	}
	

	private File getJson(String zipExport) {
		File json = null;
		outputTemporaryFolder = zipExport.substring(0, zipExport.lastIndexOf(File.separator) + 1) + zipExport.substring(0, zipExport.lastIndexOf("."));
		ZipUtils.unzip(zipExport, outputTemporaryFolder);
		try {
			@SuppressWarnings("unchecked")
			Iterator<File> i = FileUtils.listFiles(new File(outputTemporaryFolder), FileFilterUtils.nameFileFilter(JSON),
					TrueFileFilter.INSTANCE).iterator();
			while (i.hasNext()) {
				json = i.next();
				if (json != null) {
					setWorkingPath(json.getParent() + File.separator);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	private List<SpringpadNote> parseJson(File json) {
		list = null;
		Gson gson = new Gson();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(json));
			list = Arrays.asList(gson.fromJson(br, SpringpadNote[].class));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<SpringpadNote> getSpringpadNotes() {
		return this.list;
	}

	
	public void clean() {
		File folder = new File(outputTemporaryFolder);
		folder.delete();
	}


	public String getWorkingPath() {
		return workingPath;
	}


	public void setWorkingPath(String workingPath) {
		this.workingPath = workingPath;
	}
}
