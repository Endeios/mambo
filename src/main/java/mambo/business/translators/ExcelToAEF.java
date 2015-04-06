package mambo.business.translators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import mambo.business.Translator;
import mambo.utils.Utils;

public class ExcelToAEF implements Translator {

	private Integer titleRowIndex = 0;
	private Integer firstRowIndex = 1;
	
	private ObjectMapper objectMapper;

	
	@Override
	public ByteArrayOutputStream translate(MultipartFile file)
			throws IOException, InvalidFormatException {
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		int numberOfSheets = wb.getNumberOfSheets();
		
		File tFile = File.createTempFile("mambo", ".json");
		

		Map<String, Object> documentMap = new HashMap<>();

		for (int i = 0; i < numberOfSheets; i++) {
			Sheet currentSheet = wb.getSheetAt(i);
			String sheetName = currentSheet.getSheetName();
			List<Map<String, Object>> data = extractSheetByIndex(currentSheet);
			documentMap.put(sheetName, data);
		}
		
		StringBuilder retValue = new StringBuilder();
		//getting fonds
		List<Map<String, Object>> fonds = (List<Map<String, Object>>) documentMap.get("fond");
		for (Map<String, Object> singleFond : fonds) {
			HashMap<String, Object> fondMap = new HashMap<String, Object>();
			fondMap.put("fond", singleFond);
			retValue.append(objectMapper.writeValueAsString(fondMap));
			retValue.append("\n\n");
		}
		//getting units
		List<Map<String, Object>> units = (List<Map<String, Object>>)  documentMap.get("unit");
		for (Map<String, Object> singleUnit : units) {
			HashMap<String, Object> unitMap = new HashMap<String , Object>();
			unitMap.put("unit", singleUnit);
			retValue.append(objectMapper.writeValueAsString(unitMap));
			retValue.append("\n\n");
		}
		FileOutputStream out = new FileOutputStream(tFile);
		out.write(retValue.toString().getBytes());
		out.flush();
		out.close();
		ByteArrayOutputStream retByteValue = null;
		try {
			retByteValue = Utils.createPackage(tFile);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return retByteValue;
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
					try {
						String currentTitle = title.get(headerIndex);
						Object value = extractCellValue(dataCell);
						rowModel.put(currentTitle, value);
						headerIndex++;
					} catch (IndexOutOfBoundsException exception) {
//						System.out.println(exception);
//						exception.printStackTrace();
					}
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

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}
