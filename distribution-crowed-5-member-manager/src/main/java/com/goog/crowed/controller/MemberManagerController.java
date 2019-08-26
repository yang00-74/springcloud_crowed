package com.goog.crowed.controller;

import com.goog.crowed.api.DataBaseOperationRemoteService;
import com.goog.crowed.api.RedisOperationRemoteService;
import com.goog.crowed.entity.ResultEntity;
import com.goog.crowed.entity.po.MemberPO;
import com.goog.crowed.entity.vo.MemberSignSuccessVO;
import com.goog.crowed.entity.vo.MemberVO;
import com.goog.crowed.utils.Constant;
import com.goog.crowed.utils.CrowdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class MemberManagerController {

	// read application.yml property value
	@Value("${code.len}")
	private int codeLength;

	@Value("${redis.key.timeout}")
	private Integer timeoutMinute;

	@Autowired
	private RedisOperationRemoteService redisRemoteService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private DataBaseOperationRemoteService databaseRemoteService;
	
	@RequestMapping("/member/manager/loginout")
	public ResultEntity<String> loginout(@RequestParam("token") String token) {
		return redisRemoteService.removeByKey(token);
	}

	@RequestMapping("/member/manager/login")
	public ResultEntity<MemberSignSuccessVO> login(@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd) {
		// 1. 检查账号是否存在
		ResultEntity<MemberPO> resultEntity = databaseRemoteService.retrieveMemberByLoginAcctCount(loginAcct);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		MemberPO memberPO = resultEntity.getData();
		if (memberPO == null) {
			return ResultEntity.failed(Constant.MESSAGE_LOGIN_FAILED);
		}
		// 2. 校验密码
		String userpswdDatabase = memberPO.getUserpswd();
		boolean matchResult = passwordEncoder.matches(userPswd, userpswdDatabase);
		if (!matchResult) {
			return ResultEntity.failed(Constant.MESSAGE_LOGIN_FAILED);
		}
		// 3. 生成token
		String token = CrowdUtils.createToken();
		String memberId = memberPO.getId().toString();
		ResultEntity<String> resultEntity2 = redisRemoteService.saveNormalStringKeyValue(token, memberId, 30);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return ResultEntity.failed(resultEntity2.getMessage());
		}
		// 4. 封装MemberSignSuccessVO对象
		MemberSignSuccessVO memberSignSuccessVO = new MemberSignSuccessVO();
		BeanUtils.copyProperties(memberPO, memberSignSuccessVO);
		memberSignSuccessVO.setToken(token);

		return ResultEntity.successWithData(memberSignSuccessVO);

	}

	@RequestMapping("/member/manager/register")
	public ResultEntity<String> register(@RequestBody MemberVO memberVO) {
		// 1. 检查验证码
		String code = memberVO.getRandomCode();
		if (CrowdUtils.isEmpty(code)) {
			return ResultEntity.failed(Constant.MESSAGE_RANDOM_CODE_INVALID);
		}

		// 2. 检查电话号码
		String phoneNum = memberVO.getPhoneNum();
		if (CrowdUtils.isEmpty(phoneNum)) {
			return ResultEntity.failed(Constant.MESSAGE_PHONE_NUM_INVALID);
		}
		// 3. 校验验证码
		String normalKey = Constant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(normalKey);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		String redisRandomCode = resultEntity.getData();
		if (CrowdUtils.isEmpty(redisRandomCode)) {
			return ResultEntity.failed(Constant.MESSAGE_RANDOM_CODE_NOT_EXISTS);
		}
		if (!Objects.equals(code, redisRandomCode)) {
			return ResultEntity.failed(Constant.MESSAGE_RANDOM_CODE_NOT_MATCHED);
		}
		// 4. 校验后删除验证码
		ResultEntity<String> resultEntity2 = redisRemoteService.removeByKey(normalKey);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return resultEntity2;
		}

		// 5. 检查账号是否被占用
		String loginAcct = memberVO.getLoginacct();
		if (CrowdUtils.isEmpty(loginAcct)) {
			return ResultEntity.failed(Constant.MESSAGE_LOGIN_ACCT_INVALID);
		}
		ResultEntity<Integer> resultEntity3 = databaseRemoteService.retrieveLoginAcctCount(loginAcct);
		if (ResultEntity.FAILED.equals(resultEntity3.getResult())) {
			return ResultEntity.failed(resultEntity3.getMessage());
		}
		Integer count = resultEntity3.getData();
		if (count > 0) {
			return ResultEntity.failed(Constant.MESSAGE_LOGIN_ACCT_ALREADY_EXISTS);
		}

		// 6. 密码加密
		String password = memberVO.getUserpswd();
		String pswdEncoded = passwordEncoder.encode(password);
		memberVO.setUserpswd(pswdEncoded);

		// 7. 存库
		MemberPO memberPO = new MemberPO();
		BeanUtils.copyProperties(memberVO, memberPO);
		return databaseRemoteService.saveMember(memberPO);
	}

	@RequestMapping("/member/manager/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum) {
		if (CrowdUtils.isEmpty(phoneNum)) {
			return ResultEntity.failed(Constant.MESSAGE_PHONE_NUM_INVALID);
		}

		String randomCode = CrowdUtils.randomCode(codeLength);
		String normalKey = Constant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		ResultEntity<String> resultEntity = redisRemoteService.saveNormalStringKeyValue(normalKey, randomCode,
				timeoutMinute);

		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		System.out.println("The random code is " + randomCode);
		return ResultEntity.successNoData();
	}
}
