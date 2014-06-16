package it.feio.android.springpadimporter;

import org.junit.Test;

public class ImporterTest {

	private final String ZIP_PATH = "";

	@Test
	public void test() {
		Importer importer = new Importer();
		importer.doImport(ZIP_PATH);
		// assertEquals(expected, actual);;
	}

}
