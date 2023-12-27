package kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : FnkeyCmNmcdVO.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(기능키/공통코드)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.12.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class FnkeyCmNmcdVO extends PageVO {

    private static final long serialVersionUID = -5422207322395607788L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 기능키구분 */
    private String fnkeyFg;
    /** 기능키명 */
    private String fnkeyNm;
    /** 기능키번호 */
    private String fnkeyNo;
    /** 기능키명(영문)*/
    private String fnkeyEnNm;
    /** 기능키명(중문) */
    private String fnkeyCnNm;
    /** 기능키명(일문) */
    private String fnkeyJpNm;
    /** 공통코드그룹코드*/
   private String nmcodeGrpCd;
    /** 공통코드 */
    private String nmcodeCd;
    /** 공통코드명 */
    private String nmcodeNm;
    /** 공통코드명(영문)*/
    private String nmcodeEnNm;
    /** 공통코드명(중문) */
    private String nmcodeCnNm;
    /** 공통코드명(일문) */
    private String nmcodeJpNm;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;
    /** 수정한 기능키 번호 array*/
    private String arrFnkeyNo[];

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

    public String getFnkeyFg() {
        return fnkeyFg;
    }

    public void setFnkeyFg(String fnkeyFg) {
        this.fnkeyFg = fnkeyFg;
    }

    public String getFnkeyNm() {
        return fnkeyNm;
    }

    public void setFnkeyNm(String fnkeyNm) {
        this.fnkeyNm = fnkeyNm;
    }

    public String getFnkeyNo() {
        return fnkeyNo;
    }

    public void setFnkeyNo(String fnkeyNo) {
        this.fnkeyNo = fnkeyNo;
    }

    public String getFnkeyEnNm() {
        return fnkeyEnNm;
    }

    public void setFnkeyEnNm(String fnkeyEnNm) {
        this.fnkeyEnNm = fnkeyEnNm;
    }

    public String getFnkeyCnNm() {
        return fnkeyCnNm;
    }

    public void setFnkeyCnNm(String fnkeyCnNm) {
        this.fnkeyCnNm = fnkeyCnNm;
    }

    public String getFnkeyJpNm() {
        return fnkeyJpNm;
    }

    public void setFnkeyJpNm(String fnkeyJpNm) {
        this.fnkeyJpNm = fnkeyJpNm;
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

    public String getNmcodeEnNm() {
        return nmcodeEnNm;
    }

    public void setNmcodeEnNm(String nmcodeEnNm) {
        this.nmcodeEnNm = nmcodeEnNm;
    }

    public String getNmcodeCnNm() {
        return nmcodeCnNm;
    }

    public void setNmcodeCnNm(String nmcodeCnNm) {
        this.nmcodeCnNm = nmcodeCnNm;
    }

    public String getNmcodeJpNm() {
        return nmcodeJpNm;
    }

    public void setNmcodeJpNm(String nmcodeJpNm) {
        this.nmcodeJpNm = nmcodeJpNm;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String[] getArrFnkeyNo() {
        return arrFnkeyNo;
    }

    public void setArrFnkeyNo(String[] arrFnkeyNo) {
        this.arrFnkeyNo = arrFnkeyNo;
    }
}
