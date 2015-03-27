package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import mambo.business.DataLoader;

import org.junit.Test;

public class DataLoaderTest {
	
	

	@Test
	public void test() throws ZipException, IOException {
		DataLoader.loadArchive(new File("/home/bveronesi/opt/cleanSTS3.5/masterwl/mambo/src/test/resources/Zippone.zip"));
	}

}
