package kr.co.solbipos.adi.etc.kitchenmemo.service;

import kr.co.solbipos.adi.etc.kitchenmemo.service.enums.KitchenMemoEnvFg;
import kr.co.solbipos.application.common.service.CmmVO;

/**
* @Class Name : KitchenMemoVO.java
* @Description : 부가서비스 > 주방메모관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class KitchenMemoVO extends CmmVO{

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 주방메모코드 */
    private String kitchnMemoCd;
    /** 주방메모명 */
    private String kitchnMemoNm;
    /** 메모구분  */
    private String memoFg;
    /** 사용여부  */
    private String useYn;
    /** 본사통제여부 ( 1: 본사통제, 2:매장통제 )  */
    private KitchenMemoEnvFg envstVal;
    /** 등록주체구분 */
    private String regFg;



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
     * @return the kitchnMemoCd
     */

    public String getKitchnMemoCd() {
        return kitchnMemoCd;
    }

    /**
     * @param kitchnMemoCd the kitchnMemoCd to set
     */
    public void setKitchnMemoCd(String kitchnMemoCd) {
        this.kitchnMemoCd = kitchnMemoCd;
    }

    /**
     * @return the kitchnMemoNm
     */

    public String getKitchnMemoNm() {
        return kitchnMemoNm;
    }

    /**
     * @param kitchnMemoNm the kitchnMemoNm to set
     */
    public void setKitchnMemoNm(String kitchnMemoNm) {
        this.kitchnMemoNm = kitchnMemoNm;
    }

    /**
     * @return the memoFg
     */

    public String getMemoFg() {
        return memoFg;
    }

    /**
     * @param memoFg the memoFg to set
     */
    public void setMemoFg(String memoFg) {
        this.memoFg = memoFg;
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
     * @return the envstVal
     */

    public KitchenMemoEnvFg getEnvstVal() {
        return envstVal;
    }

    /**
     * @param envstVal the envstVal to set
     */
    public void setEnvstVal(KitchenMemoEnvFg envstVal) {
        this.envstVal = envstVal;
    }

    /**
     * @return the regFg
     */

    public String getRegFg() {
        return regFg;
    }

    /**
     * @param regFg the regFg to set
     */
    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }
}
