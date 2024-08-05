package kr.co.solbipos.iostock.loan.virtualAccount.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : VirtualAccountVO.java
 * @Description : 수불관리 > 주문관리 > 가상계좌내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VirtualAccountVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 소속코드 */
    private String orgnCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 등록일자 */
    private String reqDate;

    /** 등록순번 */
    private String reqSeq;

    /** 등록일시 */
    private String regDt;

    /** 사용자ID */
    private String userId;

    /** 응답 수신 시간 */
    private String resDt;

    /** 가상계좌 상태구분 */
    private String depositFg;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreNm() { return storeNm; }

    public void setStoreNm(String storeNm) { this.storeNm = storeNm; }

    public String getReqDate() { return reqDate; }

    public void setReqDate(String reqDate) { this.reqDate = reqDate; }

    public String getReqSeq() { return reqSeq; }

    public void setReqSeq(String reqSeq) { this.reqSeq = reqSeq; }

    public String getRegDt() { return regDt; }

    public void setRegDt(String regDt) { this.regDt = regDt; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getResDt() { return resDt; }

    public void setResDt(String resDt) { this.resDt = resDt; }

    public String getDepositFg() { return depositFg; }

    public void setDepositFg(String depositFg) { this.depositFg = depositFg; }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// KCP API 호출(가상계좌 발급) start

    /** NHN KCP 발급 사이트코드 */
    private String site_cd;

    /** KCP PG-API 인증서정보(직렬화) */
    private String kcp_cert_info;

    /** 결제수단 (VCNT : 고정값) */
    private String pay_method;

    /** 요청자 IP */
    private String cust_ip;

    /** 총 금액 */
    private int amount;

    /** 가상계좌 발급금액 */
    private int va_mny;

    /** 화폐단위(원화) (410 : 고정값) */
    private String currency;

    /** 상점관리 주문번호 (유니크한 값 설정 권장) */
    private String ordr_idxx;

    /** 상품명 */
    private String good_name;

    /** 주문자 명 */
    private String buyr_name;

    /** 주문자 E-Mail */
    private String buyr_mail;

    /** 주문자 휴대폰번호 */
    private String buyr_tel2;

    /** 가상계좌 발급타입 (41100000 : 고정값) */
    private String va_txtype;

    /** 발급할 계좌의 은행코드 */
    private String va_bankcode;

    /** 입금자명 */
    private String va_name;

    /** 입금 마감시각 */
    private String va_date;

    /** 현금영수증 발행용도 */
    private String va_receipt_gubn;

    /** 현금영수증 식별번호 */
    private String va_taxno;

    public String getSite_cd() { return site_cd; }

    public void setSite_cd(String site_cd) { this.site_cd = site_cd; }

    public String getKcp_cert_info() { return kcp_cert_info; }

    public void setKcp_cert_info(String kcp_cert_info) { this.kcp_cert_info = kcp_cert_info; }

    public String getPay_method() { return pay_method; }

    public void setPay_method(String pay_method) { this.pay_method = pay_method; }

    public String getCust_ip() { return cust_ip; }

    public void setCust_ip(String cust_ip) { this.cust_ip = cust_ip; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public int getVa_mny() { return va_mny; }

    public void setVa_mny(int va_mny) { this.va_mny = va_mny; }

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public String getOrdr_idxx() { return ordr_idxx; }

    public void setOrdr_idxx(String ordr_idxx) { this.ordr_idxx = ordr_idxx; }

    public String getGood_name() { return good_name; }

    public void setGood_name(String good_name) { this.good_name = good_name; }

    public String getBuyr_name() { return buyr_name; }

    public void setBuyr_name(String buyr_name) { this.buyr_name = buyr_name; }

    public String getBuyr_mail() { return buyr_mail; }

    public void setBuyr_mail(String buyr_mail) { this.buyr_mail = buyr_mail; }

    public String getBuyr_tel2() { return buyr_tel2; }

    public void setBuyr_tel2(String buyr_tel2) { this.buyr_tel2 = buyr_tel2; }

    public String getVa_txtype() { return va_txtype; }

    public void setVa_txtype(String va_txtype) { this.va_txtype = va_txtype; }

    public String getVa_bankcode() { return va_bankcode; }

    public void setVa_bankcode(String va_bankcode) { this.va_bankcode = va_bankcode; }

    public String getVa_name() { return va_name; }

    public void setVa_name(String va_name) { this.va_name = va_name; }

    public String getVa_date() { return va_date; }

    public void setVa_date(String va_date) { this.va_date = va_date; }

    public String getVa_receipt_gubn() { return va_receipt_gubn; }

    public void setVa_receipt_gubn(String va_receipt_gubn) { this.va_receipt_gubn = va_receipt_gubn; }

    public String getVa_taxno() { return va_taxno; }

    public void setVa_taxno(String va_taxno) { this.va_taxno = va_taxno; }

// KCP API 호출(가상계좌 발급) end
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// KCP API 응답(가상계좌 발급) start
    /** 결과코드 */
    private String res_cd;

    /** 결과메세지 */
    private String res_msg;

    /** NHN KCP 거래 고유번호 */
    private String tno;

    /** 총 금액 */
//    private int amount;

    /** 가상계좌 은행명 */
    private String bankname;

    /** 가상계좌 은행코드 */
    private String bankcode;

    /** 가상계좌 예금주명 */
    private String depositor;

    /** 가상계좌 번호 */
    private String account;

    /** 가상계좌 발급 시각 */
    private String app_time;

    private String order_no;
    private String mall_taxno;
    private String van_apptime;
    private String res_free_mny;
//    private String pay_method;
    private String trace_no;
//    private String va_name;
//    private String va_date;
    private String escw_yn;
    private String res_vat_mny;
    private String res_tax_flag;
    private String res_en_msg;
    private String van_txid;
    private String res_tax_mny;
    private String van_cd;

    public String getRes_cd() { return res_cd; }

    public void setRes_cd(String res_cd) { this.res_cd = res_cd; }

    public String getRes_msg() { return res_msg; }

    public void setRes_msg(String res_msg) { this.res_msg = res_msg; }

    public String getTno() { return tno; }

    public void setTno(String tno) { this.tno = tno; }

//    public int getAmount() { return amount; }
//
//    public void setAmount(int amount) { this.amount = amount; }

    public String getBankname() { return bankname; }

    public void setBankname(String bankname) { this.bankname = bankname; }

    public String getBankcode() { return bankcode; }

    public void setBankcode(String bankcode) { this.bankcode = bankcode; }

    public String getDepositor() { return depositor; }

    public void setDepositor(String depositor) { this.depositor = depositor; }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }

    public String getApp_time() { return app_time; }

    public void setApp_time(String app_time) { this.app_time = app_time; }

    public String getOrder_no() { return order_no; }

    public void setOrder_no(String order_no) { this.order_no = order_no; }

    public String getMall_taxno() { return mall_taxno; }

    public void setMall_taxno(String mall_taxno) { this.mall_taxno = mall_taxno; }

    public String getVan_apptime() { return van_apptime; }

    public void setVan_apptime(String van_apptime) { this.van_apptime = van_apptime; }

    public String getRes_free_mny() { return res_free_mny; }

    public void setRes_free_mny(String res_free_mny) { this.res_free_mny = res_free_mny; }

//    public String getPay_method() { return pay_method; }
//
//    public void setPay_method(String pay_method) { this.pay_method = pay_method; }

    public String getTrace_no() { return trace_no; }

    public void setTrace_no(String trace_no) { this.trace_no = trace_no; }

//    public String getVa_name() { return va_name; }
//
//    public void setVa_name(String va_name) { this.va_name = va_name; }
//
//    public String getVa_date() { return va_date; }
//
//    public void setVa_date(String va_date) { this.va_date = va_date; }

    public String getEscw_yn() { return escw_yn; }

    public void setEscw_yn(String escw_yn) { this.escw_yn = escw_yn; }

    public String getRes_vat_mny() { return res_vat_mny; }

    public void setRes_vat_mny(String res_vat_mny) { this.res_vat_mny = res_vat_mny; }

    public String getRes_tax_flag() { return res_tax_flag; }

    public void setRes_tax_flag(String res_tax_flag) { this.res_tax_flag = res_tax_flag; }

    public String getRes_en_msg() { return res_en_msg; }

    public void setRes_en_msg(String res_en_msg) { this.res_en_msg = res_en_msg; }

    public String getVan_txid() { return van_txid; }

    public void setVan_txid(String van_txid) { this.van_txid = van_txid; }

    public String getRes_tax_mny() { return res_tax_mny; }

    public void setRes_tax_mny(String res_tax_mny) { this.res_tax_mny = res_tax_mny; }

    public String getVan_cd() { return van_cd; }

    public void setVan_cd(String van_cd) { this.van_cd = van_cd; }

// KCP API 응답(가상계좌 발급) end
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}