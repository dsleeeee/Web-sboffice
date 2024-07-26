package kr.co.solbipos.iostock.loan.virtualAccount.service;

/**
 * @Class Name : ApiVirtualAccountRegisterReceiveVO.java
 * @Description : KCP API 응답(가상계좌 발급)
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
public class ApiVirtualAccountRegisterReceiveVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 결과코드 */
    private String res_cd;

    /** 결과메세지 */
    private String res_msg;

    /** NHN KCP 거래 고유번호 */
    private String tno;

    /** 총 금액 */
    private int amount;

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
    private String pay_method;
    private String trace_no;
    private String va_name;
    private String va_date;
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

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

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

    public String getPay_method() { return pay_method; }

    public void setPay_method(String pay_method) { this.pay_method = pay_method; }

    public String getTrace_no() { return trace_no; }

    public void setTrace_no(String trace_no) { this.trace_no = trace_no; }

    public String getVa_name() { return va_name; }

    public void setVa_name(String va_name) { this.va_name = va_name; }

    public String getVa_date() { return va_date; }

    public void setVa_date(String va_date) { this.va_date = va_date; }

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
}