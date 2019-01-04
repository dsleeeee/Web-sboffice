package kr.co.solbipos.base.store.view.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.store.view.service.enums.StoreEnv;

/**
* @Class Name : CopyStoreEnvVO.java
* @Description : 기초관리 > 매장관리 > 매장정보조회 > 매장환경복사
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.12.28  김지은      최초생성
*
* @author 솔비포스 김지은
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class CopyStoreEnvVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;

    /** 원매장코드 */
    private String originalStoreCd;

    /** 복사대상매장코드 */
    private String targetStoreCd;

    /** 복사할 매장환경 코드 */
    private StoreEnv nmcodeCd;

    /** 환경설정 구분 */
    private String envstFg;

    /** XML 설정구분 */
    private String confgFg;

    /** XML */
    private String xml;

    /** 프로시져 실행 결과 */
    private String result;

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
     * @return the originalStoreCd
     */

    public String getOriginalStoreCd() {
        return originalStoreCd;
    }

    /**
     * @param originalStoreCd the originalStoreCd to set
     */
    public void setOriginalStoreCd(String originalStoreCd) {
        this.originalStoreCd = originalStoreCd;
    }

    /**
     * @return the targetStoreCd
     */

    public String getTargetStoreCd() {
        return targetStoreCd;
    }

    /**
     * @param targetStoreCd the targetStoreCd to set
     */
    public void setTargetStoreCd(String targetStoreCd) {
        this.targetStoreCd = targetStoreCd;
    }

    /**
     * @return the nmcodeCd
     */

    public StoreEnv getNmcodeCd() {
        return nmcodeCd;
    }

    /**
     * @param nmcodeCd the nmcodeCd to set
     */
    public void setNmcodeCd(StoreEnv nmcodeCd) {
        this.nmcodeCd = nmcodeCd;
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
     * @return the confgFg
     */

    public String getConfgFg() {
        return confgFg;
    }

    /**
     * @param confgFg the confgFg to set
     */
    public void setConfgFg(String confgFg) {
        this.confgFg = confgFg;
    }

    /**
     * @return the xml
     */

    public String getXml() {
        return xml;
    }

    /**
     * @param xml the xml to set
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

    /**
     * @return the result
     */

    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
}
