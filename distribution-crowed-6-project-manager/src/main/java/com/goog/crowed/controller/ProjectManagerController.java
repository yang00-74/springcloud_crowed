package com.goog.crowed.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.goog.crowed.api.DataBaseOperationRemoteService;
import com.goog.crowed.api.RedisOperationRemoteService;
import com.goog.crowed.entity.ResultEntity;
import com.goog.crowed.entity.vo.MemberConfirmInfoVO;
import com.goog.crowed.entity.vo.ProjectVO;
import com.goog.crowed.entity.vo.ReturnVO;
import com.goog.crowed.utils.Constant;
import com.goog.crowed.utils.CrowdUtils;

@RestController
public class ProjectManagerController {

	/*
	 * // https://crowd190105.oss-cn-shenzhen.aliyuncs.com/good/happy/111.jpg String
	 * endPoint = "http://oss-cn-shenzhen.aliyuncs.com"; String acessId =
	 * "LTAIRyb77bOaduym"; String accessKey = "oI7ziHH0vEcDT3SzPnROgzWLKXdmW6";
	 * 
	 * OSSClient ossClient = new OSSClient(endPoint, acessId,accessKey);
	 * 
	 * InputStream inputStream = new FileInputStream("photo.jpg");
	 * ossClient.putObject("crowd190105","mayun/photo.jpg",inputStream);
	 * 
	 * ossClient.shutdown();
	 */
	@Autowired
	private RedisOperationRemoteService redisRemoteService;
	@Autowired
	private DataBaseOperationRemoteService dataBaseRemoteService;

	@RequestMapping("/project/manager/save/all/information")
	public ResultEntity<String> saveAllInformation(@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTempToken") String projectTempToken) {

		// 1. 检查登录状态
		if (CrowdUtils.isEmpty(memberSignToken)) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		if (null == memberId) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}

