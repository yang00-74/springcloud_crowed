package com.goog.crowed.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.goog.crowed.entity.po.MemberPO;
import com.goog.crowed.entity.po.MemberPOExample;
import com.goog.crowed.mapper.MemberPOMapper;
import com.goog.crowed.service.api.MemberService;
import com.goog.crowed.utils.CrowdUtils;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberPOMapper memberPOMapper;

	public int getLoginAcctCount(String loginAcct) {
		
		MemberPOExample example = new MemberPOExample();
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		
		return memberPOMapper.countByExample(example);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveMember(MemberPO memberPO) {
		memberPOMapper.insert(memberPO);
	}

	public MemberPO getMemberByLoginAcct(String loginAcct) {
		MemberPOExample memberPOExample = new MemberPOExample();
		memberPOExample.createCriteria().andLoginacctEqualTo(loginAcct);
		List<MemberPO> list = memberPOMapper.selectByExample(memberPOExample);
		if (!CrowdUtils.isCollectionEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
}
