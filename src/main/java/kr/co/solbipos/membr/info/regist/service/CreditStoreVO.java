package kr.co.solbipos.membr.info.regist.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : CreditStoreVO.java
 * @Description : 기초관리 > 결제수단 > 후불회원등록매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CreditStoreVO extends CmmVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 회원번호 */
    private String memberNo;
    /** 후불회원 등록 매장 */
    private String creditStoreCd;

    /**
     * @return the hqOfficeCd
     */

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the memberNo
     */

    public String getMemberNo() {
        return memberNo;
    }

    /**
     * @param memberNo the memberNo to set
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    /**
     * @return the creditStoreCd
     */

    public String getCreditStoreCd() {
        return creditStoreCd;
    }

    /**
     * @param creditStoreCd the creditStoreCd to set
     */
    public void setCreditStoreCd(String creditStoreCd) {
        this.creditStoreCd = creditStoreCd;
    }
}
