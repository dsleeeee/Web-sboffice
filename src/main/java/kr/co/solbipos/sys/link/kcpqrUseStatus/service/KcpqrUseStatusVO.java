package kr.co.solbipos.sys.link.kcpqrUseStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : KcpqrUseStatusVO.java
 * @Description : 시스템관리 > 연동 > KCPQR현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class KcpqrUseStatusVO extends PageVO {

    private static final long serialVersionUID = -1078638295980643838L;
    
    /** 현황 구부 */
    private String srchFg;
    
    /** 오늘 날짜 */
    private String today;

    public String getSrchFg() {
        return srchFg;
    }

    public void setSrchFg(String srchFg) {
        this.srchFg = srchFg;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
