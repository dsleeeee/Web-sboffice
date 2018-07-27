package kr.co.solbipos.store.hq.brand.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : HqBrandVO.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class HqBrandVO extends CmmVO {

    private static final long serialVersionUID = 829520160199087941L;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 브랜드명 */
    private String hqBrandNm;
    /** 사용여부 */
    private UseYn UseYn;
    
    
    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }
    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }
    /**
     * @return the hqOfficeNm
     */
    public String getHqOfficeNm() {
        return hqOfficeNm;
    }
    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }
    /**
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }
    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }
    /**
     * @return the hqBrandNm
     */
    public String getHqBrandNm() {
        return hqBrandNm;
    }
    /**
     * @param hqBrandNm the hqBrandNm to set
     */
    public void setHqBrandNm(String hqBrandNm) {
        this.hqBrandNm = hqBrandNm;
    }
    /**
     * @return the useYn
     */
    public UseYn getUseYn() {
        return UseYn;
    }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        UseYn = useYn;
    }
    
}
