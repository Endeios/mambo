package mambo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Util methods for Mambo
 * 
 * @author bruno
 *
 */
public class Utils {

	public static String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
	private static SimpleDateFormat formatter = new SimpleDateFormat(pattern);

	/**
	 * Calculates the sha-256 hash of a file and returns the string value
	 * 
	 * @param filePath
	 *            the file path to hash
	 * @return string representing sha-256 hash of the file
	 * @throws NoSuchAlgorithmException
	 *             if the algorithm is not supported by the platform
	 * @throws IOException
	 *             if the file to analyze does not exist
	 */
	public static String getCheckSum(String filePath)
			throws NoSuchAlgorithmException, IOException {
		MessageDigest dig = MessageDigest.getInstance("sha-256");
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		byte[] resDig = dig.digest(bytes);
		String b = DatatypeConverter.printHexBinary(resDig);
		return b.toLowerCase();
	}

	public static ByteArrayOutputStream createPackage(File fileToPack)
			throws IOException, NoSuchAlgorithmException {

		ObjectMapper m = new ObjectMapper();

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ZipOutputStream retValue = new ZipOutputStream(byteArrayOutputStream);
		ZipEntry data = new ZipEntry("data.json");
		data.setMethod(ZipEntry.DEFLATED);// bah...
		byte[] dataBytes = Files.readAllBytes(Paths.get(fileToPack
				.getAbsolutePath()));
		data.setSize(dataBytes.length);
		retValue.putNextEntry(data);
		retValue.write(dataBytes);
		ZipEntry metadata = new ZipEntry("metadata.json");
		metadata.setMethod(ZipEntry.DEFLATED);// bah x2
		HashMap<String, Object> metadata_data = new HashMap<String, Object>();
		metadata_data.put("attached_entity", "Fond");
		metadata_data.put("date", getArchivistaDateFormat().format(new Date()));
		metadata_data.put("mode", "full");
		metadata_data.put("producer", "i686-pc-mingw32");
		metadata_data.put("version", 121);
		metadata_data
				.put("checksum", getCheckSum(fileToPack.getAbsolutePath()));
		byte[] metadata_bytes = m.writeValueAsBytes(metadata_data);
		metadata.setSize(metadata_bytes.length);
		retValue.putNextEntry(metadata);
		retValue.write(metadata_bytes);
		retValue.finish();
		retValue.close();
		return byteArrayOutputStream;
	}

	public static DateFormat getArchivistaDateFormat() {
		return formatter;
	}
}
