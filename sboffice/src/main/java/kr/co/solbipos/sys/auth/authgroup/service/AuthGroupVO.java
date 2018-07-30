package kr.co.solbipos.sys.auth.authgroup.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.sys.auth.authgroup.enums.TargetAllFg;

/**
 * @Class Name : AuthGroupVO.java
 * @Description : 시스템관리 > 권한관리 > 권한 그룹 관리
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
public class AuthGroupVO extends CmmVO {

    private static final long serialVersionUID = -4385655718150753431L;
    /** 그룹 코드 */
    private String authGrpCd;
    /** 그룹 명 */
    private String authGrpNm;
    /** 전체 적용 구분 */
    private TargetAllFg targetAllFg;
    /** 대상 소속 */
    private String targetOrgn;
    /** 비고 */
    private String remark;
    /** 사용여부 */
    private UseYn useYn;
    /** 사용자 아이디 */
    private String userId;
    
    
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
     * @return the authGrpNm
     */
    public String getAuthGrpNm() {
        return authGrpNm;
    }
    /**
     * @param authGrpNm the authGrpNm to set
     */
    public void setAuthGrpNm(String authGrpNm) {
        this.authGrpNm = authGrpNm;
    }
    /**
     * @return the targetAllFg
     */
    public TargetAllFg getTargetAllFg() {
        return targetAllFg;
    }
    /**
     * @param targetAllFg the targetAllFg to set
     */
    public void setTargetAllFg(TargetAllFg targetAllFg) {
        this.targetAllFg = targetAllFg;
    }
    /**
     * @return the targetOrgn
     */
    public String getTargetOrgn() {
        return targetOrgn;
    }
    /**
     * @param targetOrgn the targetOrgn to set
     */
    public void setTargetOrgn(String targetOrgn) {
        this.targetOrgn = targetOrgn;
    }
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
    
}
