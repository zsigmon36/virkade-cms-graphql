package com.virkade.cms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class PropsUtil {

	private static final Logger LOG = Logger.getLogger(PropsUtil.class);
	private static Properties props = new Properties();
	
	public final static String MAIL_SMTP_USER = "mail.smtp.user";
	public final static String MAIL_SMTP_PASSWORD = "mail.smtp.password";
	public final static String MAIL_SMTP_FROM = "mail.smtp.from";
	public static final String SMS_ACCOUNT_SID = "sms.account.sid";
	public static final String SMS_AUTH_TOKEN = "sms.auth.token";
	public static final String ADMIN_MOBILE_NUM = "admin.mobile.num";
	public final static String DEFAULT_CLOSE_TIME_FULL = "default.close.time";
	public final static String DEFAULT_SESSION_OPTIONS = "default.session.options";
	public final static String DEFAULT_SESSION_BUFFER = "default.session.buffer";
	public final static String PLAY_SESSION_SETUP_MIN = "session.setup.minutes";
	public final static String CROSS_ORIGIN_HOSTS = "cross.origin.hosts";
	public final static String SMS_PHONE_FROM = "sms.phone.from";
	public final static String LENGTH_KEY = "length";
	public final static String GAP_KEY = "gap";
	public final static int DEFAULT_PHONE_CC = 1;
	public final static String GOOGLE_SECRET_JSON_FILENAME = "google_client_secret.json";
	
	private static String adminMobileNum = "4792632216";
	private static int defaultClosingTimeHour = 0;
	private static int defaultClosingTimeMin = 0;
	private static JSONArray playSessionOptionsJsonArray = new JSONArray();
	private static int defaultSessionBuffer = 1;
	private static int playSessionSetupMin = 5;
	private static String crossOriginHosts = "http://localhost:80";

	static {
		try {
			LOG.info("getting state data resource: app.cfg.properties");
			Path resource = Paths.get("config", "app.cfg.properties");
			InputStream propStream = null;
			if (!Files.exists(resource)) {
				LOG.warn("could not find external resource, looking in project");
				propStream = PropsUtil.class.getClassLoader().getResourceAsStream(resource.toString());
			} else {
				propStream = Files.newInputStream(resource, StandardOpenOption.READ);
			}
			props.load(propStream);
		} catch (IOException e) {
			LOG.error("could not load app config properties", e);
		}
		try {
			LOG.info("getting state data resource: comm.cfg.properties");
			Path resource = Paths.get("config", "comm.cfg.properties");
			InputStream propStream = null;
			if (!Files.exists(resource)) {
				LOG.warn("could not find external resource, looking in project");
				propStream = PropsUtil.class.getClassLoader().getResourceAsStream(resource.toString());
			} else {
				propStream = Files.newInputStream(resource, StandardOpenOption.READ);
			}
			props.load(propStream);
		} catch (IOException e) {
			LOG.error("could not load comm config properties", e);
		}
		try {
			String[] closingParts = String.valueOf(props.get(DEFAULT_CLOSE_TIME_FULL)).split(":");
			String sessionBuffer = String.valueOf(props.get(DEFAULT_SESSION_BUFFER));
			String sessionSetupMin = String.valueOf(props.get(PLAY_SESSION_SETUP_MIN));
			String crossOriginHosts = String.valueOf(props.get(CROSS_ORIGIN_HOSTS));
			String playSessionOptionsJsonString = String.valueOf(props.get(DEFAULT_SESSION_OPTIONS));
			Object adminNumberProp = props.get(ADMIN_MOBILE_NUM);
			if (adminNumberProp != null && !adminNumberProp.equals("")) {
				adminMobileNum = String.valueOf(adminNumberProp);
			}
			PropsUtil.defaultClosingTimeHour = Integer.valueOf(closingParts[0]);
			PropsUtil.defaultClosingTimeMin = Integer.valueOf(closingParts[1]);
			PropsUtil.defaultSessionBuffer = Integer.valueOf(sessionBuffer);
			PropsUtil.playSessionSetupMin = Integer.valueOf(sessionSetupMin);
			PropsUtil.crossOriginHosts = String.valueOf(crossOriginHosts);
			PropsUtil.playSessionOptionsJsonArray = new JSONArray(playSessionOptionsJsonString);
		} catch (RuntimeException | JSONException e) {
			LOG.error("There was a problem seperating out the min time, session time, & closing hour/min, details may be inaccurate", e);
		}

	}

	public static Object get(String key) {
		return props.get(key);
	}

	public static Properties getProps() {
		return props;
	}
	public static InputStream getDefaultGoogleSecret(){
		return getInputStream(GOOGLE_SECRET_JSON_FILENAME);
	}

	public static InputStream getInputStream(String fileName){
		InputStream stream = null;
		try {
			LOG.info("getting file resource "+fileName);
			Path resource = Paths.get("config", fileName);
			if (!Files.exists(resource)) {
				LOG.warn("could not find external resource, looking in project");
				stream = PropsUtil.class.getClassLoader().getResourceAsStream(resource.toString());
			} else {
				stream = Files.newInputStream(resource, StandardOpenOption.READ);
			}
		} catch (IOException e) {
			LOG.error("could not load comm config properties", e);
		}
		return stream;
	}

	/**
	 * @return the defaultClosingTimeHour
	 */
	public static int getDefaultClosingTimeHour() {
		return defaultClosingTimeHour;
	}

	public static String getAdminMobileNum() {
		return adminMobileNum;
	}

	public static void setAdminMobileNum(String adminMobileNum) {
		PropsUtil.adminMobileNum = adminMobileNum;
	}

	/**
	 * @return the defaultClosingTimeMin
	 */
	public static int getDefaultClosingTimeMin() {
		return defaultClosingTimeMin;
	}

	/**
	 * @return the playSessionLengths
	 */
	public static JSONArray getPlaySessionOptionsJsonArray() {
		return playSessionOptionsJsonArray;
	}

	public static JSONObject getFirstPlaySessionOptionJson() {
		JSONObject jo = new JSONObject(); 
		try {
			if (playSessionOptionsJsonArray.getJSONObject(0) != null) {
				jo = playSessionOptionsJsonArray.getJSONObject(0);

			} else {
				jo.put("length", 30);
				jo.put("gap", 5);	
			}
		} catch (JSONException e) {
			LOG.error("Exception getting the first play session option", e);
		}
		return jo;
	}

	/**
	 * @return the defaultSessionBuffer
	 */
	public static int getDefaultSessionBuffer() {
		return defaultSessionBuffer;
	}

	public static int getPlaySessionSetupMin() {
		return playSessionSetupMin;
	}

	public static String getCrossOriginHosts() {
		return crossOriginHosts;
	}

	public static int getPlaySessionGapFromLength(int length) throws JSONException {
		int gap = 5;
		for ( int i = 0; i < PropsUtil.getPlaySessionOptionsJsonArray().length(); i++ ) {
			JSONObject curObj = PropsUtil.getPlaySessionOptionsJsonArray().getJSONObject(i);
			if (length == curObj.getInt(PropsUtil.LENGTH_KEY)) {
				gap = curObj.getInt(PropsUtil.GAP_KEY);
			}
		}
		return gap;
	}
}
