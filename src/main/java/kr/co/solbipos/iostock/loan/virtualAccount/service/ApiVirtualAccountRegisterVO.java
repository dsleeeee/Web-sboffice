package kr.co.solbipos.iostock.loan.virtualAccount.service;

/**
 * @Class Name : ApiVirtualAccountRegisterVO.java
 * @Description : KCP API 호출(가상계좌 발급)
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

public class ApiVirtualAccountRegisterVO {

    private static final long serialVersionUID = 4567094904301269212L;

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
}