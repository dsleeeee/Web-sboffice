package kr.co.solbipos.sale.card.cardCredit.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : CardCreditServiceVO.java
 * @Description : 광운대 > 신용카드입금관리 > 신용카드입금관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CardCreditVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사브랜드코드 */
    private String hqBrandCd;

    /** 조회매장 */
    private String storeCd;

    /** 영업일자 */
    private String saleDate;

    /** 포스번호 */
    private String posNo;

    /** 영수증번호 */
    private String billNo;

    /** 라인번호 */
    private String lineNo;

    /** 라인일련번호 */
    private String lineSeqNo;

    /** 입금일자 */
    private String creditDate;

    /** 입금금액 */
    private String creditAmt;

    /** 수수료 */
    private String creditFee;

    /** 입금은행 */
    private String creditBank;

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getHqBrandCd() { return hqBrandCd; }

    public void setHqBrandCd(String hqBrandCd) { this.hqBrandCd = hqBrandCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getSaleDate() { return saleDate; }

    public void setSaleDate(String saleDate) { this.saleDate = saleDate; }

    public String getPosNo() { return posNo; }

    public void setPosNo(String posNo) { this.posNo = posNo; }

    public String getBillNo() { return billNo; }

    public void setBillNo(String billNo) { this.billNo = billNo; }

    public String getLineNo() { return lineNo; }

    public void setLineNo(String lineNo) { this.lineNo = lineNo; }

    public String getLineSeqNo() { return lineSeqNo; }

    public void setLineSeqNo(String lineSeqNo) { this.lineSeqNo = lineSeqNo; }

    public String getCreditDate() { return creditDate; }

    public void setCreditDate(String creditDate) { this.creditDate = creditDate; }

    public String getCreditAmt() { return creditAmt; }

    public void setCreditAmt(String creditAmt) { this.creditAmt = creditAmt; }

    public String getCreditFee() { return creditFee; }

    public void setCreditFee(String creditFee) { this.creditFee = creditFee; }

    public String getCreditBank() { return creditBank; }

    public void setCreditBank(String creditBank) { this.creditBank = creditBank; }
}