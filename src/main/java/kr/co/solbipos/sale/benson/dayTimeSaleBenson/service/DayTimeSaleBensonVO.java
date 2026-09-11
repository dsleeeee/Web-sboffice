package kr.co.solbipos.sale.benson.dayTimeSaleBenson.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DayTimeSaleBensonVO.java
 * @Description : 벤슨 > 매출분석 > 일별시간대별매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DayTimeSaleBensonVO extends PageVO {

    private static final long serialVersionUID = 8123094904301269301L;

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

    /** 조회 시작시간 */
    private String startTime;

    /** 조회 종료시간 */
    private String endTime;

    /** 시간대 Array */
    private String[] arrTimeCol;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String[] getArrTimeCol() {
        return arrTimeCol;
    }

    public void setArrTimeCol(String[] arrTimeCol) {
        this.arrTimeCol = arrTimeCol;
    }
}
