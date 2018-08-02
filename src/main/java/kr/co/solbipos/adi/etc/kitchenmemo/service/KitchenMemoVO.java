package kr.co.solbipos.adi.etc.kitchenmemo.service;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.common.validate.Login;
import kr.co.common.validate.PwChange;
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
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class KitchenMemoVO extends CmmVO{

    private static final long serialVersionUID = 1L;
    /** 매장코드 */
    private String storeCd;
    /** 주방메모코드 */
    @NotBlank
    @Size(   min = 2, max = 3, message = "{msg.cmm.size.max}")
    private String kitchnMemoCd;
    /** 주방메모명 */
    @NotBlank
    @Size(groups = {PwChange.class}, min = 1, max = 50, message = "{msg.cmm.size.max}")
    private String kitchnMemoNm;
    /** 메모구분  */
    @NotBlank
    @Size( groups={ Login.class }, max = 1, message = "{msg.cmm.size.max}" )
    private String memoFg;
    /** 사용여부  */
    @NotBlank
    private String useYn;
    
    
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
    
}
