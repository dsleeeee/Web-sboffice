package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service;

/**
 * @Class Name : ApiGroupVO.java
 * @Description : NHN API 호출(그룹에 발신프로필 추가)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ApiGroupVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** API에서 쓰는 고정키값 */
    private String appKey;

    /** NHN매장계정 그룹아이디 */
    private String groupSenderKey;

    /** NHN매장계정 아이디 */
    private String senderKey;

    public String getAppKey() { return appKey; }

    public void setAppKey(String appKey) { this.appKey = appKey; }

    public String getGroupSenderKey() { return groupSenderKey; }

    public void setGroupSenderKey(String groupSenderKey) { this.groupSenderKey = groupSenderKey; }

    public String getSenderKey() { return senderKey; }

    public void setSenderKey(String senderKey) { this.senderKey = senderKey; }
}