		// 2. 取出redis中的 projectvo string
		ResultEntity<String> resultEntity2 = redisRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return ResultEntity.failed(resultEntity2.getMessage());
		}
		// 3. 还原ProjectVo对象
		String projectVOString = resultEntity2.getData();
		ProjectVO redisProjectVO = JSON.parseObject(projectVOString, ProjectVO.class);

		// 4. 存入数据库
		ResultEntity<String> resultEntity3 = dataBaseRemoteService.saveProjectRemote(redisProjectVO, memberId);
		if (ResultEntity.FAILED.equals(resultEntity3.getResult())) {
			return ResultEntity.failed(resultEntity3.getMessage());
		}
		// 5. 删除redis缓存数据
		return redisRemoteService.removeByKey(projectTempToken);
	}

	@RequestMapping("/project/manager/save/confirm/information")
	public ResultEntity<String> saveConfirmInformation(@RequestBody MemberConfirmInfoVO memberConfirmInfoVO) {

		// 1. 检查登录状态
		String memberSignToken = memberConfirmInfoVO.getMemberSignToken();
		if (CrowdUtils.isEmpty(memberSignToken)) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}

		// 2. 取出redis中的 projectvo string
		String projectTempToken = memberConfirmInfoVO.getProjectTempToken();
		ResultEntity<String> resultEntity2 = redisRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return ResultEntity.failed(resultEntity2.getMessage());
		}
		// 3. 还原ProjectVo对象
		String projectVOString = resultEntity2.getData();
		ProjectVO redisProjectVO = JSON.parseObject(projectVOString, ProjectVO.class);

		redisProjectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

		// 5. 存回redis
		String jsonString = JSON.toJSONString(redisProjectVO);
		return redisRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}

	@RequestMapping("/project/manager/save/return/information")
	public ResultEntity<String> saveReturnInformation(@RequestBody ReturnVO returnVO) {

		// 1. 检查登录状态
		String memberSignToken = returnVO.getMemberSignToken();
		if (CrowdUtils.isEmpty(memberSignToken)) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}

		// 2. 取出redis中的 projectvo string
		String projectTempToken = returnVO.getProjectTempToken();
		ResultEntity<String> resultEntity2 = redisRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return ResultEntity.failed(resultEntity2.getMessage());
		}
		// 3. 还原ProjectVo对象
		String projectVOString = resultEntity2.getData();
		ProjectVO redisProjectVO = JSON.parseObject(projectVOString, ProjectVO.class);

		// 4. 获取回报信息
		List<ReturnVO> returnList = redisProjectVO.getReturnVOList();
		if (CrowdUtils.isCollectionEmpty(returnList)) {
			returnList = new ArrayList<ReturnVO>();
			redisProjectVO.setReturnVOList(returnList);
		}
		returnList.add(returnVO);

		// 5. 存回redis
		String jsonString = JSON.toJSONString(redisProjectVO);
		return redisRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}

	@RequestMapping("/project/manager/save/project/information")
	public ResultEntity<String> saveProjectInformation(@RequestBody ProjectVO projectVO) {

		// 1. 检查登录状态
		String memberSignToken = projectVO.getMemberSignToken();
		if (CrowdUtils.isEmpty(memberSignToken)) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}

		// 2. 取出redis中的 projectvo string
		String projectTempToken = projectVO.getProjectTempToken();
		ResultEntity<String> resultEntity2 = redisRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return ResultEntity.failed(resultEntity2.getMessage());
		}
		// 3. 还原对象
		String projectVOString = resultEntity2.getData();
		ProjectVO redisProjectVO = JSON.parseObject(projectVOString, ProjectVO.class);

		// 4.设置属性
		projectVO.setHeaderPicturePath(redisProjectVO.getHeaderPicturePath());
		projectVO.setDetailPicturePathList(redisProjectVO.getDetailPicturePathList());
		BeanUtils.copyProperties(projectVO, redisProjectVO);

		// 5. 存回redis
		String jsonString = JSON.toJSONString(redisProjectVO);
		return redisRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}

	@RequestMapping("/project/manager/save/detail/picture/path/list")
	public ResultEntity<String> saveDetailPicturePathList(@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTempToken") String projectTempToken,
			@RequestParam("detailPicturePathList") List<String> detailPicturePathList) {

		// 1. 检查登录状态
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		if (null == memberId) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}

		ResultEntity<String> resultEntity2 = redisRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return ResultEntity.failed(resultEntity2.getMessage());
		}

		String projectVOString = resultEntity2.getData();
		ProjectVO projectVO = JSON.parseObject(projectVOString, ProjectVO.class);
		projectVO.setDetailPicturePathList(detailPicturePathList);

		String jsonString = JSON.toJSONString(projectVO);
		return redisRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}

	@RequestMapping("/project/manager/save/head/picture/path")
	public ResultEntity<String> saveHeadPicturePath(@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTempToken") String projectTempToken,
			@RequestParam("headerPicturePath") String headerPicturePath) {

		// 1. 检查登录状态
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		if (null == memberId) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}

		ResultEntity<String> resultEntity2 = redisRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if (ResultEntity.FAILED.equals(resultEntity2.getResult())) {
			return ResultEntity.failed(resultEntity2.getMessage());
		}

		String projectVOString = resultEntity2.getData();
		ProjectVO projectVO = JSON.parseObject(projectVOString, ProjectVO.class);
		projectVO.setHeaderPicturePath(headerPicturePath);

		String jsonString = JSON.toJSONString(projectVO);
		return redisRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}

	@RequestMapping("/project/manager/initCreation")
	public ResultEntity<ProjectVO> initCreation(@RequestParam("memberSignToken") String memberSignToken) {

		// 1. 检查登录状态
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		if (null == memberId) {
			return ResultEntity.failed(Constant.MESSAGE_PLEASE_LOGIN);
		}

		ProjectVO projectVO = new ProjectVO();
		projectVO.setMemberSignToken(memberSignToken);
		String projectTempToken = Constant.REDIS_PROJECT_TEMP_TOKEN_PREFIX
				+ UUID.randomUUID().toString().replaceAll("-", "");
		projectVO.setProjectTempToken(projectTempToken);
		String jsonString = JSON.toJSONString(projectVO);

		redisRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);

		return ResultEntity.successWithData(projectVO);
	}
}
