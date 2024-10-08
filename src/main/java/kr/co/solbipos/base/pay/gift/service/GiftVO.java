package kr.co.solbipos.base.pay.gift.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.data.domain.Page;

/**
 * @Class Name : GiftVO.java
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.18  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class GiftVO extends PageVO {

    /** [소속구분] */
    private OrgnFg orgnFg;
    /** [본사코드] */
    private String hqOfficeCd;
    /** [매장코드] */
    private String storeCd;
    /** [상품권코드] */
    private String giftCd;
    /** [상품권명] */
    private String giftNm;
    /** [권종분류코드] */
    private String payClassCd;
    /** [상품권액면가] */
    private Integer giftUprc = 0;
    /** [사용여부] Y:사용 N:사용안함 */
    private String useYn;
    /** 프로시져 실행 결과 */
    private String result;

    /**
     * @return the orgnFg
     */

    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
    }

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
     * @return the giftCd
     */

    public String getGiftCd() {
        return giftCd;
    }

    /**
     * @param giftCd the giftCd to set
     */
    public void setGiftCd(String giftCd) {
        this.giftCd = giftCd;
    }

    /**
     * @return the giftNm
     */

    public String getGiftNm() {
        return giftNm;
    }

    /**
     * @param giftNm the giftNm to set
     */
    public void setGiftNm(String giftNm) {
        this.giftNm = giftNm;
    }

    /**
     * @return the payClassCd
     */

    public String getPayClassCd() {
        return payClassCd;
    }

    /**
     * @param payClassCd the payClassCd to set
     */
    public void setPayClassCd(String payClassCd) {
        this.payClassCd = payClassCd;
    }

    /**
     * @return the giftUprc
     */

    public Integer getGiftUprc() {
        return giftUprc;
    }

    /**
     * @param giftUprc the giftUprc to set
     */
    public void setGiftUprc(Integer giftUprc) {
        this.giftUprc = giftUprc;
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
