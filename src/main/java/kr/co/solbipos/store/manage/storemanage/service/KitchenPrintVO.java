package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : KitchenPrintVO.java
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
public class KitchenPrintVO extends CmmVO {

    private static final long serialVersionUID = -4237428030982506491L;
    /** 매장코드 */
    private String storeCd;
    /** 프린터번호 */
    private String prterNo;
    /** 프린터명 */
    private String prterNm;
    /** 포스번호 */
    private String posNo;
    /** 프린터종류구분 */
    private String prterKindFg;
    /** 프린터포트구분 */
    private String prterPortFg;
    /** 프린터속도구분 */
    private String prterSpeedFg;
    /** 프린터넷아이피 */
    private String prterNetIp;
    /** 프린터넷포트 */
    private String prterNetPort;
    /** 프린터출력수량 */
    private String prterOutputQty;
    /** 프린터 상태 체크 */
    private UseYn printCheckYn;
    /** 사용여부  (Y:사용, N:미사용) */
    private UseYn useYn;


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
     * @return the prterNo
     */
    public String getPrterNo() {
        return prterNo;
    }
    /**
     * @param prterNo the prterNo to set
     */
    public void setPrterNo(String prterNo) {
        this.prterNo = prterNo;
    }
    /**
     * @return the prterNm
     */
    public String getPrterNm() {
        return prterNm;
    }
    /**
     * @param prterNm the prterNm to set
     */
    public void setPrterNm(String prterNm) {
        this.prterNm = prterNm;
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
     * @return the prterKindFg
     */
    public String getPrterKindFg() {
        return prterKindFg;
    }
    /**
     * @param prterKindFg the prterKindFg to set
     */
    public void setPrterKindFg(String prterKindFg) {
        this.prterKindFg = prterKindFg;
    }
    /**
     * @return the prterPortFg
     */
    public String getPrterPortFg() {
        return prterPortFg;
    }
    /**
     * @param prterPortFg the prterPortFg to set
     */
    public void setPrterPortFg(String prterPortFg) {
        this.prterPortFg = prterPortFg;
    }
    /**
     * @return the prterSpeedFg
     */
    public String getPrterSpeedFg() {
        return prterSpeedFg;
    }
    /**
     * @param prterSpeedFg the prterSpeedFg to set
     */
    public void setPrterSpeedFg(String prterSpeedFg) {
        this.prterSpeedFg = prterSpeedFg;
    }
    /**
     * @return the prterNetIp
     */
    public String getPrterNetIp() {
        return prterNetIp;
    }
    /**
     * @param prterNetIp the prterNetIp to set
     */
    public void setPrterNetIp(String prterNetIp) {
        this.prterNetIp = prterNetIp;
    }
    /**
     * @return the prterNetPort
     */
    public String getPrterNetPort() {
        return prterNetPort;
    }
    /**
     * @param prterNetPort the prterNetPort to set
     */
    public void setPrterNetPort(String prterNetPort) {
        this.prterNetPort = prterNetPort;
    }
    /**
     * @return the prterOutputQty
     */
    public String getPrterOutputQty() {
        return prterOutputQty;
    }
    /**
     * @param prterOutputQty the prterOutputQty to set
     */
    public void setPrterOutputQty(String prterOutputQty) {
        this.prterOutputQty = prterOutputQty;
    }

    /**
     * @return the printCheckYn
     */

    public UseYn getPrintCheckYn() {
        return printCheckYn;
    }

    /**
     * @param printCheckYn the printCheckYn to set
     */
    public void setPrintCheckYn(UseYn printCheckYn) {
        this.printCheckYn = printCheckYn;
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
