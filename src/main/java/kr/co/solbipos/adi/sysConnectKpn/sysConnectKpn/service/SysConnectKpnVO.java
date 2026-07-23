package kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : SysConnectKpnVO.java
 * @Description : 부가서비스 > 정산 > KPN시스템접속
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SysConnectKpnVO extends CmmVO {

    private static final long serialVersionUID = 1L;

    /** API 번호 (TB_CM_API_LINK_INFO.API_NO) */
    private String apiNo;

    /** 통합인증토큰 */
    private String intgCerTkn;

    /** 통합인증토큰 발행일자 */
    private String intgCerTknPblcDate;

    /** 통합인증토큰 발행시각 */
    private String intgCerTknPblcTime;

    /** 통합인증토큰 만료일자 */
    private String intgCerTknDdlDate;

    /** 통합인증토큰 만료시각 */
    private String intgCerTknDdlTime;

    public String getApiNo() {
        return apiNo;
    }

    public void setApiNo(String apiNo) {
        this.apiNo = apiNo;
    }

    public String getIntgCerTkn() {
        return intgCerTkn;
    }

    public void setIntgCerTkn(String intgCerTkn) {
        this.intgCerTkn = intgCerTkn;
    }

    public String getIntgCerTknPblcDate() {
        return intgCerTknPblcDate;
    }

    public void setIntgCerTknPblcDate(String intgCerTknPblcDate) {
        this.intgCerTknPblcDate = intgCerTknPblcDate;
    }

    public String getIntgCerTknPblcTime() {
        return intgCerTknPblcTime;
    }

    public void setIntgCerTknPblcTime(String intgCerTknPblcTime) {
        this.intgCerTknPblcTime = intgCerTknPblcTime;
    }

    public String getIntgCerTknDdlDate() {
        return intgCerTknDdlDate;
    }

    public void setIntgCerTknDdlDate(String intgCerTknDdlDate) {
        this.intgCerTknDdlDate = intgCerTknDdlDate;
    }

    public String getIntgCerTknDdlTime() {
        return intgCerTknDdlTime;
    }

    public void setIntgCerTknDdlTime(String intgCerTknDdlTime) {
        this.intgCerTknDdlTime = intgCerTknDdlTime;
    }
}
