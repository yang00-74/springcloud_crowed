package com.goog.crowed.service.api;

import com.goog.crowed.entity.po.MemberPO;

public interface MemberService {

	int getLoginAcctCount(String loginAcct);

	void saveMember(MemberPO memberPO);

	MemberPO getMemberByLoginAcct(String loginAcct);

}
