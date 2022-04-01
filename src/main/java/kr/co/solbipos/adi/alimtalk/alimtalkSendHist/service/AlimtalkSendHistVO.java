package kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : AlimtalkSendHistVO.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AlimtalkSendHistVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 소속코드 */
    private String orgnCd;

    /** 조회구분 */
    private String gubunCombo;

    /** 제목 */
    private String subject;

    /** 메세지내용 */
    private String msgContent;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 전송이력시퀀스 */
    private String alkSendSeq;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getGubunCombo() {
        return gubunCombo;
    }

    public void setGubunCombo(String gubunCombo) {
        this.gubunCombo = gubunCombo;
    }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getMsgContent() { return msgContent; }

    public void setMsgContent(String msgContent) { this.msgContent = msgContent; }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() { return storeCds; }

    public void setStoreCds(String storeCds) { this.storeCds = storeCds; }

    public String getAlkSendSeq() {  return alkSendSeq; }

    public void setAlkSendSeq(String alkSendSeq) { this.alkSendSeq = alkSendSeq; }
}