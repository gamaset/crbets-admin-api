package com.gamaset.crbetadmin.infra.configuration.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class SHA512Hasher {

	private SecureRandom secureRandom = new SecureRandom();
	
	public String hash(String passwordToHash) {
		String generatedPassword = null;
		try {
			byte[] salt = new byte[16];
			secureRandom.nextBytes(salt);
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public boolean checkPassword(String hash, String attempt) {
		String generatedHash = hash(attempt);
		return hash.equals(generatedHash);
	}

}
