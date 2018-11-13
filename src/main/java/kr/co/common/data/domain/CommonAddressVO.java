package kr.co.common.data.domain;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : CommonAddressVO.java
 * @Description : 주소 검색
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.30  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CommonAddressVO extends CmmVO {

    private static final long serialVersionUID = 5836102353818252969L;

    // 요청변수
    /** 승인키 */
    private String confmKey = "U01TX0FVVEgyMDE4MTAzMDE2MzI1NDEwODI2Nzg=";
    /** 현재 페이지 번호*/
    private Integer currentPage;
    /** 페이지당 출력할 결과 Row 수 */
    private Integer countPerPage;
    /** 주소 검색어 */
    private String keyword;
    /** 검색결과형식 설정(xml, json) */
    private String resultType;

    // 출력결과
    /** 총 검색 데이터수 */
    private String totalCount;
    /** 에러코드 */
    private String errorCode;
    /** 에러 메시지 */
    private String errorMessage;
    /** 전체 도로명 주소 */
    private String roadAddr;
    /** 도로명주소(참고항목 제외) */
    private String roadAddrPart1;
    /** 도로명주소 참고항목 */
    private String roadAddrPart2;
    /** 지번정보 */
    private String jibunAddr;
    /** 도로명주소(영문) */
    private String engAddr;
    /** 우편번호 */
    private String zipNo;
    /** 행정구역코드 */
    private String admCd;
    /** 도로명코드 */
    private String rnMgtSn;
    /** 건물관리번호 */
    private String bdMgtSn;
    /** 상세건물명 */
    private String detBdNmList;
    /** 건물명 */
    private String bdNm;
    /** 공동주택여부 (1:공동주택, 0:비공동주택)*/
    private String bdKdcd;
    /** 시도명 */
    private String siNm;
    /** 시군구명 */
    private String sggNm;
    /** 읍면동명 */
    private String emdNm;
    /** 법정리명 */
    private String liNm;
    /** 도로명 */
    private String rn;
    /** 지하여부 (0:지상, 1:지하)*/
    private String udrtYn;
    /** 건물본번 */
    private String buldMnnm;
    /** 건물부번 (부번이 없는 경우 0 )*/
    private String buldSlno;
    /** 산여부 (0:대지 1:산) */
    private String mtYn;
    /** 지번본번(번지) */
    private String lnbrMnnm;
    /** 지번부번(호) (부번이 없는 경우 0) */
    private String lnbrSlno;
    /** 읍면동일련번호 */
    private String emdNo;


    /**
     * @return the confmKey
     */
    public String getConfmKey() {
        return confmKey;
    }

    /**
     * @param confmKey the confmKey to set
     */
    public void setConfmKey(String confmKey) {
        this.confmKey = confmKey;
    }

    /**
     * @return the currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the countPerPage
     */
    public Integer getCountPerPage() {
        return countPerPage;
    }

    /**
     * @param countPerPage the countPerPage to set
     */
    public void setCountPerPage(Integer countPerPage) {
        this.countPerPage = countPerPage;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the resultType
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * @param resultType the resultType to set
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * @return the totalCount
     */
    public String getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the roadAddr
     */
    public String getRoadAddr() {
        return roadAddr;
    }

    /**
     * @param roadAddr the roadAddr to set
     */
    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    /**
     * @return the roadAddrPart1
     */
    public String getRoadAddrPart1() {
        return roadAddrPart1;
    }

    /**
     * @param roadAddrPart1 the roadAddrPart1 to set
     */
    public void setRoadAddrPart1(String roadAddrPart1) {
        this.roadAddrPart1 = roadAddrPart1;
    }

    /**
     * @return the roadAddrPart2
     */
    public String getRoadAddrPart2() {
        return roadAddrPart2;
    }

    /**
     * @param roadAddrPart2 the roadAddrPart2 to set
     */
    public void setRoadAddrPart2(String roadAddrPart2) {
        this.roadAddrPart2 = roadAddrPart2;
    }

    /**
     * @return the jibunAddr
     */
    public String getJibunAddr() {
        return jibunAddr;
    }

    /**
     * @param jibunAddr the jibunAddr to set
     */
    public void setJibunAddr(String jibunAddr) {
        this.jibunAddr = jibunAddr;
    }

    /**
     * @return the engAddr
     */
    public String getEngAddr() {
        return engAddr;
    }

    /**
     * @param engAddr the engAddr to set
     */
    public void setEngAddr(String engAddr) {
        this.engAddr = engAddr;
    }

    /**
     * @return the zipNo
     */
    public String getZipNo() {
        return zipNo;
    }

    /**
     * @param zipNo the zipNo to set
     */
    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }

    /**
     * @return the admCd
     */
    public String getAdmCd() {
        return admCd;
    }

    /**
     * @param admCd the admCd to set
     */
    public void setAdmCd(String admCd) {
        this.admCd = admCd;
    }

    /**
     * @return the rnMgtSn
     */
    public String getRnMgtSn() {
        return rnMgtSn;
    }

    /**
     * @param rnMgtSn the rnMgtSn to set
     */
    public void setRnMgtSn(String rnMgtSn) {
        this.rnMgtSn = rnMgtSn;
    }

    /**
     * @return the bdMgtSn
     */
    public String getBdMgtSn() {
        return bdMgtSn;
    }

    /**
     * @param bdMgtSn the bdMgtSn to set
     */
    public void setBdMgtSn(String bdMgtSn) {
        this.bdMgtSn = bdMgtSn;
    }

    /**
     * @return the detBdNmList
     */
    public String getDetBdNmList() {
        return detBdNmList;
    }

    /**
     * @param detBdNmList the detBdNmList to set
     */
    public void setDetBdNmList(String detBdNmList) {
        this.detBdNmList = detBdNmList;
    }

    /**
     * @return the bdNm
     */
    public String getBdNm() {
        return bdNm;
    }

    /**
     * @param bdNm the bdNm to set
     */
    public void setBdNm(String bdNm) {
        this.bdNm = bdNm;
    }

    /**
     * @return the bdKdcd
     */
    public String getBdKdcd() {
        return bdKdcd;
    }

    /**
     * @param bdKdcd the bdKdcd to set
     */
    public void setBdKdcd(String bdKdcd) {
        this.bdKdcd = bdKdcd;
    }

    /**
     * @return the siNm
     */
    public String getSiNm() {
        return siNm;
    }

    /**
     * @param siNm the siNm to set
     */
    public void setSiNm(String siNm) {
        this.siNm = siNm;
    }

    /**
     * @return the sggNm
     */
    public String getSggNm() {
        return sggNm;
    }

    /**
     * @param sggNm the sggNm to set
     */
    public void setSggNm(String sggNm) {
        this.sggNm = sggNm;
    }

    /**
     * @return the emdNm
     */
    public String getEmdNm() {
        return emdNm;
    }

    /**
     * @param emdNm the emdNm to set
     */
    public void setEmdNm(String emdNm) {
        this.emdNm = emdNm;
    }

    /**
     * @return the liNm
     */
    public String getLiNm() {
        return liNm;
    }

    /**
     * @param liNm the liNm to set
     */
    public void setLiNm(String liNm) {
        this.liNm = liNm;
    }

    /**
     * @return the rn
     */
    public String getRn() {
        return rn;
    }

    /**
     * @param rn the rn to set
     */
    public void setRn(String rn) {
        this.rn = rn;
    }

    /**
     * @return the udrtYn
     */
    public String getUdrtYn() {
        return udrtYn;
    }

    /**
     * @param udrtYn the udrtYn to set
     */
    public void setUdrtYn(String udrtYn) {
        this.udrtYn = udrtYn;
    }

    /**
     * @return the buldMnnm
     */
    public String getBuldMnnm() {
        return buldMnnm;
    }

    /**
     * @param buldMnnm the buldMnnm to set
     */
    public void setBuldMnnm(String buldMnnm) {
        this.buldMnnm = buldMnnm;
    }

    /**
     * @return the buldSlno
     */
    public String getBuldSlno() {
        return buldSlno;
    }

    /**
     * @param buldSlno the buldSlno to set
     */
    public void setBuldSlno(String buldSlno) {
        this.buldSlno = buldSlno;
    }

    /**
     * @return the mtYn
     */
    public String getMtYn() {
        return mtYn;
    }

    /**
     * @param mtYn the mtYn to set
     */
    public void setMtYn(String mtYn) {
        this.mtYn = mtYn;
    }

    /**
     * @return the lnbrMnnm
     */
    public String getLnbrMnnm() {
        return lnbrMnnm;
    }

    /**
     * @param lnbrMnnm the lnbrMnnm to set
     */
    public void setLnbrMnnm(String lnbrMnnm) {
        this.lnbrMnnm = lnbrMnnm;
    }

    /**
     * @return the lnbrSlno
     */
    public String getLnbrSlno() {
        return lnbrSlno;
    }

    /**
     * @param lnbrSlno the lnbrSlno to set
     */
    public void setLnbrSlno(String lnbrSlno) {
        this.lnbrSlno = lnbrSlno;
    }

    /**
     * @return the emdNo
     */
    public String getEmdNo() {
        return emdNo;
    }

    /**
     * @param emdNo the emdNo to set
     */
    public void setEmdNo(String emdNo) {
        this.emdNo = emdNo;
    }
}
