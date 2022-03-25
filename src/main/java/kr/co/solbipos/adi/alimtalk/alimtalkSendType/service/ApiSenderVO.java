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

    /** 소속코드 */
//    private String orgnCd;

    /** 카카오계정ID */
    private String plusFriendId;

    /** 휴대폰번호 */
    private String phoneNo;

    /** 휴대폰번호에 오는 인증번호 */
//    private int token;

    /** NHN매장계정 아이디 */
//    private String senderKey;

    /** API에서 쓰는 고정키값 */
//    private String appKey;

    /** API에서 쓰는 Header키 값 */
//    private String secretKey;

    /** 플러스 친구 상태코드 */
//    private String apprFg;

    /** 사용여부 */
//    private String useYn;

    /** 사업자 카테고리 코드 */
    private String categoryCode;

//    public String getOrgnCd() { return orgnCd; }
//
//    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getPlusFriendId() { return plusFriendId; }

    public void setPlusFriendId(String plusFriendId) { this.plusFriendId = plusFriendId; }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

//    public int getToken() { return token; }
//
//    public void setToken(int token) { this.token = token; }
//
//    public String getSenderKey() { return senderKey; }
//
//    public void setSenderKey(String senderKey) { this.senderKey = senderKey; }
//
//    public String getAppKey() { return appKey; }
//
//    public void setAppKey(String appKey) { this.appKey = appKey; }
//
//    public String getSecretKey() { return secretKey; }
//
//    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
//
//    public String getApprFg() { return apprFg; }
//
//    public void setApprFg(String apprFg) { this.apprFg = apprFg; }
//
//    public String getUseYn() { return useYn; }
//
//    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getCategoryCode() { return categoryCode; }

    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
}