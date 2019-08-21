package com.goog.crowed.utils;

public class CrowdUtils {

	public static boolean isEmpty(String s) {
		if (null != s && !("").equals(s)) {
			return false;
		}
		return true;
	}

	public static String randomCode(int length) {
		if (length <= 0)
			return "0000";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(String.valueOf((int) (Math.random() * 10)));
		}
		return sb.toString();

	}

}
