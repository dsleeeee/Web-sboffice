package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : AlimtalkSendTypeVO.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송유형
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AlimtalkSendTypeVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 소속코드 */
    private String orgnCd;

    /** 전송유형코드 */
    private String sendTypeCd;

    /** 전송유형상세코드 */
    private String sendTypeDtlCd;

    /** 사용여부 */
    private String useYn;

    /** 전송주기 구분자 */
    private String sendPeriodFg;

    /** 전송주기 */
    private String sendPeriod;

    /** 템플릿 그룹구분 */
    private String templateGrpFg;

    /** 템플릿코드 */
    private String templateCd;

    /** 구분 */
    private String gubun;

    /** 사업자 카테고리 코드 */
    private String categoryCode;

    /** 카카오계정ID */
    private String plusFriendId;

    /** 휴대폰번호 */
    private String phoneNo;

    /** 휴대폰번호에 오는 인증번호 */
    private int token;

    /** NHN매장계정 아이디 */
    private String senderKey;

    /** API에서 쓰는 고정키값 */
    private String apiUrl;

    /** API에서 쓰는 고정키값 */
    private String appKey;

    /** API에서 쓰는 Header키 값 */
    private String secretKey;

    /** 플러스 친구 상태코드 */
    private String apprFg;

    /** NHN매장계정 그룹아이디 */
    private String groupSenderKey;

    /** NHN매장계정 그룹아이디명 */
    private String groupSenderKeyNm;

    /** 공통템플릿 구분 */
    private String commonFg;

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getSendTypeCd() { return sendTypeCd; }

    public void setSendTypeCd(String sendTypeCd) { this.sendTypeCd = sendTypeCd; }

    public String getSendTypeDtlCd() { return sendTypeDtlCd; }

    public void setSendTypeDtlCd(String sendTypeDtlCd) { this.sendTypeDtlCd = sendTypeDtlCd; }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getSendPeriodFg() { return sendPeriodFg; }

    public void setSendPeriodFg(String sendPeriodFg) { this.sendPeriodFg = sendPeriodFg; }

    public String getSendPeriod() { return sendPeriod; }

    public void setSendPeriod(String sendPeriod) { this.sendPeriod = sendPeriod; }

    public String getTemplateGrpFg() { return templateGrpFg; }

    public void setTemplateGrpFg(String templateGrpFg) { this.templateGrpFg = templateGrpFg; }

    public String getTemplateCd() { return templateCd; }

    public void setTemplateCd(String templateCd) { this.templateCd = templateCd; }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getCategoryCode() { return categoryCode; }

    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }

    public String getPlusFriendId() { return plusFriendId; }

    public void setPlusFriendId(String plusFriendId) { this.plusFriendId = plusFriendId; }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public int getToken() { return token; }

    public void setToken(int token) { this.token = token; }

    public String getSenderKey() { return senderKey; }

    public void setSenderKey(String senderKey) { this.senderKey = senderKey; }

    public String getApiUrl() { return apiUrl; }

    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    public String getAppKey() { return appKey; }

    public void setAppKey(String appKey) { this.appKey = appKey; }

    public String getSecretKey() { return secretKey; }

    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getApprFg() { return apprFg; }

    public void setApprFg(String apprFg) { this.apprFg = apprFg; }

    public String getGroupSenderKey() { return groupSenderKey; }

    public void setGroupSenderKey(String groupSenderKey) { this.groupSenderKey = groupSenderKey; }

    public String getGroupSenderKeyNm() { return groupSenderKeyNm; }

    public void setGroupSenderKeyNm(String groupSenderKeyNm) { this.groupSenderKeyNm = groupSenderKeyNm; }

    public String getCommonFg() { return commonFg; }

    public void setCommonFg(String commonFg) { this.commonFg = commonFg; }
}