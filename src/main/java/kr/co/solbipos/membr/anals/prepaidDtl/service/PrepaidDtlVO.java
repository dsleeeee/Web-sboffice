package kr.co.solbipos.membr.anals.prepaidDtl.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PrepaidDtlVO.java
 * @Description : 회원관리 > 회원분석 > 선불금 충전/사용내역 상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.05.27  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.05.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PrepaidDtlVO extends PageVO {

    private static final long serialVersionUID = -3292539878217761575L;

    /** 회원소속코드 */
    private String membrOrgnCd;
    /** 회원번호 */
    private String membrNo;
    /** 회원명 */
    private String membrNm;
    /** 선불회원 등록 매장 코드 */
    private String storeCd;
    /** 조회매장 */
    private String storeCds;
    /** 조회매장 */
    private String[] storeCdList;

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getMembrNo() {
        return membrNo;
    }

    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    public String getMembrNm() {
        return membrNm;
    }

    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }
}
