package kr.co.solbipos.sys.link.naverPlaceStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : NaverPlaceStatusVO.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class NaverPlaceStatusVO extends PageVO {

    private static final long serialVersionUID = -2271922580159167926L;

    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 메뉴 리소스 코드 */
    private String resrceCd;
    /** 메뉴 리소스 명 */
    private String resrceNm;

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

    public String getResrceCd() {
        return resrceCd;
    }

    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }

    public String getResrceNm() {
        return resrceNm;
    }

    public void setResrceNm(String resrceNm) {
        this.resrceNm = resrceNm;
    }
}
