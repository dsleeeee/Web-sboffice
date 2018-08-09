package kr.co.solbipos.sys.auth.authgroup.service;

import java.util.List;
import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : AuthorGrpResrceVO.java
 * @Description : 시스템관리 > 권한관리 > 권한 그룹 관리 > 리소스 정보<br>TB_WB_AUTHOR_GRP_RESRCE
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
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AuthorGrpResrceVO extends CmmVO {

    private static final long serialVersionUID = -4163325563957957588L;
    /** 그룹 코드 */
    private String authGrpCd;
    /** 리소스 코드 */
    private String resrceCd;
    /** 사용여부 */
    private UseYn useYn;
    /** 상위 리소스 */
    private String pResrce;
    /** 리소스 명 */
    private String resrceNm;
    /** URL */
    private String url;
    /** 리소스 화면 표시 내용 */
    private String resrceDisp;
    /** 권한 보유 여부 */
    private Boolean authFg;
    /** 최상단 메뉴 아이콘 명 */
    private String iconNm;
    /** Child Items */
    private List<AuthorGrpResrceVO> items;
    
    
    /**
     * @return the authGrpCd
     */
    public String getAuthGrpCd() {
        return authGrpCd;
    }
    /**
     * @param authGrpCd the authGrpCd to set
     */
    public void setAuthGrpCd(String authGrpCd) {
        this.authGrpCd = authGrpCd;
    }
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
     * @return the resrceDisp
     */
    public String getResrceDisp() {
        return resrceDisp;
    }
    /**
     * @param resrceDisp the resrceDisp to set
     */
    public void setResrceDisp(String resrceDisp) {
        this.resrceDisp = resrceDisp;
    }
    /**
     * @return the authFg
     */
    public Boolean getAuthFg() {
        return authFg;
    }
    /**
     * @param authFg the authFg to set
     */
    public void setAuthFg(Boolean authFg) {
        this.authFg = authFg;
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
     * @return the items
     */
    public List<AuthorGrpResrceVO> getItems() {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(List<AuthorGrpResrceVO> items) {
        this.items = items;
    }
    
}
