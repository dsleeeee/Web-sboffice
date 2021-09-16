package kr.co.solbipos.adi.sms.smsTelNoManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsTelNoManageVO.java
 * @Description : 부가서비스 > SMS관리 > 발신번호관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.09.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SmsTelNoManageVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 소속코드 */
    private String orgnCd;

    /** 전화번호 */
    private String telNo;

    /** 노출우선순위 */
    private String useSeq;

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getUseSeq() { return useSeq; }

    public void setUseSeq(String useSeq) { this.useSeq = useSeq; }
}