package com.virkade.cms;

import java.text.DecimalFormat;

public class VersionUtil {
	
	public static float versionFormat(String version) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		Float numVersion = Float.valueOf(version);
		return Float.valueOf(df.format(numVersion));
	}
	public static float versionFormat(float version) {
		return versionFormat(String.valueOf(version));
	}
	
	public static float versionFormat(double version) {
		return versionFormat(String.valueOf(version));
	}
}
