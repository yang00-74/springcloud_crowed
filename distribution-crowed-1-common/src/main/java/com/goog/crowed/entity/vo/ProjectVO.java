package com.goog.crowed.entity.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO {

	// �û���¼ϵͳ��ϵͳ�����tokenֵ������ʶ���û���ݡ�
	// �û���id���Ը���tokenֵ��ѯRedis�õ�
	private String memberSignToken;

	// ��Redis����ʱ�洢��Ŀ���ݵ�tokenֵ
	private String projectTempToken;

	// ����id����
	private List<Integer> typeIdList;

	// ��ǩid����
	private List<Integer> tagIdList;

	// ��Ŀ����
	private String projectName;

	// ��Ŀ����
	private String projectDescription;

	// �ƻ��Ｏ�Ľ��
	private Integer money;

	// �Ｏ�ʽ������
	private Integer day;

	// ������Ŀ������
	private String createdate;

	// ͷͼ��·��
	private String headerPicturePath;

	// ����ͼƬ��·��
	private List<String> detailPicturePathList;

	// ��������Ϣ
	private MemberLauchInfoVO memberLauchInfoVO;

	// �ر���Ϣ����
	private List<ReturnVO> returnVOList;

	// ������ȷ����Ϣ
	private MemberConfirmInfoVO memberConfirmInfoVO;
}
