package com.virkade.cms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.virkade.cms.hibernate.dao.ConstantsDAO;

public class DocUtil {

	private static final Logger LOG = Logger.getLogger(DocUtil.class);

	public static String getTextFileContent(String fullFileName) {
		String s = "";
		try {
			LOG.info("getting doc content resource from templates");
			Path resource = Paths.get("templates", fullFileName);
			
			if (!Files.exists(resource)) {
				LOG.warn("could not find external resource, looking in project");
				InputStream in = DocUtil.class.getClassLoader().getResourceAsStream(resource.toString());
				InputStreamReader read = new InputStreamReader(in);
				s = new BufferedReader(read).lines().collect(Collectors.joining(System.lineSeparator()));
			} else {
				byte[] bytes = Files.readAllBytes(resource);
				s = new String(bytes);
			}
		} catch (IOException e) {
			LOG.error("could not load file content", e);
		}
		
		return s;
	}

	public static float getTextFileVersion(String fileNameNoExt) {
		float results = 1.0f;
		LOG.info("getting doc version based on file name");
		String[] parts = fileNameNoExt.split(ConstantsDAO.DEFAULT_DOC_SEP);
		try {
			String version = parts[1];
			results = VersionUtil.versionFormat(version);
		} catch (Exception e) {
			LOG.error("could not find version, defaulting to 1.0", e);
		}
		return results;
	}
	
	public static String getEncodedOwnerSig() {
		String s ="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";
		return s;
	}
}