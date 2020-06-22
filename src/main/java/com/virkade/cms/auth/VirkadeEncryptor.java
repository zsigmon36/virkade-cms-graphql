package com.virkade.cms.auth;

import java.util.Random;

import org.apache.log4j.Logger;
import org.postgresql.util.MD5Digest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class VirkadeEncryptor {
	private static final Logger LOG = Logger.getLogger(VirkadeEncryptor.class);
	private static final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(5);
	private static final Random random = new Random(561986);
	private VirkadeEncryptor() {
	}
	
	public static String hashEncode(String value) {
		String encodedPass = bcrypt.encode(value);
		return encodedPass;
	}
	
	public static boolean isMatch(String encodedValue, String clearValue) {
		boolean isMatching = bcrypt.matches(clearValue, encodedValue);
		LOG.debug("The user password is matching="+isMatching);
		return isMatching;
	}

	public static String createRandomToken() {
		 byte[] MD5Token = MD5Digest.encode(
				String.valueOf(random.nextDouble()).getBytes(), 
				String.valueOf(random.nextDouble()).getBytes(),
				String.valueOf(random.nextDouble()).getBytes()
			);
		 String token = new String(MD5Token);
		return token;
	}
	
}
