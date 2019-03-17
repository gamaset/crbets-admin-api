package com.gamaset.crbetadmin.infra.utils;

import java.security.SecureRandom;

public class HashUtils {

	public static String generateHashId() {
		String hash = "CR";
		int len = 10;
		char[] ch = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

		char[] c = new char[len];
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < len; i++) {
			c[i] = ch[random.nextInt(ch.length)];
		}

		return hash.concat(new String(c)).concat("BETS");
	}

}
