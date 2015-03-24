package mambo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TransformationController {
	@RequestMapping(value = "/transform", method = RequestMethod.POST)
	public void transform(
			/* @RequestParam("name") String name, */@RequestParam("file") MultipartFile file,
			HttpServletResponse servletResponse) throws IOException,
			InvalidFormatException {
		servletResponse.setHeader("ContentType", "application/text");
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Sheet sheet = wb.getSheetAt(0);
		Row titleRow = sheet.getRow(0);
		List<String> title = new ArrayList<String>();
		for (Cell c : titleRow) {
			title.add(c.getStringCellValue());
		}
		int rowStart = 1;
		int rowEnd = sheet.getLastRowNum();
		List<Map<String,Double>> retData = new ArrayList<Map<String,Double>>();
		for(String el:title){
			
			servletResponse.getWriter().write("Element: "+el+"\n");
		}
		
		for(int i = rowStart;i<rowEnd;i++){
			Row currentRow = sheet.getRow(i);
			int headerIndex = 0;
			Map<String,Double> rowModel = new HashMap<String, Double>();
			for(Cell dataCell:currentRow){
				String currentTitle = title.get(headerIndex);
				double value = dataCell.getNumericCellValue();
				rowModel.put(currentTitle, value);
				headerIndex++;
			}
			retData.add(rowModel);
		}
		
		for(Map<String,Double> row:retData){
			servletResponse.getWriter().write(""+row.toString());
		}
		
		 servletResponse.getWriter().write("Name: "+file.getOriginalFilename()+"\n");
		 servletResponse.getWriter().write("Size: "+file.getSize()+"\n");
		 servletResponse.getWriter().write("RowEnd: "+rowEnd+"\n");
	}
}
