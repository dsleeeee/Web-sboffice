package kr.co.solbipos.adi.sms.remainAmtChk.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : RemainAmtChkVO.java
 * @Description : 부가서비스 > SMS분석 > 잔여금액확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class RemainAmtChkVO extends PageVO {

    private static final long serialVersionUID = -5887543869185615796L;

    /** 조회일자 */
    private String startDate;
    private String endDate;

    /** 소속코드 */
    private String srchOrgnCd;
    /** 소속명 */
    private String srchOrgnNm;

    /** */
    private String nowDate;

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSrchOrgnCd() {
        return srchOrgnCd;
    }

    public void setSrchOrgnCd(String srchOrgnCd) {
        this.srchOrgnCd = srchOrgnCd;
    }

    public String getSrchOrgnNm() {
        return srchOrgnNm;
    }

    public void setSrchOrgnNm(String srchOrgnNm) {
        this.srchOrgnNm = srchOrgnNm;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }
}
