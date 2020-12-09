package com.virkade.cms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropsUtil {

	private static final Logger LOG = Logger.getLogger(PropsUtil.class);
	private static Properties props = new Properties();

	public final static String MAIL_SMTP_USER = "mail.smtp.user";
	public final static String MAIL_SMTP_PASSWORD = "mail.smtp.password";
	public final static String MAIL_SMTP_FROM = "mail.smtp.from";
	public final static String DEFAULT_CLOSE_TIME_FULL = "default.close.time";
	public final static String DEFAULT_SESSION_LENGTH_IN_MINUTES = "default.session.length.min";
	public final static String DEFAULT_SESSION_MINIMUM_GAP = "default.session.minimum.gap";
	public final static String DEFAULT_SESSION_BUFFER = "default.session.buffer";
	public final static String PLAY_SESSION_SETUP_MIN = "session.setup.minutes";
	public final static String CROSS_ORIGIN_HOSTS = "cross.origin.hosts";

	private static int defaultClosingTimeHour = 0;
	private static int defaultClosingTimeMin = 0;
	private static int playSessionLength = 30;
	private static int playSessionMinGap = 10;
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
			LOG.info("getting state data resource: email.cfg.properties");
			Path resource = Paths.get("config", "email.cfg.properties");
			InputStream propStream = null;
			if (!Files.exists(resource)) {
				LOG.warn("could not find external resource, looking in project");
				propStream = PropsUtil.class.getClassLoader().getResourceAsStream(resource.toString());
			} else {
				propStream = Files.newInputStream(resource, StandardOpenOption.READ);
			}
			props.load(propStream);
		} catch (IOException e) {
			LOG.error("could not load email config properties", e);
		}
		try {
			String[] closingParts = String.valueOf(props.get(DEFAULT_CLOSE_TIME_FULL)).split(":");
			String sessionLength = String.valueOf(props.get(DEFAULT_SESSION_LENGTH_IN_MINUTES));
			String sessionMinGap = String.valueOf(props.get(DEFAULT_SESSION_MINIMUM_GAP));
			String sessionBuffer = String.valueOf(props.get(DEFAULT_SESSION_BUFFER));
			String sessionSetupMin = String.valueOf(props.get(PLAY_SESSION_SETUP_MIN));
			String crossOriginHosts = String.valueOf(props.get(CROSS_ORIGIN_HOSTS));
			PropsUtil.defaultClosingTimeHour = Integer.valueOf(closingParts[0]);
			PropsUtil.defaultClosingTimeMin = Integer.valueOf(closingParts[1]);
			PropsUtil.playSessionLength = Integer.valueOf(sessionLength);
			PropsUtil.playSessionMinGap = Integer.valueOf(sessionMinGap);
			PropsUtil.defaultSessionBuffer = Integer.valueOf(sessionBuffer);
			PropsUtil.playSessionSetupMin = Integer.valueOf(sessionSetupMin);
			PropsUtil.crossOriginHosts = String.valueOf(crossOriginHosts);
		} catch (RuntimeException e) {
			LOG.error("There was a problem seperating out the min time, session time, & closing hour/min, details may be inaccurate", e);
		}

	}

	public static Object get(String key) {
		return props.get(key);
	}

	public static Properties getProps() {
		return props;
	}

	/**
	 * @return the defaultClosingTimeHour
	 */
	public static int getDefaultClosingTimeHour() {
		return defaultClosingTimeHour;
	}

	/**
	 * @return the defaultClosingTimeMin
	 */
	public static int getDefaultClosingTimeMin() {
		return defaultClosingTimeMin;
	}

	/**
	 * @return the playSessionLength
	 */
	public static int getPlaySessionLength() {
		return playSessionLength;
	}

	/**
	 * @return the playSessionMinGap
	 */
	public static int getPlaySessionMinGap() {
		return playSessionMinGap;
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

}
