package kr.co.common.data.domain;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : VanVO.java
 * @Description : 벤사 조회 공통 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VanVO extends CmmVO {

    private static final long serialVersionUID = -2340647114412075524L;
    /** 벤사구분 */
    private String vanFg;
    /** 밴사코드 */
    private String vanCd;
    /** 밴사명 */
    private String vanNm;
    /** 전화번호 */
    private String telNo;
    /** 팩스번호 */
    private String faxNo;
    /** 메인IP */
    private String mainIp;
    /** 메인PORT */
    private String mainPort;
    /** 서브IP */
    private String subIp;
    /** 서브PORT */
    private String subPort;


    /**
     * @return the vanFg
     */
    public String getVanFg() {
        return vanFg;
    }
    /**
     * @param vanFg the vanFg to set
     */
    public void setVanFg(String vanFg) {
        this.vanFg = vanFg;
    }
    /**
     * @return the vanCd
     */
    public String getVanCd() {
        return vanCd;
    }
    /**
     * @param vanCd the vanCd to set
     */
    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }
    /**
     * @return the vanNm
     */
    public String getVanNm() {
        return vanNm;
    }
    /**
     * @param vanNm the vanNm to set
     */
    public void setVanNm(String vanNm) {
        this.vanNm = vanNm;
    }
    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }
    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    /**
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }
    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }
    /**
     * @return the mainIp
     */
    public String getMainIp() {
        return mainIp;
    }
    /**
     * @param mainIp the mainIp to set
     */
    public void setMainIp(String mainIp) {
        this.mainIp = mainIp;
    }
    /**
     * @return the mainPort
     */
    public String getMainPort() {
        return mainPort;
    }
    /**
     * @param mainPort the mainPort to set
     */
    public void setMainPort(String mainPort) {
        this.mainPort = mainPort;
    }
    /**
     * @return the subIp
     */
    public String getSubIp() {
        return subIp;
    }
    /**
     * @param subIp the subIp to set
     */
    public void setSubIp(String subIp) {
        this.subIp = subIp;
    }
    /**
     * @return the subPort
     */
    public String getSubPort() {
        return subPort;
    }
    /**
     * @param subPort the subPort to set
     */
    public void setSubPort(String subPort) {
        this.subPort = subPort;
    }

}
