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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TransformationController {
	private Integer titleRowIndex=0;
	private Integer firstRowIndex=1;
	
	@RequestMapping(value = "/transform", method = RequestMethod.POST, produces="application/json" )
	public @ResponseBody List<Map<String,Double>> transform(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws IOException,
			InvalidFormatException {
		
		List<Map<String, Double>> retData = danceMambo(file);
		response.addHeader("Content-Disposition", "attachment; filename=\""+file.getOriginalFilename()+".json\"");
		return retData;
	}

	private List<Map<String, Double>> danceMambo(MultipartFile file)
			throws IOException, InvalidFormatException {
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Sheet sheet = wb.getSheetAt(0);
		Row titleRow = sheet.getRow(getTitleRowIndex());
		List<String> title = new ArrayList<String>();

		for (Cell c : titleRow) {
			title.add(c.getStringCellValue());
		}
		int rowStart = getFirstRowIndex();
		int rowEnd = sheet.getLastRowNum();
		List<Map<String,Double>> retData = new ArrayList<Map<String,Double>>();
		
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
		return retData;
	}

	public Integer getTitleRowIndex() {
		return titleRowIndex;
	}

	public void setTitleRowIndex(Integer titleRowIndex) {
		this.titleRowIndex = titleRowIndex;
	}

	public Integer getFirstRowIndex() {
		return firstRowIndex;
	}

	public void setFirstRowIndex(Integer firstRowIndex) {
		this.firstRowIndex = firstRowIndex;
	}
}

