package mambo.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * Util methods for Mambo
 * 
 * @author bruno
 *
 */
public class Utils {
	/**
	 * Calculates the sha-256 hash of a file and returns the string value
	 * 
	 * @param filePath the file path to hash
	 * @return string representing sha-256 hash of the file
	 * @throws NoSuchAlgorithmException if the algorithm is not supported by the platform
	 * @throws IOException if the file to analyze does not exist 
	 */
	public static String getCheckSum(String filePath) throws NoSuchAlgorithmException, IOException{
		MessageDigest dig = MessageDigest.getInstance("sha-256");
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		byte[] resDig = dig.digest(bytes);
		String b = DatatypeConverter.printHexBinary(resDig);
		return b;
	}
}
