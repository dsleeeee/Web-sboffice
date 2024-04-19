package kr.co.solbipos.pos.license.vanFixExceptStore.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : VanFixExceptStoreVO.java
 * @Description : 포스관리 > 라이선스 관리 > VAN사 변경허용 매장관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.09  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VanFixExceptStoreVO extends PageVO {

    private static final long serialVersionUID = 4669093696213872268L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 관리업체 코드 */
    private String agencyCd;
    /** 밴사코드 */
    private String vanCd;
    /** 하위대리점 포함여부 */
    private String includeFg;
    /** 비고  */
    private String remark;
    /** 등록시간 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 수정시간 */
    private String modDt;
    /** 수정아이디 */
    private String modId;


    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
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

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getVanCd() {
        return vanCd;
    }

    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }

    public String getIncludeFg() {
        return includeFg;
    }

    public void setIncludeFg(String includeFg) {
        this.includeFg = includeFg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getRegDt() {
        return regDt;
    }

    @Override
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    @Override
    public String getRegId() {
        return regId;
    }

    @Override
    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public String getModDt() {
        return modDt;
    }

    @Override
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void setModId(String modId) {
        this.modId = modId;
    }
}
