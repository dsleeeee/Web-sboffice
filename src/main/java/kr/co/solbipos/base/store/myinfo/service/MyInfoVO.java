package kr.co.solbipos.base.store.myinfo.service;

/**
 * @Class Name : MyInfoVO.java
 * @Description : 기초관리 > 매장관리 > 내정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.27  이호원      최초생성
 *
 * @author NHN한국사이버결제 이호원
 * @since 2018.07.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MyInfoVO{
    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사사업장명 */
    private String hqOfficeNm;
    /** 대표자명 */
    private String ownerNm;
    /** 사업자번호 */
    private String bizNo;
    /** 사업자유형코드 */
    private String bizTypeCd;
    /** 사업자항목코드 */
    private String bizItemCd;
    /** 사업자매장명 */
    private String bizStoreNm;
    /** 전화번호 */
    private String telNo;
    /** 팩스번호 */
    private String faxNo;
    /** 이메일주소 */
    private String emailAddr;
    /** 홈페이지주소 */
    private String hmpgAddr;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addr;
    /** 주소상세 */
    private String addrDtl;
    /** 지역코드 */
    private String areaCd;
    /** 시스템상태구분 */
    private String sysStatFg;
    /** 시스템오픈일자 */
    private String sysOpenDate;
    /** 시스템폐점일자 */
    private String sysClosureDate;
    /** 대리점코드 */
    private String agencyCd;
    /** 비고 */
    private String remark;
    /** 등록일시 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;

    private String clsFg;

    public String getHqOfficeCd(){
        return hqOfficeCd;
    }

    public void setHqOfficeCd( String hqOfficeCd ){
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm(){
        return hqOfficeNm;
    }

    public void setHqOfficeNm( String hqOfficeNm ){
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getOwnerNm(){
        return ownerNm;
    }

    public void setOwnerNm( String ownerNm ){
        this.ownerNm = ownerNm;
    }

    public String getBizNo(){
        return bizNo;
    }

    public void setBizNo( String bizNo ){
        this.bizNo = bizNo;
    }

    public String getBizTypeCd(){
        return bizTypeCd;
    }

    public void setBizTypeCd( String bizTypeCd ){
        this.bizTypeCd = bizTypeCd;
    }

    public String getBizItemCd(){
        return bizItemCd;
    }

    public void setBizItemCd( String bizItemCd ){
        this.bizItemCd = bizItemCd;
    }

    public String getBizStoreNm(){
        return bizStoreNm;
    }

    public void setBizStoreNm( String bizStoreNm ){
        this.bizStoreNm = bizStoreNm;
    }

    public String getTelNo(){
        return telNo;
    }

    public void setTelNo( String telNo ){
        this.telNo = telNo;
    }

    public String getFaxNo(){
        return faxNo;
    }

    public void setFaxNo( String faxNo ){
        this.faxNo = faxNo;
    }

    public String getEmailAddr(){
        return emailAddr;
    }

    public void setEmailAddr( String emailAddr ){
        this.emailAddr = emailAddr;
    }

    public String getHmpgAddr(){
        return hmpgAddr;
    }

    public void setHmpgAddr( String hmpgAddr ){
        this.hmpgAddr = hmpgAddr;
    }

    public String getPostNo(){
        return postNo;
    }

    public void setPostNo( String postNo ){
        this.postNo = postNo;
    }

    public String getAddr(){
        return addr;
    }

    public void setAddr( String addr ){
        this.addr = addr;
    }

    public String getAddrDtl(){
        return addrDtl;
    }

    public void setAddrDtl( String addrDtl ){
        this.addrDtl = addrDtl;
    }

    public String getAreaCd(){
        return areaCd;
    }

    public void setAreaCd( String areaCd ){
        this.areaCd = areaCd;
    }

    public String getSysStatFg(){
        return sysStatFg;
    }

    public void setSysStatFg( String sysStatFg ){
        this.sysStatFg = sysStatFg;
    }

    public String getSysOpenDate(){
        return sysOpenDate;
    }

    public void setSysOpenDate( String sysOpenDate ){
        this.sysOpenDate = sysOpenDate;
    }

    public String getSysClosureDate(){
        return sysClosureDate;
    }

    public void setSysClosureDate( String sysClosureDate ){
        this.sysClosureDate = sysClosureDate;
    }

    public String getAgencyCd(){
        return agencyCd;
    }

    public void setAgencyCd( String agencyCd ){
        this.agencyCd = agencyCd;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark( String remark ){
        this.remark = remark;
    }

    public String getRegDt(){
        return regDt;
    }

    public void setRegDt( String regDt ){
        this.regDt = regDt;
    }

    public String getRegId(){
        return regId;
    }

    public void setRegId( String regId ){
        this.regId = regId;
    }

    public String getModDt(){
        return modDt;
    }

    public void setModDt( String modDt ){
        this.modDt = modDt;
    }

    public String getModId(){
        return modId;
    }

    public void setModId( String modId ){
        this.modId = modId;
    }

    public String getClsFg(){
        return clsFg;
    }

    public void setClsFg( String clsFg ){
        this.clsFg = clsFg;
    }
}