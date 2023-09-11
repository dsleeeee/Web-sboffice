package kr.co.solbipos.common.popup.selectSdselClass.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SelectSdselClassVO.java
 * @Description : (공통) 선택분류 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SelectSdselClassVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 사이드선택그룹코드 */
    private String sdselGrpCd;

    /** 사이드선택그룹명 */
    private String sdselGrpNm;

    /** 사이드선택분류코드 */
    private String sdselClassCd;

    /** 사이드선택분류명 */
    private String sdselClassNm;

    /** 적용매장구분 */
    private String regStoreFg;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    public String getSdselGrpNm() {
        return sdselGrpNm;
    }

    public void setSdselGrpNm(String sdselGrpNm) {
        this.sdselGrpNm = sdselGrpNm;
    }

    public String getSdselClassCd() {
        return sdselClassCd;
    }

    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    public String getSdselClassNm() {
        return sdselClassNm;
    }

    public void setSdselClassNm(String sdselClassNm) {
        this.sdselClassNm = sdselClassNm;
    }

    public String getRegStoreFg() { return regStoreFg; }

    public void setRegStoreFg(String regStoreFg) { this.regStoreFg = regStoreFg; }
}