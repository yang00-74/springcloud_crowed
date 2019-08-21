package com.goog.crowed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPO {

	private Integer id;

	private String loginacct;

	private String userpswd;

	private String username;

	private String email;

	private Byte authstatus;

	private Byte usertype;

	private String realname;

	private String cardnum;

	private Byte accttype;
}