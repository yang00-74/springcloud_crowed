package com.goog.crowed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goog.crowed.entity.ResultEntity;
import com.goog.crowed.entity.po.MemberPO;
import com.goog.crowed.entity.vo.ProjectVO;
import com.goog.crowed.service.api.ProjectService;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping("/save/project/{memberId}")
	ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO redisProjectVO,
			@PathVariable("memberId") String memberId){

		try {
			projectService.saveProject(redisProjectVO, memberId);
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
}
