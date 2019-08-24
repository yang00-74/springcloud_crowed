package com.goog.crowed.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.goog.crowed.entity.po.MemberConfirmInfoPO;
import com.goog.crowed.entity.po.MemberLaunchInfoPO;
import com.goog.crowed.entity.po.ProjectItemPicPO;
import com.goog.crowed.entity.po.ProjectPO;
import com.goog.crowed.entity.po.ReturnPO;
import com.goog.crowed.entity.vo.MemberConfirmInfoVO;
import com.goog.crowed.entity.vo.MemberLauchInfoVO;
import com.goog.crowed.entity.vo.ProjectVO;
import com.goog.crowed.entity.vo.ReturnVO;
import com.goog.crowed.mapper.MemberConfirmInfoPOMapper;
import com.goog.crowed.mapper.MemberLaunchInfoPOMapper;
import com.goog.crowed.mapper.ProjectItemPicPOMapper;
import com.goog.crowed.mapper.ProjectPOMapper;
import com.goog.crowed.mapper.ReturnPOMapper;
import com.goog.crowed.mapper.TagPOMapper;
import com.goog.crowed.mapper.TypePOMapper;
import com.goog.crowed.service.api.ProjectService;
import com.goog.crowed.utils.CrowdUtils;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveProject(ProjectVO redisProjectVO, String memberId) {

		// 1. 保存ProjectPO
		ProjectPO projectPO = new ProjectPO();
		BeanUtils.copyProperties(redisProjectVO, projectPO);
		projectPO.setMemberid(Integer.valueOf(memberId));
		projectPOMapper.insert(projectPO);

		// 2. 获取Project 主键id,需修改 mapper.xml文件对应方法属性
		Integer projectId = projectPO.getId();

		// 3. 保存typeList
		List<Integer> typeIdList = redisProjectVO.getTypeIdList();
		if (!CrowdUtils.isCollectionEmpty(typeIdList)) {
			typePOMapper.insertRelationshipBatch(projectId, typeIdList);
		}

		// 4. 保存tagIdList
		List<Integer> tagIdList = redisProjectVO.getTagIdList();
		if (!CrowdUtils.isCollectionEmpty(tagIdList)) {
			tagPOMapper.insertRelationshipBatch(projectId, tagIdList);
		}

		// 5. 保存detailPicturePathList
		List<String> detaiList = redisProjectVO.getDetailPicturePathList();
		if (!CrowdUtils.isCollectionEmpty(detaiList)) {
			List<ProjectItemPicPO> projectItemPicPOList = new ArrayList<ProjectItemPicPO>();
			for (String path : detaiList) {
				ProjectItemPicPO itemPicPO = new ProjectItemPicPO(null, projectId, path);
				projectItemPicPOList.add(itemPicPO);
			}
			projectItemPicPOMapper.insertBatch(projectItemPicPOList);
		}

		// 6. 保存MemberLaunchInfoPO
		MemberLauchInfoVO memberLauchInfoVO = redisProjectVO.getMemberLauchInfoVO();
		if (!CrowdUtils.isEmpty(memberLauchInfoVO)) {
			MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();

			BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
			memberLaunchInfoPO.setMemberid(Integer.valueOf(memberId));

			memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
		}

		// 7. 根据ReturnVO list 保存ReturnPO
		List<ReturnVO> returnVOList = redisProjectVO.getReturnVOList();
		if (!CrowdUtils.isCollectionEmpty(returnVOList)) {
			List<ReturnPO> returnPOList = new ArrayList<ReturnPO>();
			for (ReturnVO returnVO : returnVOList) {

				ReturnPO returnPO = new ReturnPO();

				BeanUtils.copyProperties(returnVO, returnPO);

				returnPO.setProjectid(projectId);

				returnPOList.add(returnPO);
			}

			returnPOMapper.insertBatch(returnPOList);
		}

		// 8. 保存MemberconfirmInfoPO
		MemberConfirmInfoVO confirmInfoVO = redisProjectVO.getMemberConfirmInfoVO();
		if (!CrowdUtils.isEmpty(confirmInfoVO)) {
			MemberConfirmInfoPO confirmInfoPO = new MemberConfirmInfoPO();

			BeanUtils.copyProperties(confirmInfoVO, confirmInfoPO);
			confirmInfoPO.setMemberid(Integer.valueOf(memberId));

			memberConfirmInfoPOMapper.insert(confirmInfoPO);
		}
	}
}
