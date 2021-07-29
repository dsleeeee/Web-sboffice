package kr.co.solbipos.adi.sms.msgManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MsgManageVO.java
 * @Description : 부가서비스 > SMS관리 > 메세지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MsgManageVO extends PageVO {

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

    /** 그룹코드 */
    private String msgGrpCd;

    /** 그룹명 */
    private String msgGrpNm;

    /** SEQ_NO */
    private String seqNo;

    /** 제목 */
    private String title;

    /** 내용 */
    private String content;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 매장상태 */
    private String sysStatFg;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getMsgGrpCd() { return msgGrpCd; }

    public void setMsgGrpCd(String msgGrpCd) { this.msgGrpCd = msgGrpCd; }

    public String getMsgGrpNm() { return msgGrpNm; }

    public void setMsgGrpNm(String msgGrpNm) { this.msgGrpNm = msgGrpNm; }

    public String getSeqNo() { return seqNo; }

    public void setSeqNo(String seqNo) { this.seqNo = seqNo; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreNm() { return storeNm; }

    public void setStoreNm(String storeNm) { this.storeNm = storeNm; }

    public String getSysStatFg() { return sysStatFg; }

    public void setSysStatFg(String sysStatFg) { this.sysStatFg = sysStatFg; }
}