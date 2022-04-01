package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service;

/**
 * @Class Name : ApiSenderVO.java
 * @Description : NHN API 호출(발신프로필 등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ApiSenderVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 카카오계정ID */
    private String plusFriendId;

    /** 휴대폰번호 */
    private String phoneNo;

    /** 사업자 카테고리 코드 */
    private String categoryCode;

    public String getPlusFriendId() { return plusFriendId; }

    public void setPlusFriendId(String plusFriendId) { this.plusFriendId = plusFriendId; }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getCategoryCode() { return categoryCode; }

    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
}