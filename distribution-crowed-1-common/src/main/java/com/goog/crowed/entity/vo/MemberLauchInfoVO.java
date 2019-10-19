package com.goog.crowed.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLauchInfoVO {

	// �û���¼ϵͳ��ϵͳ�����tokenֵ������ʶ���û���ݡ�
	// �û���id���Ը���tokenֵ��ѯRedis�õ�
	private String memberSignToken;

	// ��Redis����ʱ�洢��Ŀ���ݵ�tokenֵ
	private String projectTempToken;

	// �򵥽���
	private String descriptionSimple;

	// ��ϸ����
	private String descriptionDetail;

	// ��ϵ�绰
	private String phoneNum;

	// �ͷ��绰
	private String serviceNum;

}
