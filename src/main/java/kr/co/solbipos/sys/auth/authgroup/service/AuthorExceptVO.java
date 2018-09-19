package kr.co.solbipos.sys.auth.authgroup.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;

/**
 * @Class Name : AuthorGrpResrceVO.java
 * @Description : 시스템관리 > 권한관리 > 권한 그룹 관리 > 리소스 정보<br>TB_WB_AUTHOR_EXCEPT
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AuthorExceptVO extends CmmVO {

    private static final long serialVersionUID = -3700922601075031244L;
    /** 사용자 아이디 */
    private String userId;
    /** 리소스 코드 */
    private String resrceCd;
    /** 포함/제외 여부 */
    private IncldExcldFg incldExcldFg;
    /** 사용여부 */
    private UseYn useYn;
    
    
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
