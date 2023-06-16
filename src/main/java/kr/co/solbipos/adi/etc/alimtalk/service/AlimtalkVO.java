package kr.co.solbipos.adi.etc.alimtalk.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : AlimtalkVO.java
 * @Description : 맘스터치 > 기타관리 > 매출트레킹수신자목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.06.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AlimtalkVO extends CmmVO {

    private static final long serialVersionUID = -1046090570078823455L;

    /** 소속구분 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 가맹점코드 */
    private String storeCd;
    /** 알림톡구분 */
    private String alimtalkFg;
    /** 수신자번호 */
    private String mpNo;
    /** 수신자정보 */
    private String mpInfo;
    /** 비고 */
    private String remark;

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

    public String getAlimtalkFg() {
        return alimtalkFg;
    }

    public void setAlimtalkFg(String alimtalkFg) {
        this.alimtalkFg = alimtalkFg;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getMpInfo() {
        return mpInfo;
    }

    public void setMpInfo(String mpInfo) {
        this.mpInfo = mpInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}