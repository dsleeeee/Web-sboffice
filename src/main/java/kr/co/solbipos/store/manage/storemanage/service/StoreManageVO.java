package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;

/**
 * @Class Name : StoreManageVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리
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
public class StoreManageVO extends PageVO {

    private static final long serialVersionUID = 74163511582334938L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 대표자명 */
    private String ownerNm;
    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사사업장명 */
    private String hqOfficeNm;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 사업자번호 */
    private String bizNo;
    /** 사업자번호 (처음3자리) */
    private String bizNo1;
    /** 사업자번호 (중간2자리)*/
    private String bizNo2;
    /** 사업자번호 (끝5자리)*/
    private String bizNo3;
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
    /** 핸드폰번호 */
    private String mpNo;
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
    /** 용도구분  */
    private String clsFg;
    /** 시스템상태구분 */
    private SysStatFg sysStatFg;
    /** 시스템오픈일자 */
    private String sysOpenDate;
    /** 시스템폐점일자 */
    private String sysClosureDate;
    /** 밴사코드 */
    private String vanCd;
    /** 대리점코드 */
    private String agencyCd;
    /** 시스템 비고 */
    private String sysRemark;
    /** 본사 비고 */
    private String hdRemark;
    /** 비고 */
    private String remark;
    /** 설치포스수  */
    private String installPosCnt;
    /** 매장환경 복사 대상이 되는 본사  */
    private String copyHqOfficeCd;
    /** 매장환경 복사 대상이 되는 매장 */
    private String copyStoreCd;
    /** 터치키 입력 구분 (H:본사, S:매장) - 매장 등록시 사용 */
    private String inFg;
    /** 복사할 환경값 */
    private String copyChkVal;
    /** 포스번호 */
    private int posNo;
    /** 웹 사용자 아이디 */
    private String userId;
    /** 웹 사용자 패스워드 */
    private String userPwd;
    /** 포스 사원번호 */
    private String posEmpNo;
    /** 포스 사용자 패스워드 */
    private String posUserPwd;
    /** 코너 사용여부 */
    private String cornerUseYn;

    /** 여러 포스값 */
    private String[] arrPosNo;

    /** 매장코드 채번방식 */
    private String storeCdInputType;

    /** 직영구분 */
    private String directManageYn;

    /** 권한 그룹 코드 */
    private String authGrpCd;

    /** 조회용 매장코드 */
    private String srchStoreCd;

    /** 조회용 매장명 */
    private String srchStoreNm;

    /** 매핑매장코드*/
    private String mapStoreCd;

    /** 본사신규상품매장생성 */
    private String envst0043;

    /** 매장코드8이상 사용매장여부(값이 있으면 사용, 없으면 미사용)*/
    private String digit8Store;

    /** ERP 연동 매장 등록여부 */
    private String regYn;

    /** BBQ 매장코드 */
    private String bbqStoreCd;

    /** ERP를 연동 본사 여부  */
    private String erpLinkHq;

    /** 환경설정코드 */
    private String envstCd;

    /** 환경설정값 */
    private String envstVal;

    /** 사원번호 */
    private String empNo;

    /** 프로시져 실행 결과 */
    private String result;

    /** 위도 */
    private String latitude;

    /** 경도 */
    private String longitude;

    /** 그룹코드 */
    private String branchCd;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 팀별 */
    private String momsTeam;

    /** AC점포별 */
    private String momsAcShop;

    /** 지역구분 */
    private String momsAreaFg;

    /** 상권 */
    private String momsCommercial;

    /** 점포유형 */
    private String momsShopType;

    /** 매장관리타입 */
    private String momsStoreManageType;

    /** 환경설정 [1250 맘스터치] */
    private String momsEnvstVal;

    /** 사이트코드 */
    private String siteCd;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** [벤더구분] */
    private String vendorFg;
    /** [벤더구분명] */
    private String vendorFgNm;
    /** [벤더코드] */
    private String vendorCd;
    /** [벤더명] */
    private String vendorNm;
    /** [벤더 터미널번호] */
    private String vendorTermnlNo;
    /** [벤더시리얼번호] */
    private String vendorSerNo;

    /** 매장그룹 */
    private String momsStoreFg01;

    /** 매장그룹2 */
    private String momsStoreFg02;

    /** 매장그룹3 */
    private String momsStoreFg03;

    /** 매장그룹4 */
    private String momsStoreFg04;

    /** 매장그룹5 */
    private String momsStoreFg05;

    /** 언어구분 */
    private String langFg;
    /** 파일URL */
    private String adverUrl;

    /** 대표 VAN*/
    private String baseVanYn;

    /** 포스번호 */
    private String strPosNo;

    /** 이전에 선택했던 밴더코드 */
    private String bVendorCd;

    /** 코너코드*/
    private String cornrCd;

    /** 시스템명칭관리 [308 권한허용본사] */
    private String nmcodeAuthHqCnt;

    /** 본사코드 */
    private String selectHqOfficeCd;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }

    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }
    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
    /**
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }
    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }
    /**
     * @return the ownerNm
     */
    public String getOwnerNm() {
        return ownerNm;
    }
    /**
     * @param ownerNm the ownerNm to set
     */
    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }
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
     * @return the hqOfficeNm
     */
    public String getHqOfficeNm() {
        return hqOfficeNm;
    }
    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }
    /**
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }
    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }
    /**
     * @return the bizNo
     */
    public String getBizNo() {
        return bizNo;
    }
    /**
     * @param bizNo the bizNo to set
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    /**
     * @return the bizNo1
     */

    public String getBizNo1() {
        return bizNo1;
    }

    /**
     * @param bizNo1 the bizNo1 to set
     */
    public void setBizNo1(String bizNo1) {
        this.bizNo1 = bizNo1;
    }

    /**
     * @return the bizNo2
     */

    public String getBizNo2() {
        return bizNo2;
    }

    /**
     * @param bizNo2 the bizNo2 to set
     */
    public void setBizNo2(String bizNo2) {
        this.bizNo2 = bizNo2;
    }

    /**
     * @return the bizNo3
     */

    public String getBizNo3() {
        return bizNo3;
    }

    /**
     * @param bizNo3 the bizNo3 to set
     */
    public void setBizNo3(String bizNo3) {
        this.bizNo3 = bizNo3;
    }

    /**
     * @return the bizTypeCd
     */
    public String getBizTypeCd() {
        return bizTypeCd;
    }
    /**
     * @param bizTypeCd the bizTypeCd to set
     */
    public void setBizTypeCd(String bizTypeCd) {
        this.bizTypeCd = bizTypeCd;
    }
    /**
     * @return the bizItemCd
     */
    public String getBizItemCd() {
        return bizItemCd;
    }
    /**
     * @param bizItemCd the bizItemCd to set
     */
    public void setBizItemCd(String bizItemCd) {
        this.bizItemCd = bizItemCd;
    }
    /**
     * @return the bizStoreNm
     */
    public String getBizStoreNm() {
        return bizStoreNm;
    }
    /**
     * @param bizStoreNm the bizStoreNm to set
     */
    public void setBizStoreNm(String bizStoreNm) {
        this.bizStoreNm = bizStoreNm;
    }
    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }
    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    /**
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }
    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    /**
     * @return the emailAddr
     */
    public String getEmailAddr() {
        return emailAddr;
    }
    /**
     * @param emailAddr the emailAddr to set
     */
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }
    /**
     * @return the hmpgAddr
     */
    public String getHmpgAddr() {
        return hmpgAddr;
    }
    /**
     * @param hmpgAddr the hmpgAddr to set
     */
    public void setHmpgAddr(String hmpgAddr) {
        this.hmpgAddr = hmpgAddr;
    }
    /**
     * @return the postNo
     */
    public String getPostNo() {
        return postNo;
    }
    /**
     * @param postNo the postNo to set
     */
    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }
    /**
     * @return the addr
     */
    public String getAddr() {
        return addr;
    }
    /**
     * @param addr the addr to set
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }
    /**
     * @return the addrDtl
     */
    public String getAddrDtl() {
        return addrDtl;
    }
    /**
     * @param addrDtl the addrDtl to set
     */
    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }
    /**
     * @return the areaCd
     */
    public String getAreaCd() {
        return areaCd;
    }
    /**
     * @param areaCd the areaCd to set
     */
    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }
    /**
     * @return the clsFg
     */
    public String getClsFg() {
        return clsFg;
    }
    /**
     * @param clsFg the clsFg to set
     */
    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }
    /**
     * @return the sysStatFg
     */
    public SysStatFg getSysStatFg() {
        return sysStatFg;
    }
    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(SysStatFg sysStatFg) {
        this.sysStatFg = sysStatFg;
    }
    /**
     * @return the sysOpenDate
     */
    public String getSysOpenDate() {
        return sysOpenDate;
    }
    /**
     * @param sysOpenDate the sysOpenDate to set
     */
    public void setSysOpenDate(String sysOpenDate) {
        this.sysOpenDate = sysOpenDate;
    }
    /**
     * @return the sysClosureDate
     */
    public String getSysClosureDate() {
        return sysClosureDate;
    }
    /**
     * @param sysClosureDate the sysClosureDate to set
     */
    public void setSysClosureDate(String sysClosureDate) {
        this.sysClosureDate = sysClosureDate;
    }
    /**
     * @return the vanCd
     */
    public String getVanCd() {
        return vanCd;
    }
    /**
     * @param vanCd the vanCd to set
     */
    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }
    /**
     * @return the agencyCd
     */
    public String getAgencyCd() {
        return agencyCd;
    }
    /**
     * @param agencyCd the agencyCd to set
     */
    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }
    /**
     * @return the sysRemark
     */
    public String getSysRemark() {
        return sysRemark;
    }
    /**
     * @param sysRemark the sysRemark to set
     */
    public void setSysRemark(String sysRemark) {
        this.sysRemark = sysRemark;
    }
    /**
     * @return the hdRemark
     */
    public String getHdRemark() {
        return hdRemark;
    }
    /**
     * @param hdRemark the hdRemark to set
     */
    public void setHdRemark(String hdRemark) {
        this.hdRemark = hdRemark;
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
     * @return the installPosCnt
     */
    public String getInstallPosCnt() {
        return installPosCnt;
    }
    /**
     * @param installPosCnt the installPosCnt to set
     */
    public void setInstallPosCnt(String installPosCnt) {
        this.installPosCnt = installPosCnt;
    }
    /**
     * @return the copyHqOfficeCd
     */
    public String getCopyHqOfficeCd() {
        return copyHqOfficeCd;
    }
    /**
     * @param copyHqOfficeCd the copyHqOfficeCd to set
     */
    public void setCopyHqOfficeCd(String copyHqOfficeCd) {
        this.copyHqOfficeCd = copyHqOfficeCd;
    }
    /**
     * @return the copyStoreCd
     */
    public String getCopyStoreCd() {
        return copyStoreCd;
    }
    /**
     * @param copyStoreCd the copyStoreCd to set
     */
    public void setCopyStoreCd(String copyStoreCd) {
        this.copyStoreCd = copyStoreCd;
    }
    /**
     * @return the inFg
     */
    public String getInFg() {
        return inFg;
    }
    /**
     * @param inFg the inFg to set
     */
    public void setInFg(String inFg) {
        this.inFg = inFg;
    }
    /**
     * @return the copyChkVal
     */
    public String getCopyChkVal() {
        return copyChkVal;
    }
    /**
     * @param copyChkVal the copyChkVal to set
     */
    public void setCopyChkVal(String copyChkVal) {
        this.copyChkVal = copyChkVal;
    }
    /**
     * @return the posNo
     */
    public int getPosNo() {
        return posNo;
    }
    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(int posNo) {
        this.posNo = posNo;
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

    /**
     * @return the userPwd
     */

    public String getUserPwd() {
        return userPwd;
    }

    /**
     * @param userPwd the userPwd to set
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * @return the posEmpNo
     */

    public String getPosEmpNo() {
        return posEmpNo;
    }

    /**
     * @param posEmpNo the posEmpNo to set
     */
    public void setPosEmpNo(String posEmpNo) {
        this.posEmpNo = posEmpNo;
    }

    /**
     * @return the posUserPwd
     */

    public String getPosUserPwd() {
        return posUserPwd;
    }

    /**
     * @param posUserPwd the posUserPwd to set
     */
    public void setPosUserPwd(String posUserPwd) {
        this.posUserPwd = posUserPwd;
    }

    /**
     * @return the cornerUseYn
     */

    public String getCornerUseYn() {
        return cornerUseYn;
    }

    /**
     * @param cornerUseYn the cornerUseYn to set
     */
    public void setCornerUseYn(String cornerUseYn) {
        this.cornerUseYn = cornerUseYn;
    }

    /**
     * @return the arrPosNo
     */

    public String[] getArrPosNo() {
        return arrPosNo;
    }

    /**
     * @param arrPosNo the arrPosNo to set
     */
    public void setArrPosNo(String[] arrPosNo) {
        this.arrPosNo = arrPosNo;
    }

    public String getStoreCdInputType() {
        return storeCdInputType;
    }

    public void setStoreCdInputType(String storeCdInputType) {
        this.storeCdInputType = storeCdInputType;
    }

    public String getDirectManageYn() {
        return directManageYn;
    }

    public void setDirectManageYn(String directManageYn) {
        this.directManageYn = directManageYn;
    }

    public String getAuthGrpCd() { return authGrpCd; }

    public void setAuthGrpCd(String authGrpCd) { this.authGrpCd = authGrpCd; }

    public String getSrchStoreCd() {
        return srchStoreCd;
    }

    public void setSrchStoreCd(String srchStoreCd) {
        this.srchStoreCd = srchStoreCd;
    }

    public String getSrchStoreNm() {
        return srchStoreNm;
    }

    public void setSrchStoreNm(String srchStoreNm) {
        this.srchStoreNm = srchStoreNm;
    }

    public String getMapStoreCd() {
        return mapStoreCd;
    }

    public void setMapStoreCd(String mapStoreCd) {
        this.mapStoreCd = mapStoreCd;
    }

    public String getEnvst0043() {
        return envst0043;
    }

    public void setEnvst0043(String envst0043) {
        this.envst0043 = envst0043;
    }

    public String getDigit8Store() {
        return digit8Store;
    }

    public void setDigit8Store(String digit8Store) {
        this.digit8Store = digit8Store;
    }

    public String getRegYn() {
        return regYn;
    }

    public void setRegYn(String regYn) {
        this.regYn = regYn;
    }

    public String getBbqStoreCd() {
        return bbqStoreCd;
    }

    public void setBbqStoreCd(String bbqStoreCd) {
        this.bbqStoreCd = bbqStoreCd;
    }

    public String getErpLinkHq() {
        return erpLinkHq;
    }

    public void setErpLinkHq(String erpLinkHq) {
        this.erpLinkHq = erpLinkHq;
    }

    public String getEnvstCd() {
        return envstCd;
    }

    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }

    public String getEnvstVal() {
        return envstVal;
    }

    public void setEnvstVal(String envstVal) {
        this.envstVal = envstVal;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getMomsTeam() {
        return momsTeam;
    }

    public void setMomsTeam(String momsTeam) {
        this.momsTeam = momsTeam;
    }

    public String getMomsAcShop() {
        return momsAcShop;
    }

    public void setMomsAcShop(String momsAcShop) {
        this.momsAcShop = momsAcShop;
    }

    public String getMomsAreaFg() {
        return momsAreaFg;
    }

    public void setMomsAreaFg(String momsAreaFg) {
        this.momsAreaFg = momsAreaFg;
    }

    public String getMomsCommercial() {
        return momsCommercial;
    }

    public void setMomsCommercial(String momsCommercial) {
        this.momsCommercial = momsCommercial;
    }

    public String getMomsShopType() {
        return momsShopType;
    }

    public void setMomsShopType(String momsShopType) {
        this.momsShopType = momsShopType;
    }

    public String getMomsStoreManageType() {
        return momsStoreManageType;
    }

    public void setMomsStoreManageType(String momsStoreManageType) {
        this.momsStoreManageType = momsStoreManageType;
    }

    public String getMomsEnvstVal() {
        return momsEnvstVal;
    }

    public void setMomsEnvstVal(String momsEnvstVal) {
        this.momsEnvstVal = momsEnvstVal;
    }

    public String getSiteCd() {
        return siteCd;
    }

    public void setSiteCd(String siteCd) {
        this.siteCd = siteCd;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
    }

    public String getVendorFg() {
        return vendorFg;
    }

    public void setVendorFg(String vendorFg) {
        this.vendorFg = vendorFg;
    }

    public String getVendorFgNm() {
        return vendorFgNm;
    }

    public void setVendorFgNm(String vendorFgNm) {
        this.vendorFgNm = vendorFgNm;
    }

    public String getVendorCd() {
        return vendorCd;
    }

    public void setVendorCd(String vendorCd) {
        this.vendorCd = vendorCd;
    }

    public String getVendorNm() {
        return vendorNm;
    }

    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    public String getVendorTermnlNo() {
        return vendorTermnlNo;
    }

    public void setVendorTermnlNo(String vendorTermnlNo) {
        this.vendorTermnlNo = vendorTermnlNo;
    }

    public String getVendorSerNo() {
        return vendorSerNo;
    }

    public void setVendorSerNo(String vendorSerNo) {
        this.vendorSerNo = vendorSerNo;
    }

    public String getMomsStoreFg01() { return momsStoreFg01; }

    public void setMomsStoreFg01(String momsStoreFg01) { this.momsStoreFg01 = momsStoreFg01; }

    public String getMomsStoreFg02() { return momsStoreFg02; }

    public void setMomsStoreFg02(String momsStoreFg02) { this.momsStoreFg02 = momsStoreFg02; }

    public String getMomsStoreFg03() { return momsStoreFg03; }

    public void setMomsStoreFg03(String momsStoreFg03) { this.momsStoreFg03 = momsStoreFg03; }

    public String getMomsStoreFg04() { return momsStoreFg04; }

    public void setMomsStoreFg04(String momsStoreFg04) { this.momsStoreFg04 = momsStoreFg04; }

    public String getMomsStoreFg05() { return momsStoreFg05; }

    public void setMomsStoreFg05(String momsStoreFg05) { this.momsStoreFg05 = momsStoreFg05; }

    public String getLangFg() {
        return langFg;
    }

    public void setLangFg(String langFg) {
        this.langFg = langFg;
    }

    public String getAdverUrl() {
        return adverUrl;
    }

    public void setAdverUrl(String adverUrl) {
        this.adverUrl = adverUrl;
    }

    public String getBaseVanYn() {
        return baseVanYn;
    }

    public void setBaseVanYn(String baseVanYn) {
        this.baseVanYn = baseVanYn;
    }

    public String getStrPosNo() {
        return strPosNo;
    }

    public void setStrPosNo(String strPosNo) {
        this.strPosNo = strPosNo;
    }

    public String getbVendorCd() {
        return bVendorCd;
    }

    public void setbVendorCd(String bVendorCd) {
        this.bVendorCd = bVendorCd;
    }

    public String getCornrCd() {
        return cornrCd;
    }

    public void setCornrCd(String cornrCd) {
        this.cornrCd = cornrCd;
    }

    public String getNmcodeAuthHqCnt() {
        return nmcodeAuthHqCnt;
    }

    public void setNmcodeAuthHqCnt(String nmcodeAuthHqCnt) {
        this.nmcodeAuthHqCnt = nmcodeAuthHqCnt;
    }

    public String getSelectHqOfficeCd() {
        return selectHqOfficeCd;
    }

    public void setSelectHqOfficeCd(String selectHqOfficeCd) {
        this.selectHqOfficeCd = selectHqOfficeCd;
    }
}