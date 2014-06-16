package it.feio.android.springpadimporter.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Test;

public class ZipUtilsTest {

	private final String ZIP_PATH = "/home/alfresco/Scaricati/federico.iosue-export.zip";
	private final String OUT_FOLDER = "/home/alfresco/Scaricati/federico.iosue-export";
	private final String JSON = "export.json";

	@Test
	public void test() {
		assertEquals(ZipUtils.unzip(ZIP_PATH, OUT_FOLDER), new File(OUT_FOLDER));
//		assertEquals(FileUtils.listFiles(new File(OUT_FOLDER), FileFilterUtils.nameFileFilter(JSON),
//				TrueFileFilter.INSTANCE), new File(JSON));
	}

}
