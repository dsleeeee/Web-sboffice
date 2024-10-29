package kr.co.solbipos.sys.cd.verEnvMng.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : VerEnvMngVO.java
 * @Description : 시스템관리 > 코드관리 > 버전별환경설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.23  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.10.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VerEnvMngVO extends CmmVO {

    private static final long serialVersionUID = 1468282261135978249L;

    /** 버전코드 */
    private String vsCd;

    /** 버전명 */
    private String vsNm;

    /** 사용여부 */
    private String useYn;

    /** 환경설정코드 */
    private String envstCd;

    /** 환경설정값코드 */
    private String envstValCd;

    /** 초기값여부 */
    private String defltYn;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 기능키구분 */
    private String fnkeyFg;

    /** 기능키번호 */
    private String fnkeyNo;

    public String getVsCd() {
        return vsCd;
    }

    public void setVsCd(String vsCd) {
        this.vsCd = vsCd;
    }

    public String getVsNm() {
        return vsNm;
    }

    public void setVsNm(String vsNm) {
        this.vsNm = vsNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getEnvstCd() {
        return envstCd;
    }

    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }

    public String getEnvstValCd() {
        return envstValCd;
    }

    public void setEnvstValCd(String envstValCd) {
        this.envstValCd = envstValCd;
    }

    public String getDefltYn() {
        return defltYn;
    }

    public void setDefltYn(String defltYn) {
        this.defltYn = defltYn;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getFnkeyFg() {
        return fnkeyFg;
    }

    public void setFnkeyFg(String fnkeyFg) {
        this.fnkeyFg = fnkeyFg;
    }

    public String getFnkeyNo() {
        return fnkeyNo;
    }

    public void setFnkeyNo(String fnkeyNo) {
        this.fnkeyNo = fnkeyNo;
    }
}
