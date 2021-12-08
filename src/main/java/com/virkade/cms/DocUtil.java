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
	
	public static String getEncodedOwnerSig(String fullFileName) {
		String s = "";
		try {
			LOG.info("getting signature from templates");
			Path resource = Paths.get("templates", fullFileName);
			
			if (!Files.exists(resource)) {
				LOG.warn("could not find external resource, looking in project");
				InputStream in = DocUtil.class.getClassLoader().getResourceAsStream(resource.toString());
				InputStreamReader read = new InputStreamReader(in);
				s = new BufferedReader(read).lines().collect(Collectors.joining("\n"));
			} else {
				byte[] bytes = Files.readAllBytes(resource);
				s = new String(bytes);
			}
			Encoder encoder = Base64.getEncoder();
			s = encoder.encodeToString(s.getBytes());
		} catch (IOException e) {
			LOG.error("could not load file content", e);
		}
		
		return s;
	}
}
