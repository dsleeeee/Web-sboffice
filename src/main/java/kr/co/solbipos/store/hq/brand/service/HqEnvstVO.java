package kr.co.solbipos.store.hq.brand.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.store.hq.brand.enums.TargtFg;

/**
 * @Class Name : HqEnvstVO.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리 > 환경설정
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
public class HqEnvstVO extends CmmVO{
    
    private static final long serialVersionUID = 713050528057597639L;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 환경설정코드 */
    private String envstCd;
    /** 환경설정명 */
    private String envstNm;
    /** 환경설정그룹코드 */
    private String envstGrpCd;
    /** 환경설정 기본값 */
    private String envDefault;
    /** 환경설정값 */
    private String envstVal;
    /** 대상구분 */
    private TargtFg targtFg;
    /** 직접입력여부 */
    private String dirctInYn;
    /** 사용여부 */
    private UseYn useYn;
    
    
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
     * @return the envstCd
     */
    public String getEnvstCd() {
        return envstCd;
    }
    /**
     * @param envstCd the envstCd to set
     */
    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }
    /**
     * @return the envstNm
     */
    public String getEnvstNm() {
        return envstNm;
    }
    /**
     * @param envstNm the envstNm to set
     */
    public void setEnvstNm(String envstNm) {
        this.envstNm = envstNm;
    }
    /**
     * @return the envstGrpCd
     */
    public String getEnvstGrpCd() {
        return envstGrpCd;
    }
    /**
     * @param envstGrpCd the envstGrpCd to set
     */
    public void setEnvstGrpCd(String envstGrpCd) {
        this.envstGrpCd = envstGrpCd;
    }
    /**
     * @return the envDefault
     */
    public String getEnvDefault() {
        return envDefault;
    }
    /**
     * @param envDefault the envDefault to set
     */
    public void setEnvDefault(String envDefault) {
        this.envDefault = envDefault;
    }
    /**
     * @return the envstVal
     */
    public String getEnvstVal() {
        return envstVal;
    }
    /**
     * @param envstVal the envstVal to set
     */
    public void setEnvstVal(String envstVal) {
        this.envstVal = envstVal;
    }
    /**
     * @return the targtFg
     */
    public TargtFg getTargtFg() {
        return targtFg;
    }
    /**
     * @param targtFg the targtFg to set
     */
    public void setTargtFg(TargtFg targtFg) {
        this.targtFg = targtFg;
    }
    /**
     * @return the dirctInYn
     */
    public String getDirctInYn() {
        return dirctInYn;
    }
    /**
     * @param dirctInYn the dirctInYn to set
     */
    public void setDirctInYn(String dirctInYn) {
        this.dirctInYn = dirctInYn;
    }
    /**
     * @return the useYn
     */
    public UseYn getUseYn() {
        return useYn;
    }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }
    
}
