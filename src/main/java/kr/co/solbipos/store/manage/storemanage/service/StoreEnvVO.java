package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StoreEnvVO.java
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
public class StoreEnvVO extends CmmVO {

    private static final long serialVersionUID = -8250525839006427440L;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 환경설정코드 */
    private String envstCd;
    /** 환경설정명 */
    private String envstNm;
    /** 환경설정구분 */
    private String envstFg;
    /** 환경설정그룹코드 */
    private String envGrpCd;
    /** 대상구분 */
    private String targtFg;
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
    /** 사용여부  (Y:사용, N:미사용) */
    private UseYn useYn;
    /** 기능키 관련 프로시져 실행 결과 */
    private String result;
    /** 검색 */
    private String envst;
    /** 세션ID */
    private String sessionId;
    /** 환경설정사용등록 */
    private String envst1266;

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
     * @return the result
     */
    public String getResult() { return result; }
    /**
     * @param result the result to set
     */
    public void setResult(String result) { this.result = result; }

    public String getEnvst() {
        return envst;
    }

    public void setEnvst(String envst) {
        this.envst = envst;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEnvst1266() {
        return envst1266;
    }

    public void setEnvst1266(String envst1266) {
        this.envst1266 = envst1266;
    }
}
