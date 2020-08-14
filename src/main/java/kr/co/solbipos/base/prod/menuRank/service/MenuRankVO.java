package kr.co.solbipos.base.prod.menuRank.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : menuRankVO.java
 * @Description : 기초관리 - 상품관리 - 메뉴 순위 표시 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.06  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class MenuRankVO extends PageVO {

    private static final long serialVersionUID = -5242390616938360019L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 명칭코드코드 */
    private String nmcodeCd;
    /** 명칭코드명 */
    private String nmcodeNm;
    /** 입력구분 */
    private String insFg;
    /** 사용여부 */
    private UseYn useYn;
    /** 상품적용여부 */
    private UseYn storeRegFg;

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

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
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

    public String getInsFg() {
        return insFg;
    }

    public void setInsFg(String insFg) {
        this.insFg = insFg;
    }

    public UseYn getUseYn() {
        return useYn;
    }

    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }

    public UseYn getStoreRegFg() {
        return storeRegFg;
    }

    public void setStoreRegFg(UseYn storeRegFg) {
        this.storeRegFg = storeRegFg;
    }
}
