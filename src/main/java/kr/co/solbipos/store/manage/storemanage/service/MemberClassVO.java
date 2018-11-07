package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : MemberClassVO.java
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.30  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.30
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.t
 */
public class MemberClassVO extends CmmVO {

    /**  [회원소속코드] */
    private String membrOrgnCd;
    /**  [회원분류코드] */
    private String membrClassCd;
    /**  [회원분류명] */
    private String membrClassNm;
    /**  [할인율] */
    private double dcRate;
    /**  [포인트적립구분] */
    private String pointSaveFg;
    /**  [기본여부] */
    private String defltYn;
    /**  [신규가입적립포인트] */
    private int newJoinSavePoint;
    /**  [첫거래적립포인트] */
    private int firstSaleSavePoint;
    /**  [최소사용포인트] */
    private int minUsePoint;
    /**  [기념일포인트적립구분] */
    private String anvsrPointSaveFg;
    /**  [기념일적립포인트] */
    private int anvsrSavePoint;
    /**  [사용여부] */
    private UseYn useYn;

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
     * @return the dcRate
     */

    public double getDcRate() {
        return dcRate;
    }

    /**
     * @param dcRate the dcRate to set
     */
    public void setDcRate(double dcRate) {
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

    public int getNewJoinSavePoint() {
        return newJoinSavePoint;
    }

    /**
     * @param newJoinSavePoint the newJoinSavePoint to set
     */
    public void setNewJoinSavePoint(int newJoinSavePoint) {
        this.newJoinSavePoint = newJoinSavePoint;
    }

    /**
     * @return the firstSaleSavePoint
     */

    public int getFirstSaleSavePoint() {
        return firstSaleSavePoint;
    }

    /**
     * @param firstSaleSavePoint the firstSaleSavePoint to set
     */
    public void setFirstSaleSavePoint(int firstSaleSavePoint) {
        this.firstSaleSavePoint = firstSaleSavePoint;
    }

    /**
     * @return the minUsePoint
     */

    public int getMinUsePoint() {
        return minUsePoint;
    }

    /**
     * @param minUsePoint the minUsePoint to set
     */
    public void setMinUsePoint(int minUsePoint) {
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

    public int getAnvsrSavePoint() {
        return anvsrSavePoint;
    }

    /**
     * @param anvsrSavePoint the anvsrSavePoint to set
     */
    public void setAnvsrSavePoint(int anvsrSavePoint) {
        this.anvsrSavePoint = anvsrSavePoint;
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
