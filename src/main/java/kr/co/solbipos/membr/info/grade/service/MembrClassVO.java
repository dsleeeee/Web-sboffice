package kr.co.solbipos.membr.info.grade.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

/**
 * @author 솔비포스 차세대개발실 김지은
 * @version 1.0
 * @Class Name : MembrClassVO.java
 * @Description :
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.18  김지은      최초생성
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @since 2018. 09.18
 */
public class MembrClassVO extends CmmVO {


    /**
     * [회원소속구분]
     */
    private OrgnFg membrOrgnFg;
    /**
     * [회원소속코드]
     */
    private String membrOrgnCd;
    /**
     * [회원분류코드]
     */
    private String membrClassCd;
    /**
     * [회원소속분류코드]
     */
    private String membrOrgnClassCd;
    /**
     * [회원분류명]
     */
    private String membrClassNm;
    /**
     * [회원분류]
     */
    private String membrClassType;
    /**
     * [할인율]
     */
    private Integer dcRate;
    /**
     * [포인트적립구분] TB_CM_NMCODE(NMCODE_GRP_CD='054')
     */
    private String pointSaveFg;
    private String prePointSaveFg;
    /**
     * [기본여부] Y:기본 N:기본아님
     */
    private String defltYn;
    /**
     * [신규가입적립포인트]
     */
    private Integer newJoinSavePoint;
    /**
     * [첫거래적립포인트]
     */
    private Integer firstSaleSavePoint;
    /**
     * [최소사용포인트]
     */
    private Integer minUsePoint;
    /**
     * [기념일포인트적립구분] TB_CM_NMCODE(NMCODE_GRP_CD='032')
     */
    private String anvsrPointSaveFg;
    /**
     * [기념일적립포인트]
     */
    private Integer anvsrSavePoint;
    /**
     * [사용여부] Y:사용 N:사용안함
     */
    private String useYn;
    /**
     * [사용여부] Y:사용 N:사용안함
     */
    private String dcAccPointYn;
    /**
     * [사용여부] Y:사용 N:사용안함
     */
    private String useAccPointYn;
    /**
     * [사용여부] Y:사용 N:사용안함
     */
    private Integer maxUsePoint;
    /**
     * [사용여부] Y:사용 N:사용안함
     */
    private Integer dcLimitAmt;
    /**
     * [포인트적립구분한글]
     */
    private String pointSaveFgNm;



    /**
     * [기념일적립구분한글]
     */
    private String anvsrPointSaveFgNm;

    /**
     * [회원분류 관리구분]
     */
    private String membrClassManageFg;

    /**
     * [본사코드]
     */
    private String hqOfficeCd;

    /**
     * [매장코드]
     */
    private String storeCd;

    /** 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점 */
    private String orgnFg;

    /**
     * @return the membrOrgnFg
     */

    public OrgnFg getMembrOrgnFg() {
        return membrOrgnFg;
    }

    /**
     * @param membrOrgnFg the membrOrgnFg to set
     */
    public void setMembrOrgnFg(OrgnFg membrOrgnFg) {
        this.membrOrgnFg = membrOrgnFg;
    }

    /**
     * @return the membrOrgnCd
     */

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    /**
     * @param membrOrgnCd the membrOrgnCd to set
     */
    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    /**
     * @return the membrClassCd
     */

    public String getMembrClassCd() {
        return membrClassCd;
    }

    /**
     * @param membrClassCd the membrClassCd to set
     */
    public void setMembrClassCd(String membrClassCd) {
        this.membrClassCd = membrClassCd;
    }

    public String getMembrOrgnClassCd() {
        return membrOrgnClassCd;
    }

    public void setMembrOrgnClassCd(String membrOrgnClassCd) {
        this.membrOrgnClassCd = membrOrgnClassCd;
    }

    public String getMembrClassType() {
        return membrClassType;
    }

    /**
     * @return the membrClassNm
     */

    public String getMembrClassNm() {
        return membrClassNm;
    }

    /**
     * @param membrClassNm the membrClassNm to set
     */
    public void setMembrClassNm(String membrClassNm) {
        this.membrClassNm = membrClassNm;
    }

    /**
     * @return the membrClassType
     */
    public String getMembrClassType(String membrClassType) {
        return membrClassType;
    }

    /**
     * @param membrClassType the membrClassType to set
     */
    public void setMembrClassType(String membrClassType) {
        this.membrClassType = membrClassType;
    }

    /**
     * @return the dcRate
     */

    public Integer getDcRate() {
        return dcRate;
    }

    /**
     * @param dcRate the dcRate to set
     */
    public void setDcRate(Integer dcRate) {
        this.dcRate = dcRate;
    }

    /**
     * @return the pointSaveFg
     */

    public String getPointSaveFg() {
        return pointSaveFg;
    }

