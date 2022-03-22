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
}