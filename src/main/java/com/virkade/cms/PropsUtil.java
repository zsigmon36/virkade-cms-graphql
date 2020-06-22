package com.virkade.cms;

import java.io.IOException;
import java.io.InputStream;
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

	private static int defaultClosingTimeHour = 0;
	private static int defaultClosingTimeMin = 0;
	private static int playSessionLength = 30;
	private static int playSessionMinGap = 10;
	private static int defaultSessionBuffer = 1;

	static {
		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream("app.cfg.properties");
			props.load(stream);
		} catch (IOException e) {
			LOG.error("could not load app config properties", e);
		}
		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream("email.cfg.properties");
			props.load(stream);
		} catch (IOException e) {
			LOG.error("could not load email config properties", e);
		}
		try {
			String[] closingParts = String.valueOf(props.get(DEFAULT_CLOSE_TIME_FULL)).split(":");
			String sessionLength = String.valueOf(props.get(DEFAULT_SESSION_LENGTH_IN_MINUTES));
			String sessionMinGap = String.valueOf(props.get(DEFAULT_SESSION_MINIMUM_GAP));
			String sessionBuffer = String.valueOf(props.get(DEFAULT_SESSION_BUFFER));
			PropsUtil.defaultClosingTimeHour = Integer.valueOf(closingParts[0]);
			PropsUtil.defaultClosingTimeMin = Integer.valueOf(closingParts[1]);
			PropsUtil.playSessionLength = Integer.valueOf(sessionLength);
			PropsUtil.playSessionMinGap = Integer.valueOf(sessionMinGap);
			PropsUtil.defaultSessionBuffer = Integer.valueOf(sessionBuffer);
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
	

}
