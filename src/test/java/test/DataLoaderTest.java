package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipException;

import mambo.business.DataLoader;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

public class DataLoaderTest {
	
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	
	@Test
	@Ignore
	public void test() throws ZipException, IOException {
		DataLoader.loadArchive(new File("/home/bveronesi/git/gists/a561c25b2bca4ec6ef96/zip/Archivio.html"));
		DataLoader.loadArchive(new File("/home/bveronesi/git/gists/a561c25b2bca4ec6ef96/zip/Soggetti.html"));
		DataLoader.loadArchive(new File("/home/bveronesi/git/gists/a561c25b2bca4ec6ef96/zip/Struttura.html"));
		DataLoader.loadArchive(new File("/home/bveronesi/git/gists/a561c25b2bca4ec6ef96/zip/Unità.html"));
		DataLoader.loadArchive(new File("/home/bveronesi/git/gists/a561c25b2bca4ec6ef96/zip/Sottounità.html"));
	}
	
	@Test public void lolhash() throws NoSuchAlgorithmException, IOException{
		MessageDigest dig = MessageDigest.getInstance("sha-256");
		FileReader fr = new FileReader("/home/bveronesi/git/gists/a561c25b2bca4ec6ef96/exported/data.json");
		//Path path = Paths.get("path/to/file");
		byte[] bytes = IOUtils.toByteArray(fr, "windows-1252");
		byte[] resDig = dig.digest(bytes);
		
		String lolString = "95eb6ba7de051a6a02fae7ecdf7d8d9dbbb564729f86deac1f4bc1b379962dac";
		System.out.println(lolString);
		System.out.println(bytesToHex(resDig).toLowerCase());
	}
	
	/*
	 * da http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
	 * */
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
