package com.goog.crowed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goog.crowed.entity.MemberPO;
import com.goog.crowed.entity.ResultEntity;
import com.goog.crowed.service.api.MemberService;
import com.goog.crowed.utils.Constant;
import com.goog.crowed.utils.CrowdUtils;

@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/get/login/acct/count")
	public ResultEntity<Integer> retrieveLoginAcctCount(@RequestParam("loginAcct") String loginAcct) {

		if (CrowdUtils.isEmpty(loginAcct)) {
			return ResultEntity.failed(Constant.MESSAGE_LOGIN_ACCT_INVALID);
		}

		try {
			int count = memberService.getLoginAcctCount(loginAcct);
			return ResultEntity.successWithData(count);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}

	}

	@RequestMapping("/save/member")
	public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO) {
		
		try {
			memberService.saveMember(memberPO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		return ResultEntity.successNoData();
	}
}
