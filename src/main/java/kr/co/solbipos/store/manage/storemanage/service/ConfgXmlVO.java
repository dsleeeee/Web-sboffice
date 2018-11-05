package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.store.common.enums.ConfgFg;

/**
 * @Class Name : ConfgXmlVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ConfgXmlVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 복사 대상 매장코드 */
    private String copyStoreCd;
    /** 포스번호 */
    private String posNo;
    /** 설정구분 */
    private ConfgFg confgFg;
    /** XML */
    private String xml;
    /** 사용여부 */
    private UseYn useYn;

    /** 여러 포스값 */
    private String[] arrPosNo;


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
     * @return the copyStoreCd
     */

    public String getCopyStoreCd() {
        return copyStoreCd;
    }

    /**
     * @param copyStoreCd the copyStoreCd to set
     */
    public void setCopyStoreCd(String copyStoreCd) {
        this.copyStoreCd = copyStoreCd;
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
     * @return the confgFg
     */

    public ConfgFg getConfgFg() {
        return confgFg;
    }

    /**
     * @param confgFg the confgFg to set
     */
    public void setConfgFg(ConfgFg confgFg) {
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
     * @return the arrPosNo
     */

    public String[] getArrPosNo() {
        return arrPosNo;
    }

    /**
     * @param arrPosNo the arrPosNo to set
     */
    public void setArrPosNo(String[] arrPosNo) {
        this.arrPosNo = arrPosNo;
    }
}
