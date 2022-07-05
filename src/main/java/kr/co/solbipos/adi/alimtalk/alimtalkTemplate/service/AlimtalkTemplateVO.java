package kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : AlimtalkTemplateVO.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 템플릿관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.06.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AlimtalkTemplateVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 소속코드 */
    private String orgnCd;

    /** 전송유형코드 */
    private String sendTypeCd;

    /** 전송유형상세코드 */
    private String sendTypeDtlCd;

    /** 전송유형상세명 */
    private String sendTypeDtlNm;

    /** 승인구분 */
    private String apprFg;

    /** 템플릿코드 */
    private String templateCd;

    /** 템플릿명 */
    private String templateNm;

    /** 공통템플릿 구분 */
    private String commonFg;

    /** 템플릿 그룹구분 */
    private String templateGrpFg;

    /** NHN매장계정 아이디 */
    private String groupKey;

    /** 템플릿 메세지유형 */
    private String templateMsgType;

    /** 템플릿 본문 내용 */
    private String templateContent;

    /** 템플릿 메세지유형 부가정보 내용 */
    private String templateExtra;

    /** 템플릿 메세지유형 광고성메세지 내용 */
    private String templateAd;

    /** 템플릿 강조 유형 */
    private String templateEmpsizeType;

    /** 템플릿 강조 제목 */
    private String templateTitle;

    /** 템플릿 강조 부제목 */
    private String templateSubtitle;

    /** 보안 템플릿 여부 */
    private String securityFg;

    /** 템플릿 카테고리 코드 */
    private String templateClsCd;

    /** 템플릿 버튼 순서 */
    private String buttonsOpdering;

    /** 템플릿 버튼 타입 */
    private String buttonsType;

    /** 템플릿 버튼 이름 */
    private String buttonsName;

    /** 템플릿 버튼 링크 MOBILE */
    private String buttonsLinkMo;

    /** 템플릿 버튼 링크 PC */
    private String buttonsLinkPc;

    /** 템플릿 버튼 링크 IOS */
    private String buttonsLinkLos;

    /** 템플릿 버튼 링크 ANDROID */
    private String buttonsLinkAndroid;

    /** API에서 쓰는 고정키값 */
    private String apiUrl;

    /** API에서 쓰는 고정키값 */
    private String appKey;

    /** API에서 쓰는 Header키 값 */
    private String secretKey;

    /** 파일 경로 */
    private String filePath;

    /** 파일 명 */
    private String fileNm;

    /** 파일확장자 */
    private String fileExt;

    /** 템플릿 강조 이미지명 */
    private String templateImgNm;

    /** 템플릿 강조 이미지URL */
    private String templateImgUrl;

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getSendTypeCd() { return sendTypeCd; }

    public void setSendTypeCd(String sendTypeCd) { this.sendTypeCd = sendTypeCd; }

    public String getSendTypeDtlCd() { return sendTypeDtlCd; }

    public void setSendTypeDtlCd(String sendTypeDtlCd) { this.sendTypeDtlCd = sendTypeDtlCd; }

    public String getSendTypeDtlNm() { return sendTypeDtlNm; }

    public void setSendTypeDtlNm(String sendTypeDtlNm) { this.sendTypeDtlNm = sendTypeDtlNm; }

    public String getApprFg() { return apprFg; }

    public void setApprFg(String apprFg) { this.apprFg = apprFg; }

    public String getTemplateCd() { return templateCd; }

    public void setTemplateCd(String templateCd) { this.templateCd = templateCd; }

    public String getTemplateNm() { return templateNm; }

    public void setTemplateNm(String templateNm) { this.templateNm = templateNm; }

    public String getCommonFg() { return commonFg; }

    public void setCommonFg(String commonFg) { this.commonFg = commonFg; }

    public String getTemplateGrpFg() { return templateGrpFg; }

    public void setTemplateGrpFg(String templateGrpFg) { this.templateGrpFg = templateGrpFg; }

    public String getGroupKey() { return groupKey; }

    public void setGroupKey(String groupKey) { this.groupKey = groupKey; }

    public String getTemplateMsgType() { return templateMsgType; }

    public void setTemplateMsgType(String templateMsgType) { this.templateMsgType = templateMsgType; }

    public String getTemplateContent() { return templateContent; }

    public void setTemplateContent(String templateContent) { this.templateContent = templateContent; }

    public String getTemplateExtra() { return templateExtra; }

    public void setTemplateExtra(String templateExtra) { this.templateExtra = templateExtra; }

    public String getTemplateAd() { return templateAd; }

    public void setTemplateAd(String templateAd) { this.templateAd = templateAd; }

    public String getTemplateEmpsizeType() { return templateEmpsizeType; }

    public void setTemplateEmpsizeType(String templateEmpsizeType) { this.templateEmpsizeType = templateEmpsizeType; }

    public String getTemplateTitle() { return templateTitle; }

    public void setTemplateTitle(String templateTitle) { this.templateTitle = templateTitle; }

    public String getTemplateSubtitle() { return templateSubtitle; }

    public void setTemplateSubtitle(String templateSubtitle) { this.templateSubtitle = templateSubtitle; }

    public String getSecurityFg() { return securityFg; }

    public void setSecurityFg(String securityFg) { this.securityFg = securityFg; }

    public String getTemplateClsCd() { return templateClsCd; }

    public void setTemplateClsCd(String templateClsCd) { this.templateClsCd = templateClsCd; }

    public String getButtonsOpdering() { return buttonsOpdering; }

    public void setButtonsOpdering(String buttonsOpdering) { this.buttonsOpdering = buttonsOpdering; }

    public String getButtonsType() { return buttonsType; }

    public void setButtonsType(String buttonsType) { this.buttonsType = buttonsType; }

    public String getButtonsName() { return buttonsName; }

    public void setButtonsName(String buttonsName) { this.buttonsName = buttonsName; }

    public String getButtonsLinkMo() { return buttonsLinkMo; }

    public void setButtonsLinkMo(String buttonsLinkMo) { this.buttonsLinkMo = buttonsLinkMo; }

    public String getButtonsLinkPc() { return buttonsLinkPc; }

    public void setButtonsLinkPc(String buttonsLinkPc) { this.buttonsLinkPc = buttonsLinkPc; }

    public String getButtonsLinkLos() { return buttonsLinkLos; }

    public void setButtonsLinkLos(String buttonsLinkLos) { this.buttonsLinkPc = buttonsLinkLos; }

    public String getButtonsLinkAndroid() { return buttonsLinkAndroid; }

    public void setButtonsLinkAndroid(String buttonsLinkAndroid) { this.buttonsLinkAndroid = buttonsLinkAndroid; }

    public String getApiUrl() { return apiUrl; }

    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    public String getAppKey() { return appKey; }

    public void setAppKey(String appKey) { this.appKey = appKey; }

    public String getSecretKey() { return secretKey; }

    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileNm() { return fileNm; }

    public void setFileNm(String fileNm) { this.fileNm = fileNm; }

    public String getFileExt() { return fileExt; }

    public void setFileExt(String fileExt) { this.fileExt = fileExt; }

    public String getTemplateImgNm() { return templateImgNm; }

    public void setTemplateImgNm(String templateImgNm) { this.templateImgNm = templateImgNm; }

    public String getTemplateImgUrl() { return templateImgUrl; }

    public void setTemplateImgUrl(String templateImgUrl) { this.templateImgUrl = templateImgUrl; }
}