package it.feio.android.springpadimporter;

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
	private final static String OUTPUT = "springpad_export";

	public static void main(String[] args) {
		Importer importer = new Importer();
		importer.doImport("/home/alfresco/Scaricati/federico.iosue-export.zip");
	}

	private List<SpringpadNote> list;

	public void doImport(String zipExport) {
		File json = getJson(zipExport);
		parseJson(json);

	}

	private File getJson(String zipExport) {
		File json = null;
		String output = zipExport.substring(0, zipExport.lastIndexOf(File.separator) + 1) + OUTPUT;
		ZipUtils.unzip(zipExport, output);
		try {
			@SuppressWarnings("unchecked")
			Iterator<File> i = FileUtils.listFiles(new File(output), FileFilterUtils.nameFileFilter(JSON),
					TrueFileFilter.INSTANCE).iterator();
			while (i.hasNext()) {
				json = i.next();
				if (json != null) {
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
			System.out.println(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<SpringpadNote> getSpringpadNotes() {
		return this.list;
	}

//	public List<Object> convert(List<Object> destination, String[] methods) {
//		for (String method : methods) {
//			
//		}
//		return destination;
//	}
}
