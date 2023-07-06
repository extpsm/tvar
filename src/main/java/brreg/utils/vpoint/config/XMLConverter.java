package brreg.utils.vpoint.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import brreg.utils.vpoint.config.model.VpointConfig;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XMLConverter {

    private static final Logger log = LoggerFactory.getLogger(XMLConverter.class);
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public void convertFromObjectToXML(Object object, String filepath)
			throws IOException {

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filepath);

			getMarshaller().marshal(object, new StreamResult(os));
			log.info("Ferdig - Skrev til fil :" + filepath +  " \n");
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}


    public Object convertFromXMLToObject(String xmlfile) throws IOException {
        FileInputStream is = new FileInputStream(VpointConfig.substituteWindowsEnvVariables(xmlfile));
        return convertFromXMLToObject(is);
    }

	public Object convertFromXMLToObject(InputStream is) throws IOException {
		try {

			return getUnmarshaller().unmarshal(new StreamSource(is));
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
}