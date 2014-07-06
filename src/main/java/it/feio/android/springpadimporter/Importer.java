package it.feio.android.springpadimporter;

import it.feio.android.springpadimporter.models.SpringpadComment;
import it.feio.android.springpadimporter.models.SpringpadElement;
import it.feio.android.springpadimporter.utils.Constants;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import listeners.ZipProgressesListener;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.progress.ProgressMonitor;
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
	private ZipProgressesListener mZipProgressesListener;

	private ProgressMonitor pm;


	public static void main(String[] args) {
		Importer importer = new Importer();
		importer.setZipProgressesListener(new ZipProgressesListener() {			
			public void onZipProgress(int progressPercentage) {
				System.out.println(progressPercentage);
			}
		});
		try {
//			importer.doImport("/home/fede/Scaricati/federico.iosue-export(last).zip");
//			importer.doImport("/home/fede/Scaricati/Tfdm-export (1).zip");
			importer.doImport("/home/fede/Scaricati/My_Springpad_Export.zip");
			
		} catch (ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (SpringpadElement springpadElement : importer.getNotes()) {

			StringBuilder content = new StringBuilder();

			if (springpadElement.getType() == null) {
				continue;
			}
			
			if (springpadElement.getType().equals(SpringpadElement.TYPE_VIDEO)) {
				try {
					content.append(System.getProperty("line.separator")).append(springpadElement.getVideos().get(0));
				} catch (IndexOutOfBoundsException e ) {
					content.append(System.getProperty("line.separator")).append(springpadElement.getUrl());
				}
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_TVSHOW)) {
				content.append(System.getProperty("line.separator")).append(springpadElement.getCast());
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_BOOK)) {
				content.append(System.getProperty("line.separator")).append("Author: ")
						.append(springpadElement.getAuthor()).append(System.getProperty("line.separator"))
						.append("Publication date: ").append(springpadElement.getPublicationDate());
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_RECIPE)) {
				content.append(System.getProperty("line.separator")).append("Ingredients: ")
						.append(springpadElement.getIngredients()).append(System.getProperty("line.separator"))
						.append("Directions: ").append(springpadElement.getDirections());
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_BOOKMARK)) {
				content.append(System.getProperty("line.separator")).append(springpadElement.getUrl());
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_BUSINESS)
					&& springpadElement.getPhoneNumbers() != null) {
				content.append(System.getProperty("line.separator")).append("Phone number: ")
						.append(springpadElement.getPhoneNumbers().getPhone());
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_PRODUCT)) {
				content.append(System.getProperty("line.separator")).append("Category: ")
						.append(springpadElement.getCategory()).append(System.getProperty("line.separator"))
						.append("Manufacturer: ").append(springpadElement.getManufacturer())
						.append(System.getProperty("line.separator")).append("Price: ")
						.append(springpadElement.getPrice());
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_WINE)) {
				content.append(System.getProperty("line.separator")).append("Wine type: ")
						.append(springpadElement.getWine_type()).append(System.getProperty("line.separator"))
						.append("Varietal: ").append(springpadElement.getVarietal())
						.append(System.getProperty("line.separator")).append("Price: ")
						.append(springpadElement.getPrice());
			}
			if (springpadElement.getType().equals(SpringpadElement.TYPE_ALBUM)) {
				content.append(System.getProperty("line.separator")).append("Artist: ")
						.append(springpadElement.getArtist());
			}
			for (SpringpadComment springpadComment : springpadElement.getComments()) {
				content.append(System.getProperty("line.separator")).append(springpadComment.getCommenter())
						.append(" commented at 0").append(springpadComment.getDate()).append(": ")
						.append(springpadElement.getArtist());
			}

			System.out.println(content);

		}
		try {
			importer.clean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setZipProgressesListener(ZipProgressesListener listener) {
		this.mZipProgressesListener = listener;
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
			ZipFile zipFile = new ZipFile(zipExport);
			if (mZipProgressesListener != null) {
				keepProgressUpdated(zipFile);
			}
			zipFile.extractAll(outputTemporaryFolder);
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


	private void keepProgressUpdated(ZipFile zipFile) {
		pm = zipFile.getProgressMonitor();
		Thread t = new Thread() {
			private int percentPrevious;
			private int percentDone;
			public void run() {
				while (percentDone != 100) {
					percentDone = pm.getPercentDone();
					if (percentDone != percentPrevious) {
						mZipProgressesListener.onZipProgress(percentDone);
						percentPrevious = percentDone;
					}
					try {
						sleep(1000);
					} catch (InterruptedException e) {}
				}
			}
		};
		t.start();
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
	
	
	public int getUnzipPercentage() {
		return pm.getPercentDone();
	}

}
