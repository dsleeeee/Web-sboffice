package kr.co.solbipos.store.hq.hqmanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;

/**
 * @Class Name : HqBrandVO.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
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
public class HqMenuVO extends PageVO {

    private static final long serialVersionUID = -2477533044339104751L;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 복사할 본사코드 */
    private String copyHqOfficeCd;
    /** 메뉴 리소스 코드 */
    private String resrceCd;
    /** 포함 제외 여부 */
    private IncldExcldFg incldExcldFg;
    
    
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
     * @return the copyHqOfficeCd
     */
    public String getCopyHqOfficeCd() {
        return copyHqOfficeCd;
    }
    /**
     * @param copyHqOfficeCd the copyHqOfficeCd to set
     */
    public void setCopyHqOfficeCd(String copyHqOfficeCd) {
        this.copyHqOfficeCd = copyHqOfficeCd;
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
     * @return the incldExcldFg
     */
    public IncldExcldFg getIncldExcldFg() {
        return incldExcldFg;
    }
    /**
     * @param incldExcldFg the incldExcldFg to set
     */
    public void setIncldExcldFg(IncldExcldFg incldExcldFg) {
        this.incldExcldFg = incldExcldFg;
    }
    
}
