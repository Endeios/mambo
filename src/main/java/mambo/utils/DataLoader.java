package mambo.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataLoader {
	public static List<Map<String,Object>> loadArchive(File archive) throws IOException{
		List<Map<String,Object>> retData = new ArrayList<Map<String,Object>>();
		
		Document doc = Jsoup.parse(archive, "windows-1252");
		List<String> headers = new ArrayList<String>();
		String headerPath = "body > table > tbody > tr > th ";
		String rowsPath = "body > table > tbody tr";
		Elements header = doc.select(headerPath);
	    for(Element el: header){
	    	headers.add(el.html());
	    }
	    
	    Elements rows = doc.select(rowsPath);
	    int rowSize = rows.size();
	    for(int i = 1;i<rowSize;i++){
	    	List<Element> elements = rows.get(i).getElementsByTag("td");
	    	HashMap<String, Object> map = new HashMap<String, Object>();
	    	for(int j =0 ; j < elements.size(); j++){
	    		map.put(headers.get(j), elements.get(j).text());
	    	}
	    	
	    	retData.add(map);
	    }
	    
	    return retData;
	    
		
	}
}
