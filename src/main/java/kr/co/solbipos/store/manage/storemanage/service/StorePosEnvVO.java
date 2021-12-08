package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StorePosEnvVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리 > 환경설정
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
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StorePosEnvVO extends CmmVO {

    private static final long serialVersionUID = -4479796927422847740L;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 포스번호 */
    private String posNo;
    /** 포스명 */
    private String posNm;
    /** 환경설정코드 */
    private String envstCd;
    /** 환경설정명 */
    private String envstNm;
    /** 환경설정구분 */
    private String envstFg;
    /** 환경설정그룹코드 */
    private String envGrpCd;
    /** 환경설정값 */
    private String envstVal;
    /** 환경설정값코드 */
    private String envstValCd;
    /** 환경설정값명 */
    private String envstValNm;
    /** 기본여부 */
    private String defltYn;
    /** 직접입력여부 (Y:직접, N:선택) */
    private String dirctInYn;
    /** 포스구분 (W:웹, P:포스) */
    private String posFg;
    /** 사용여부  (Y:사용, N:미사용) */
    private UseYn useYn;
    /** 포스 복사시, 타겟 포스 */
    private String targetPosNo;
    /** 검색 */
    private String envst;
    
    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }
    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
    /**
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }
    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }
    /**
     * @return the posNo
     */
    public String getPosNo() {
        return posNo;
    }
    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }
    /**
     * @return the posNm
     */
    public String getPosNm() {
        return posNm;
    }
    /**
     * @param posNm the posNm to set
     */
    public void setPosNm(String posNm) {
        this.posNm = posNm;
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
     * @return the envGrpCd
     */
    public String getEnvGrpCd() {
        return envGrpCd;
    }
    /**
     * @param envGrpCd the envGrpCd to set
     */
    public void setEnvGrpCd(String envGrpCd) {
        this.envGrpCd = envGrpCd;
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
     * @return the envstValCd
     */
    public String getEnvstValCd() {
        return envstValCd;
    }
    /**
     * @param envstValCd the envstValCd to set
     */
    public void setEnvstValCd(String envstValCd) {
        this.envstValCd = envstValCd;
    }
    /**
     * @return the envstValNm
     */
    public String getEnvstValNm() {
        return envstValNm;
    }
    /**
     * @param envstValNm the envstValNm to set
     */
    public void setEnvstValNm(String envstValNm) {
        this.envstValNm = envstValNm;
    }
    /**
     * @return the defltYn
     */
    public String getDefltYn() {
        return defltYn;
    }
    /**
     * @param defltYn the defltYn to set
     */
    public void setDefltYn(String defltYn) {
        this.defltYn = defltYn;
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
     * @return the posFg
     */
    public String getPosFg() {
        return posFg;
    }
    /**
     * @param posFg the posFg to set
     */
    public void setPosFg(String posFg) {
        this.posFg = posFg;
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
    /**
     * @return the targetPosNo
     */
    public String getTargetPosNo() {
        return targetPosNo;
    }
    /**
     * @param targetPosNo the targetPosNo to set
     */
    public void setTargetPosNo(String targetPosNo) {
        this.targetPosNo = targetPosNo;
    }

    public String getEnvst() {
        return envst;
    }

    public void setEnvst(String envst) {
        this.envst = envst;
    }
}
