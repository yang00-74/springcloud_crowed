package com.goog.crowed.service.api;

import com.goog.crowed.entity.vo.ProjectVO;

public interface ProjectService {

	void saveProject(ProjectVO redisProjectVO, String memberId);

}
