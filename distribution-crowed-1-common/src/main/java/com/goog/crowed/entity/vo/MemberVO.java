package com.goog.crowed.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {

	private String loginacct;
	private String phoneNum;
	private String randomCode;
	private String userpswd;
}
