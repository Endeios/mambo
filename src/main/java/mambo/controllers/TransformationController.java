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
	private Integer titleRowIndex = 0;
	private Integer firstRowIndex = 1;

	@RequestMapping(value = "/transform", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> transform(
			@RequestParam("file") MultipartFile file,
			HttpServletResponse response) throws IOException,
			InvalidFormatException {

		Map<String, Object> retData = danceMambo(file);
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ file.getOriginalFilename() + ".json\"");
		return retData;
	}

	private Map<String, Object> danceMambo(MultipartFile file)
			throws IOException, InvalidFormatException {
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		int numberOfSheets = wb.getNumberOfSheets();

		Map<String, Object> documentMap = new HashMap<>();

		for (int i = 0; i < numberOfSheets; i++) {
			Sheet currentSheet = wb.getSheetAt(i);
			String sheetName = currentSheet.getSheetName();
			List<Map<String, Object>> data = extractSheetByIndex(currentSheet);
			documentMap.put(sheetName, data);
		}
		return documentMap;
	}

	private List<Map<String, Object>> extractSheetByIndex(Sheet sheet) {
		Row titleRow = sheet.getRow(getTitleRowIndex());
		List<String> title = new ArrayList<String>();

		for (Cell c : titleRow) {
			title.add(c.getStringCellValue());
		}
		int rowStart = getFirstRowIndex();
		int rowEnd = sheet.getLastRowNum();
		List<Map<String, Object>> retData = new ArrayList<Map<String, Object>>();

		for (int i = rowStart; i < rowEnd; i++) {
			Row currentRow = sheet.getRow(i);
			int headerIndex = 0;
			Map<String, Object> rowModel = new HashMap<String, Object>();
			if (currentRow != null) {
				for (Cell dataCell : currentRow) {
					String currentTitle = title.get(headerIndex);
					Object value = extractCellValue(dataCell);
					rowModel.put(currentTitle, value);
					headerIndex++;
				}
				retData.add(rowModel);
			}
		}
		return retData;
	}

	private Object extractCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return null;
		}
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
