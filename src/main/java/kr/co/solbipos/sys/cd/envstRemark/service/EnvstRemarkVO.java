package kr.co.solbipos.sys.cd.envstRemark.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : EnvstRemarkVO.java
 * @Description : 시스템관리 > 코드관리 > 환경설정기능설명
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class EnvstRemarkVO extends CmmVO {

    private static final long serialVersionUID = 8257152089649317467L;
    /** 환경설정코드 */
    private String envstCd;
    /** 환경설정명 */
    private String envstNm;
    /** 환경설정구분 */
    private String envstFg;
    /** 환경설정구분명 */
    private String envstFgNm;
    /** 환경설정그룹코드 */
    private String envstGrpCd;
    /** 환경설정그룹코드명 */
    private String envstGrpCdNm;
    /** 직접입력여부 */
    private String dirctInYn;
    /** 대상구분 */
    private String targtFg;
    /** 대상구분명 */
    private String targtFgNm;
    /** 사용여부 */
    private String useYn;
    /** 비고 */
    private String remark;
    /** 비고2 */
    private String envstRemark;
    /**  */
    private String requiredYn;
    
    
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
     * @return the envstFg
     */
    public String getEnvstFg() {
        return envstFg;
    }
    /**
     * @param envstFg the envstFg to set
     */
    public void setEnvstFg(String envstFg) {
        this.envstFg = envstFg;
    }
    /**
     * @return the envstFgNm
     */
    public String getEnvstFgNm() {
        return envstFgNm;
    }
    /**
     * @param envstFgNm the envstFgNm to set
     */
    public void setEnvstFgNm(String envstFgNm) {
        this.envstFgNm = envstFgNm;
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
     * @return the envstGrpCdNm
     */
    public String getEnvstGrpCdNm() {
        return envstGrpCdNm;
    }
    /**
     * @param envstGrpCdNm the envstGrpCdNm to set
     */
    public void setEnvstGrpCdNm(String envstGrpCdNm) {
        this.envstGrpCdNm = envstGrpCdNm;
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
     * @return the targtFg
     */
    public String getTargtFg() {
        return targtFg;
    }
    /**
     * @param targtFg the targtFg to set
     */
    public void setTargtFg(String targtFg) {
        this.targtFg = targtFg;
    }
    /**
     * @return the targtFgNm
     */
    public String getTargtFgNm() {
        return targtFgNm;
    }
    /**
     * @param targtFgNm the targtFgNm to set
     */
    public void setTargtFgNm(String targtFgNm) {
        this.targtFgNm = targtFgNm;
    }
    /**
     * @return the useYn
     */
    public String getUseYn() {
        return useYn;
    }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEnvstRemark() {
        return envstRemark;
    }

    public void setEnvstRemark(String envstRemark) {
        this.envstRemark = envstRemark;
    }

    /**
     * @return the requiredYn
     */
    public String getRequiredYn() {
        return requiredYn;
    }
    /**
     * @param requiredYn the requiredYn to set
     */
    public void setRequiredYn(String requiredYn) {
        this.requiredYn = requiredYn;
    }
    
}
