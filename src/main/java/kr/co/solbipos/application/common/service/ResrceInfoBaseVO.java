package kr.co.solbipos.application.common.service;

/**
 * @Class Name : ResrceInfoBaseVO.java
 * @Description : 리소스 도메인<br>{@link kr.co.solbipos.application.common.service.ResrceInfoVO}<br>의 경량화 버전
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ResrceInfoBaseVO extends CmmVO {

    private static final long serialVersionUID = 3198051418095524045L;

    /** 리소스 코드 */
    private String resrceCd;
    /** 상위 리소스 */
    private String pResrce;
    /** 리소스 명 */
    private String resrceNm;
    /** URL */
    private String url;
    /** ICON */
    private String iconNm;
    /** 활성화 여부 */
    private boolean activation = false;
    /** 메뉴제한여부 */
    private String chkYn;
    
    
    /**
     * @return the resrceCd
     */
    public String getResrceCd() {
        return resrceCd;
    }
    /**
     * @param resrceCd the resrceCd to set
     */
    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }
    /**
     * @return the pResrce
     */
    public String getpResrce() {
        return pResrce;
    }
    /**
     * @param pResrce the pResrce to set
     */
    public void setpResrce(String pResrce) {
        this.pResrce = pResrce;
    }
    /**
     * @return the resrceNm
     */
    public String getResrceNm() {
        return resrceNm;
    }
    /**
     * @param resrceNm the resrceNm to set
     */
    public void setResrceNm(String resrceNm) {
        this.resrceNm = resrceNm;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the iconNm
     */
    public String getIconNm() {
        return iconNm;
    }

    /**
     * @param iconNm the iconNm to set
     */
    public void setIconNm(String iconNm) {
        this.iconNm = iconNm;
    }

    /**
     * @return the activation
     */

    public boolean isActivation() {
        return activation;
    }
    /**
     * @param activation the activation to set
     */
    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public String getChkYn() {
        return chkYn;
    }

    public void setChkYn(String chkYn) {
        this.chkYn = chkYn;
    }
}
