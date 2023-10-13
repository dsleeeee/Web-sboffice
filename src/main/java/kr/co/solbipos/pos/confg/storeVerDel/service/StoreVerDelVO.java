package kr.co.solbipos.pos.confg.storeVerDel.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
* @Class Name : StoreVerDelVO.java
* @Description : 포스관리 > POS 설정관리 > 매장별 POS 버전 삭제
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2023.10.12  이다솜      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 이다솜
* @since 2023.10.12
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class StoreVerDelVO extends PageVO {

    private static final long serialVersionUID = 6048011820972978057L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 포스버전구분 */
    private String progFg;
    /** 버전일련번호 */
    private String verSerNo;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getProgFg() {
        return progFg;
    }

    public void setProgFg(String progFg) {
        this.progFg = progFg;
    }

    public String getVerSerNo() {
        return verSerNo;
    }

    public void setVerSerNo(String verSerNo) {
        this.verSerNo = verSerNo;
    }
}
