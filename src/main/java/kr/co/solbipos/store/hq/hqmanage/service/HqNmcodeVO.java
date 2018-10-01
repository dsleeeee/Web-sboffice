package kr.co.solbipos.store.hq.hqmanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

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
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class HqNmcodeVO extends CmmVO {

    private static final long serialVersionUID = 8427290281086426173L;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;
    /** 명칭코드코드 */
    private String nmcodeCd;
    /** 명칭코드명 */
    private String nmcodeNm;
    /** 명칭코드항목_1 */
    private String nmcodeItem1;
    /** 명칭코드항목_2 */
    private String nmcodeItem2;
    /** 사용여부 (사용:Y 사용안함:N) */
    private UseYn useYn;
    /** 프로시져 실행 결과 */
    private String result;


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
     * @return the nmcodeGrpCd
     */

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    /**
     * @param nmcodeGrpCd the nmcodeGrpCd to set
     */
    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    /**
     * @return the nmcodeCd
     */

    public String getNmcodeCd() {
        return nmcodeCd;
    }

    /**
     * @param nmcodeCd the nmcodeCd to set
     */
    public void setNmcodeCd(String nmcodeCd) {
        this.nmcodeCd = nmcodeCd;
    }

    /**
     * @return the nmcodeNm
     */

    public String getNmcodeNm() {
        return nmcodeNm;
    }

    /**
     * @param nmcodeNm the nmcodeNm to set
     */
    public void setNmcodeNm(String nmcodeNm) {
        this.nmcodeNm = nmcodeNm;
    }

    /**
     * @return the nmcodeItem1
     */

    public String getNmcodeItem1() {
        return nmcodeItem1;
    }

    /**
     * @param nmcodeItem1 the nmcodeItem1 to set
     */
    public void setNmcodeItem1(String nmcodeItem1) {
        this.nmcodeItem1 = nmcodeItem1;
    }

    /**
     * @return the nmcodeItem2
     */

    public String getNmcodeItem2() {
        return nmcodeItem2;
    }

    /**
     * @param nmcodeItem2 the nmcodeItem2 to set
     */
    public void setNmcodeItem2(String nmcodeItem2) {
        this.nmcodeItem2 = nmcodeItem2;
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
     * @return the result
     */

    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
}
