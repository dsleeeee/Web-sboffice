package kr.co.solbipos.base.store.guestManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : GuestManageVO.java
 * @Description : 기초관리 > 매장관리 > 객층관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.07.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class GuestManageVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 명칭코드 */
    private String nmcodeCd;

    /** 명칭명 */
    private String nmcodeNm;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getNmcodeCd() { return nmcodeCd; }

    public void setNmcodeCd(String nmcodeCd) { this.nmcodeCd = nmcodeCd; }

    public String getNmcodeNm() { return nmcodeNm; }

    public void setNmcodeNm(String nmcodeNm) { this.nmcodeNm = nmcodeNm; }
}