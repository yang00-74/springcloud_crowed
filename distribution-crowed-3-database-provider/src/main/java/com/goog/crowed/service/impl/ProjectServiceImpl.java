package com.goog.crowed.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goog.crowed.entity.po.MemberConfirmInfoPO;
import com.goog.crowed.mapper.MemberConfirmInfoPOMapper;
import com.goog.crowed.mapper.MemberLaunchInfoPOMapper;
import com.goog.crowed.mapper.ProjectItemPicPOMapper;
import com.goog.crowed.mapper.ProjectPOMapper;
import com.goog.crowed.mapper.ReturnPOMapper;
import com.goog.crowed.mapper.TagPOMapper;
import com.goog.crowed.mapper.TypePOMapper;
import com.goog.crowed.service.api.ProjectService;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private MemberConfirmInfoPOMapper  memberConfirmInfoPOMapper;
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
	@Autowired
	private ProjectItemPicPOMapper projectItemPicPOMapper;
	@Autowired
	private ProjectPOMapper projectPOMapper;
	@Autowired
	private ReturnPOMapper returnPOMapper;
	@Autowired
	private TagPOMapper tagPOMapper;
	@Autowired
	private TypePOMapper typePOMapper;
}
