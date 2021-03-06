package com.goog.crowed.utils;

import java.util.List;
import java.util.UUID;

import com.goog.crowed.entity.po.MemberPO;

public class CrowdUtils {

	public static <T> boolean isEmpty(T s) {
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

	public static <T> boolean isCollectionEmpty(List<T> list) {
		if (null == list || list.size()==0) {
			return true;
		}
		return false;
	}
	
	public static String createToken() {
		return Constant.REDIS_MEMBER_SIGNED_TOKEN_PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
	}

}
