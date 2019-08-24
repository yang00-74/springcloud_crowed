package com.goog.crowed.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goog.crowed.entity.ResultEntity;
import com.goog.crowed.entity.po.MemberPO;
import com.goog.crowed.entity.vo.ProjectVO;

@FeignClient(value="database-provider")
public interface DataBaseOperationRemoteService {

	@RequestMapping("/get/login/acct/count")
	ResultEntity<Integer> retrieveLoginAcctCount(@RequestParam("loginAcct") String loginAcct);
	
	@RequestMapping("/save/member")
	ResultEntity<String> saveMember(@RequestBody MemberPO memberPO); // 传输 对象数据使用 @RequestBody
	
	@RequestMapping("/get/member/by/login/acct")
	ResultEntity<MemberPO> retrieveMemberByLoginAcctCount(@RequestParam("loginAcct") String loginAcct);

	@RequestMapping("/save/project/{memberId}")
	ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO redisProjectVO,
			@PathVariable("memberId") String memberId);
}
