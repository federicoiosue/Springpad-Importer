package it.feio.android.springpadimporter.utils;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipUtils {


	public static File unzip(String source, String destination) {
		return unzip(source, destination, null);
	}

	public static File unzip(String source, String destination, String password) {
		File result = null;
		try {
			ZipFile zipFile = new ZipFile(source);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			zipFile.extractAll(destination);
			result = new File(destination);
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return result;
	}
}
