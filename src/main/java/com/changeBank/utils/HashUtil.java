package com.changeBank.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

	public static String getHash(String data) throws Exception {
	
		//MD5(16B), SHA-224, SHA-256(32B)
		String algorithm = "SHA-224";
		return generateHash(data, algorithm);		
		
	}

	private static String generateHash(String data, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		// Hash holds hashed byte of the data argument
		byte[] hash =  digest.digest(data.getBytes());
		return bytesToStringHex(hash);
	}
	
	private final static char[] hexArr = "0123456789ABCDEF".toCharArray();
	
	private static String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length *2];
		for(int i=0;i<bytes.length;i++) {
			int v = bytes[i] & 0xFF;
			// >>> is a bit-shift operator 
			hexChars[i * 2] = hexArr[v >>> 4];
			hexChars[i * 2 + 1] = hexArr[v & 0x0F];
		}
		return new String(hexChars);
	}
}