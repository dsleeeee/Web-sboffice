package kr.co.solbipos.application.common.service;

import java.util.List;

/**
 * @Class Name : MenuVO.java
 * @Description : 메뉴 Tree 구조
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MenuVO extends CmmVO {
    
    private static final long serialVersionUID = 7366866674694441209L;
    /** 메뉴명 */
    private String nm;
    /** URL */
    private String url;
    /** 메뉴 아이콘명 */
    private String icon;
    /** 상위 리소스 코드 */
    private String pcd;
    /** 리소스 코드 */
    private String cd;
    /** Child Items */
    private List<MenuVO> items;
    
    
    /**
     * @return the nm
     */
    public String getNm() {
        return nm;
    }
    /**
     * @param nm the nm to set
     */
    public void setNm(String nm) {
        this.nm = nm;
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
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }
    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * @return the pcd
     */
    public String getPcd() {
        return pcd;
    }
    /**
     * @param pcd the pcd to set
     */
    public void setPcd(String pcd) {
        this.pcd = pcd;
    }
    /**
     * @return the cd
     */
    public String getCd() {
        return cd;
    }
    /**
     * @param cd the cd to set
     */
    public void setCd(String cd) {
        this.cd = cd;
    }
    /**
     * @return the items
     */
    public List<MenuVO> getItems() {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(List<MenuVO> items) {
        this.items = items;
    }
    
}
