package kr.co.solbipos.application.common.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.common.data.enums.UseYn;
import kr.co.common.validate.WebMenuSave;
import kr.co.solbipos.application.common.enums.ResrceFg;

/**
 * @Class Name : ResrceInfoVO.java
 * @Description : 리소스<br>table : TB_WB_RESRCE_INFO
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ResrceInfoVO extends CmmVO {

    private static final long serialVersionUID = 6149780192504788158L;
    /** 리소스 코드 */
    private String resrceCd;
    /** 상위 리소스 */
    private String pResrce;
    /** 리소스 구분 */
    private ResrceFg resrceFg;
    /** 리소스 배열 : 메뉴 관리에서 기능 목록을 저장할때 사용 */
    private ResrceInfoVO[] resrceInfoArr;
    /** 기능 구분 */
    private String funcFg;
    /** 리소스 명 */
    @NotBlank(groups = {WebMenuSave.class}, message = "{webMenu.nm}{cmm.require.text}")
    @Size(groups = {WebMenuSave.class}, max = 45, message = "{webMenu.nm} {cmm.size.max}")
    private String resrceNm;
    /** URL */
    @Size(groups = {WebMenuSave.class}, max = 150, message = "URL {cmm.size.max}")
    private String url;
    /** 특수 권한 */
    private String spclAuthor;
    /** 표기 레벨 */
    private Long dispLevel;
    /** 표기 인덱스 */
    @NotNull(groups = {WebMenuSave.class}, message = "{webMenu.dispIndx}{cmm.require.text}")
    private Long dispIdx;
    /** 메뉴 아이콘명 */
    private String iconNm;
    /** 사용 여부 */
    private UseYn useYn;
    
    
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
     * @return the resrceFg
     */
    public ResrceFg getResrceFg() {
        return resrceFg;
    }
    /**
     * @param resrceFg the resrceFg to set
     */
    public void setResrceFg(ResrceFg resrceFg) {
        this.resrceFg = resrceFg;
    }
    /**
     * @return the resrceInfoArr
     */
    public ResrceInfoVO[] getResrceInfoArr() {
        return resrceInfoArr;
    }
    /**
     * @param resrceInfoArr the resrceInfoArr to set
     */
    public void setResrceInfoArr(ResrceInfoVO[] resrceInfoArr) {
        this.resrceInfoArr = resrceInfoArr;
    }
    /**
     * @return the funcFg
     */
    public String getFuncFg() {
        return funcFg;
    }
    /**
     * @param funcFg the funcFg to set
     */
    public void setFuncFg(String funcFg) {
        this.funcFg = funcFg;
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
     * @return the spclAuthor
     */
    public String getSpclAuthor() {
        return spclAuthor;
    }
    /**
     * @param spclAuthor the spclAuthor to set
     */
    public void setSpclAuthor(String spclAuthor) {
        this.spclAuthor = spclAuthor;
    }
    /**
     * @return the dispLevel
     */
    public Long getDispLevel() {
        return dispLevel;
    }
    /**
     * @param dispLevel the dispLevel to set
     */
    public void setDispLevel(Long dispLevel) {
        this.dispLevel = dispLevel;
    }
    /**
     * @return the dispIdx
     */
    public Long getDispIdx() {
        return dispIdx;
    }
    /**
     * @param dispIdx the dispIdx to set
     */
    public void setDispIdx(Long dispIdx) {
        this.dispIdx = dispIdx;
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
