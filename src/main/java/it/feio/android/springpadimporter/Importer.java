package it.feio.android.springpadimporter;

import it.feio.android.springpadimporter.models.SpringpadAttachment;
import it.feio.android.springpadimporter.models.SpringpadElement;
import it.feio.android.springpadimporter.utils.Constants;
import it.feio.android.springpadimporter.utils.Utils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exceptions.ImportException;

public class Importer {

	private final static String JSON = "export.json";

	private List<SpringpadElement> list;
	private String outputTemporaryFolder;
	private String workingPath;
	private Integer notesCount, notebooksCount;


	public static void main(String[] args) {
		Importer importer = new Importer();
		try {
			importer.doImport("/home/fede/Scaricati/Tfdm-export (1).zip");
		} catch (ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<SpringpadElement> list = importer.getSpringpadNotes();
		for (SpringpadElement springpadNote : list) {
			if (springpadNote.getImage() != null || springpadNote.getAttachments().size() > 0) {
				System.out.println("\n");
			}
			springpadNote.toString();
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
		try {
			importer.clean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void doImport(String zipExport) throws ImportException {
		File json;
		try {
			json = getJson(zipExport);
			this.list = parseJson(json);
		} catch (ImportException e) {
			throw e;
		} catch (Exception e) {
			ImportException e1 = new ImportException(e.getMessage(), e);
			throw e1;
		}
	}


	private File getJson(String zipExport) throws Exception {
		if (zipExport == null) throw new NullPointerException("zip is null");
		File json = null;
		outputTemporaryFolder = zipExport.substring(0, zipExport.lastIndexOf(".")) + "_"
				+ Calendar.getInstance().getTimeInMillis();
		try {
			Utils.unzip(zipExport, outputTemporaryFolder);
		} catch (Exception e) {
			throw new ImportException("Error decompressing archive", e);
		}
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
		if (json == null) { throw new ImportException("The file is not a Springpad export archive"); }
		return json;
	}


	private List<SpringpadElement> parseJson(File json) throws IOException {
		List<SpringpadElement> list = new ArrayList<SpringpadElement>();
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(json));
		reader.beginArray();
		while (reader.hasNext()) {
			SpringpadElement springpadElement = gson.fromJson(reader, SpringpadElement.class);
			list.add(springpadElement);
		}
		reader.endArray();
		reader.close();
		return list;
	}


	public List<SpringpadElement> getSpringpadNotes() {
		return this.list;
	}


	public void clean() throws IOException {
		File folder = new File(outputTemporaryFolder);
		FileUtils.deleteDirectory(folder);
	}


	public String getWorkingPath() {
		return workingPath;
	}


	public void setWorkingPath(String workingPath) {
		this.workingPath = workingPath;
	}


	public List<SpringpadElement> getNotes() {
		List<SpringpadElement> notes = new ArrayList<SpringpadElement>();
		for (SpringpadElement springpadElement : this.list) {
			if (!Constants.TYPE_NOTEBOOK.equals(springpadElement.getType())) {
				notes.add(springpadElement);
			}
		}
		return notes;
	}


	public List<SpringpadElement> getNotebooks() {
		List<SpringpadElement> notebooks = new ArrayList<SpringpadElement>();
		for (SpringpadElement springpadElement : this.list) {
			if (Constants.TYPE_NOTEBOOK.equals(springpadElement.getType())) {
				notebooks.add(springpadElement);
			}
		}
		return notebooks;
	}


	public int getNotesCount() {
		if (this.notesCount == null) {
			this.notesCount = getNotes().size();
		}
		return this.notesCount;
	}


	public int getNotebooksCount() {
		if (this.notebooksCount == null) {
			this.notebooksCount = getNotebooks().size();
		}
		return this.notebooksCount;
	}

}
