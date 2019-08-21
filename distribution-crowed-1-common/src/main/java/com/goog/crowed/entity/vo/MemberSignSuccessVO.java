package com.goog.crowed.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignSuccessVO {

	private String username;
	private String email;
	private String token;
}
