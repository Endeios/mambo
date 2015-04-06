package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import mambo.utils.DataLoader;
import mambo.utils.Utils;

import org.junit.Ignore;
import org.junit.Test;

public class DataLoaderTest {
	
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	final public String testFolderPrefix = "/home/bruno/project/mambobin/testData/a561c25b2bca4ec6ef96/"; 
	
	@Test
	@Ignore
	public void test() throws ZipException, IOException {
		DataLoader.loadArchive(new File(testFolderPrefix+"zip/Archivio.html"));
		DataLoader.loadArchive(new File(testFolderPrefix+"zip/Soggetti.html"));
		DataLoader.loadArchive(new File(testFolderPrefix+"zip/Struttura.html"));
		DataLoader.loadArchive(new File(testFolderPrefix+"zip/Unità.html"));
		DataLoader.loadArchive(new File(testFolderPrefix+"zip/Sottounità.html"));
	}
	
	@Test @Ignore public void lolhash() throws NoSuchAlgorithmException, IOException{
		String b = Utils.getCheckSum(testFolderPrefix+"exported/data.json");
		String lolString = "95eb6ba7de051a6a02fae7ecdf7d8d9dbbb564729f86deac1f4bc1b379962dac";
		System.out.println(b.toLowerCase());
		System.out.println(lolString);
	}
	
	@Test public void compressFIle() throws NoSuchAlgorithmException, IOException{
		ByteArrayOutputStream out = Utils.createPackage(new File(testFolderPrefix+"exported/data.json"));
		out.writeTo(new FileOutputStream ("/tmp/test.zip") );
		
	}
	

}
