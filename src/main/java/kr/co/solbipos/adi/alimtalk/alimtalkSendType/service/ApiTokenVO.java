package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service;

/**
 * @Class Name : ApiTokenVO.java
 * @Description : NHN API 호출(발신프로필 토큰 인증)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.25  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ApiTokenVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 카카오계정ID */
    private String plusFriendId;

    /** 휴대폰번호에 오는 인증번호 */
    private int token;

    public String getPlusFriendId() { return plusFriendId; }

    public void setPlusFriendId(String plusFriendId) { this.plusFriendId = plusFriendId; }

    public int getToken() { return token; }

    public void setToken(int token) { this.token = token; }
}