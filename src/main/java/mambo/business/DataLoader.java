package mambo.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataLoader {
	public static void loadArchive(File archive) throws ZipException,
			IOException {
		ZipFile zip = new ZipFile(archive);
		Enumeration<? extends ZipEntry> entries = zip.entries();
		ZipInputStream mydata = new ZipInputStream(new FileInputStream(archive));
		String rootDir = "";
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();
			if (zipEntry.isDirectory()) {
				rootDir = zipEntry.getName();
			}

			System.out.println(zipEntry);

		}

		String archiviEntryName = rootDir + "Archivi.htm";
		System.out.println(archiviEntryName);
		ZipEntry archivi = zip.getEntry(archiviEntryName);
		InputStream archiviIS = zip.getInputStream(archivi);
		
		/**/
		byte[] buffer = new byte[1024];
		int size_read = 0;
		while ((size_read = archiviIS.read(buffer)) > 0) {
			System.out.write(buffer, 0, size_read);
		}
		archiviIS = zip.getInputStream(archivi);
		/**/
		
		Document docArchivi = Jsoup.parse(archiviIS, "utf-8", "");
		Elements stru = docArchivi.select("ul li a[href]");
		for (Element element : stru) {
			System.out.println("Stru "+element.attr("href"));
		}
		
		Elements soggettiProduttori = docArchivi.select("h3 ul a[href]");
		
		for (Element element : soggettiProduttori) {
			System.out.println("Soggetti produttori: "+element.attr("href"));
		}
	}
}