    /**
     * @param pointSaveFg the pointSaveFg to set
     */
    public void setPointSaveFg(String pointSaveFg) {
        this.pointSaveFg = pointSaveFg;
    }

    public String getPrePointSaveFg() {
        return prePointSaveFg;
    }

    public void setPrePointSaveFg(String prePointSaveFg) {
        this.prePointSaveFg = prePointSaveFg;
    }

    /**
     * @return the defltYn
     */

    public String getDefltYn() {
        return defltYn;
    }

    /**
     * @param defltYn the defltYn to set
     */
    public void setDefltYn(String defltYn) {
        this.defltYn = defltYn;
    }

    /**
     * @return the newJoinSavePoint
     */

    public Integer getNewJoinSavePoint() {
        return newJoinSavePoint;
    }

    /**
     * @param newJoinSavePoint the newJoinSavePoint to set
     */
    public void setNewJoinSavePoint(Integer newJoinSavePoint) {
        this.newJoinSavePoint = newJoinSavePoint;
    }

    /**
     * @return the firstSaleSavePoint
     */

    public Integer getFirstSaleSavePoint() {
        return firstSaleSavePoint;
    }

    /**
     * @param firstSaleSavePoint the firstSaleSavePoint to set
     */
    public void setFirstSaleSavePoint(Integer firstSaleSavePoint) {
        this.firstSaleSavePoint = firstSaleSavePoint;
    }

    /**
     * @return the minUsePoint
     */

    public Integer getMinUsePoint() {
        return minUsePoint;
    }

    /**
     * @param minUsePoint the minUsePoint to set
     */
    public void setMinUsePoint(Integer minUsePoint) {
        this.minUsePoint = minUsePoint;
    }

    /**
     * @return the anvsrPointSaveFg
     */

    public String getAnvsrPointSaveFg() {
        return anvsrPointSaveFg;
    }

    /**
     * @param anvsrPointSaveFg the anvsrPointSaveFg to set
     */
    public void setAnvsrPointSaveFg(String anvsrPointSaveFg) {
        this.anvsrPointSaveFg = anvsrPointSaveFg;
    }

    /**
     * @return the anvsrSavePoint
     */

    public Integer getAnvsrSavePoint() {
        return anvsrSavePoint;
    }

    /**
     * @param anvsrSavePoint the anvsrSavePoint to set
     */
    public void setAnvsrSavePoint(Integer anvsrSavePoint) {
        this.anvsrSavePoint = anvsrSavePoint;
    }

    /**
     * @return the useYn
     */

    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }


    /**
     * @return the dcAccPointYn
     */
    public String getDcAccPointYn() {
        return dcAccPointYn;
    }

    /**
     * @param dcAccPointYn the useYn to set
     */
    public void setDcAccPointYn(String dcAccPointYn) {
        this.dcAccPointYn = dcAccPointYn;
    }
    /**
     * @return the useAccPointYn
     */
    public String getUseAccPointYn() {
        return useAccPointYn;
    }
    /**
     * @param useAccPointYn the useYn to set
     */
    public void setUseAccPointYn(String useAccPointYn) {
        this.useAccPointYn = useAccPointYn;
    }
    /**
     * @return the maxUsePoint
     */
    public Integer getMaxUsePoint() {
        return maxUsePoint;
    }
    /**
     * @param maxUsePoint the useYn to set
     */
    public void setMaxUsePoint(Integer maxUsePoint) {
        this.maxUsePoint = maxUsePoint;
    }
    /**
     * @return the dcLimitAmt
     */
    public Integer getDcLimitAmt() {
        return dcLimitAmt;
    }
    /**
     * @param dcLimitAmt the useYn to set
     */
    public void setDcLimitAmt(Integer dcLimitAmt) {
        this.dcLimitAmt = dcLimitAmt;
    }
    /**
     * @return the pointSaveFgNm
     */
    public String getPointSaveFgNm() {
        return pointSaveFgNm;
    }
    /**
     * @param pointSaveFgNm the useYn to set
     */
    public void setPointSaveFgNm(String pointSaveFgNm) {
        this.pointSaveFgNm = pointSaveFgNm;
    }
    /**
     * @return the anvsrPointSaveFgNm
     */
    public String getAnvsrPointSaveFgNm() {
        return anvsrPointSaveFgNm;
    }
    /**
     * @param anvsrPointSaveFgNm the useYn to set
     */
    public void setAnvsrPointSaveFgNm(String anvsrPointSaveFgNm) {
        this.anvsrPointSaveFgNm = anvsrPointSaveFgNm;
    }

    public String getMembrClassManageFg() {
        return membrClassManageFg;
    }

    public void setMembrClassManageFg(String membrClassManageFg) {
        this.membrClassManageFg = membrClassManageFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }
}
