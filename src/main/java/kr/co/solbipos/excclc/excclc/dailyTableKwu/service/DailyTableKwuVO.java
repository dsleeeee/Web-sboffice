package kr.co.solbipos.excclc.excclc.dailyTableKwu.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : DailyTableKwuVO.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DailyTableKwuVO extends PageVO {

    private static final long serialVersionUID = -6606377306049900821L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 Array */
    private String arrStoreCd[];

    /** 매장코드 */
    private String storeCd;

    /** 회원소속코드 */
    private String membrOrgnCd;

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

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getMembrOrgnCd() { return membrOrgnCd; }

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }
}