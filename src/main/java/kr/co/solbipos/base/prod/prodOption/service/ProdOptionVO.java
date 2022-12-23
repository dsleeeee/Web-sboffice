package kr.co.solbipos.base.prod.prodOption.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;

/**
 * @Class Name : ProdOptionVO.java
 * @Description : 기초관리 > 상품관리 > 옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.19  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdOptionVO extends PageVO {

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 옵션그룹코드 */
    private String optionGrpCd;

    /** 옵션그룹명 */
    private String optionGrpNm;

    /** 사용여부 */
    private String useYn;

    /** 그룹별옵션코드 */
    private String optionValCd;

    /** 그룹별옵션명 */
    private String optionValNm;

    /** 등록구분 */
    private String regFg;

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

    public String getOptionGrpCd() {
        return optionGrpCd;
    }

    public void setOptionGrpCd(String optionGrpCd) {
        this.optionGrpCd = optionGrpCd;
    }

    public String getOptionGrpNm() {
        return optionGrpNm;
    }

    public void setOptionGrpNm(String optionGrpNm) {
        this.optionGrpNm = optionGrpNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOptionValCd() {
        return optionValCd;
    }

    public void setOptionValCd(String optionValCd) {
        this.optionValCd = optionValCd;
    }

    public String getOptionValNm() {
        return optionValNm;
    }

    public void setOptionValNm(String optionValNm) {
        this.optionValNm = optionValNm;
    }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }
}