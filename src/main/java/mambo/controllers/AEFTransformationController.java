package mambo.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import mambo.business.Translator;
import mambo.business.translators.ExcelToAEF;
import mambo.business.translators.ExcelToJson;

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
public class AEFTransformationController {
	
	private ExcelToAEF translator;
	

	@RequestMapping(value = "/transformToAEF", method = RequestMethod.POST, produces = "application/json")
	public void transform(
			@RequestParam("file") MultipartFile file,
			HttpServletResponse response) throws IOException,
			InvalidFormatException {

		ByteArrayOutputStream retData = translator.translate(file);
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ file.getOriginalFilename() + ".aef\"; size="+retData.size());
		ServletOutputStream os = response.getOutputStream();
		retData.writeTo(os);
	}


	public void setTranslator(ExcelToAEF translator) {
		this.translator = translator;
	}




}
