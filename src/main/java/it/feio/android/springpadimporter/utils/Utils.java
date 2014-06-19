package it.feio.android.springpadimporter.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Utils {

	/**
	 * Returns date converted from ISO format
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date d;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			d = new Date();
			e.printStackTrace();
		}
		return d;
	}


	/**
	 * Unzip a file specifying source and destination
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public static File unzip(String source, String destination) {
		return unzip(source, destination, null);
	}


	/**
	 * Unzip a file specifying source, destination and password
	 * 
	 * @param source
	 * @param destination
	 * @param password
	 * @return
	 */
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


	/**
	 * Uses reflection to get all the object fields
	 * 
	 * @param valueObj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String[]> getFieldNamesAndValues(final Object valueObj) throws IllegalArgumentException,
			IllegalAccessException {

		Class<? extends Object> c = valueObj.getClass();
		List<String[]> fields = new ArrayList<String[]>();
		Field[] valueObjFields = c.getDeclaredFields();

		for (int i = 0; i < valueObjFields.length; i++) {
			String fieldName = valueObjFields[i].getName();
			valueObjFields[i].setAccessible(true);
			String value = String.valueOf(valueObjFields[i].get(valueObj));
			fields.add(new String[] { fieldName, value });
		}
		return fields;
	}
}
