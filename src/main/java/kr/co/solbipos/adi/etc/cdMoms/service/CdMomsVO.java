package kr.co.solbipos.adi.etc.cdMoms.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : CdMomsVO.java
 * @Description : 맘스터치 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CdMomsVO extends CmmVO {

    private static final long serialVersionUID = -1046090570078823455L;

    /** 소속구분 */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 가맹점코드 */
    private String storeCd;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 명칭코드코드 */
    private String nmcodeCd;

    /** 명칭코드명 */
    private String nmcodeNm;

    /** 명칭코드항목1 */
    private String nmcodeItem1;

    /** 명칭코드항목2 */
    private String nmcodeItem2;

    /** 사용여부 */
    private String useYn;

    /** 세부명칭갯수 */
    private String cnt;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
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

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getNmcodeCd() {
        return nmcodeCd;
    }

    public void setNmcodeCd(String nmcodeCd) {
        this.nmcodeCd = nmcodeCd;
    }

    public String getNmcodeNm() {
        return nmcodeNm;
    }

    public void setNmcodeNm(String nmcodeNm) {
        this.nmcodeNm = nmcodeNm;
    }

    public String getNmcodeItem1() {
        return nmcodeItem1;
    }

    public void setNmcodeItem1(String nmcodeItem1) {
        this.nmcodeItem1 = nmcodeItem1;
    }

    public String getNmcodeItem2() {
        return nmcodeItem2;
    }

    public void setNmcodeItem2(String nmcodeItem2) {
        this.nmcodeItem2 = nmcodeItem2;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}