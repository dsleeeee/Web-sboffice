package kr.co.solbipos.pos.license.instlManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : InstlManageVO.java
 * @Description : 포스관리 > 라이선스관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.10.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class InstlManageVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 업체코드 */
    private String agencyCd;

    /** 업체명 */
    private String agencyNm;

    /** 전체기간체크 */
    private boolean chkDt;

    /** 매장코드 */
    private String storeCd;

    /** 포스번호 */
    private String posNo;

    /** 사용여부 */
    private String useYn;

    /** 업체구분 */
    private String agencyFg;

    /** 소속코드 */
    private String orgnCd;

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public boolean getChkDt() { return chkDt; }

    public void setChkDt(boolean chkDt) { this.chkDt = chkDt; }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getAgencyFg() {
        return agencyFg;
    }

    public void setAgencyFg(String agencyFg) {
        this.agencyFg = agencyFg;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }
}
