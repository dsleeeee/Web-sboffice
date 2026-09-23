package kr.co.solbipos.base.prod.sidemenu.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : SideMenuCopyReqVO.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴 - 복사요청 멱등처리(자동 재전송 차단)용 VO
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.22  김유승      최초생성 (선택분류/선택상품 복사 자동 재전송 차단)
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026. 09. 22.
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SideMenuCopyReqVO extends CmmVO {

    private static final long serialVersionUID = 4712596388267600001L;

    /** 요청 nonce(1회용, PK) */
    private String reqNonce;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 요청 계정 */
    private String userId;
    /** 요청 종류 (CLASS_COPY/PROD_COPY) */
    private String reqType;
    /** 요청 건수(참고용) */
    private int reqCnt;
    /** 처리상태 (ING/DONE/ERR) - ※ CmmVO.status(GridDataFg)와 충돌 회피 위해 procStatus 사용 */
    private String procStatus;

    public String getReqNonce() {
        return reqNonce;
    }

    public void setReqNonce(String reqNonce) {
        this.reqNonce = reqNonce;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public int getReqCnt() {
        return reqCnt;
    }

    public void setReqCnt(int reqCnt) {
        this.reqCnt = reqCnt;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }
}
