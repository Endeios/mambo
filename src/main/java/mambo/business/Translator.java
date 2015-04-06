package mambo.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

public interface Translator {

	ByteArrayOutputStream translate(MultipartFile file) throws IOException,
			InvalidFormatException;

}
