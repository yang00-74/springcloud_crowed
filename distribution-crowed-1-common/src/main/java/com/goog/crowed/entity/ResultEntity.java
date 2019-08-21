package com.goog.crowed.entity;

import com.goog.crowed.utils.Constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {

	private String result;
	private String message;
	private T data;

	public static final String SUCCESS = Constant.SUCCESS;
	public static final String FAILED = Constant.FAILED;
	public static final String NO_MESAGE = Constant.NO_MESAGE;
	public static final String NO_DATA = Constant.NO_DATA;

	public static ResultEntity<String> successNoData() {
		return new ResultEntity<String>(SUCCESS, NO_MESAGE, NO_DATA);
	}

	public static <T> ResultEntity<T> successWithData(T data) {
		return new ResultEntity<T>(SUCCESS, NO_MESAGE, data);
	}

	public static <T> ResultEntity<T> failed(String message) {
		return new ResultEntity<T>(FAILED, message, null);
	}

}
