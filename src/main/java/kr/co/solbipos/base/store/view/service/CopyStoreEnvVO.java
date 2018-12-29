package kr.co.solbipos.base.store.view.service;

import kr.co.solbipos.application.common.service.PageVO;

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
}
