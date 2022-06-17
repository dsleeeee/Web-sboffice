package kr.co.solbipos.dlvr.info.deliveryTelNo.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DeliveryTelNoVO extends PageVO {
    /** 순번 */
    private int seq;
    /** 조회본사 */
    private String hqOfficeCd;
    /** 조회매장 */
    private String storeCd;
    /** 세션 ID */
    private String sessionId;
    /** 호출일시 */
    private String cidCallDt;
    /** CID라인번호 */
    private String cidLineNo;
    /** CID전화번호 */
    private String cidTelNo;
    /** 배달주소 */
    private String dlvrAddr;
    /** 배달주소상세 */
    private String dlvrAddrDtl;
    /** 주문번호 */
    private String orderNo;
    /** 배달구분 */
    private String dlvrFg;
    /** 배달메모 */
    private String dlvrMemo;
    /** 검증결과 */
    private String result;
    /** 삭제구분 */
    private String deleteFg;
    /** 영업일자 */
    private String saleDate;
    /** CID CALL Sequenc */
    private String cidCallSeq;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCidCallDt() {
        return cidCallDt;
    }

    public void setCidCallDt(String cidCallDt) {
        this.cidCallDt = cidCallDt;
    }

    public String getCidLineNo() {
        return cidLineNo;
    }

    public void setCidLineNo(String cidLineNo) {
        this.cidLineNo = cidLineNo;
    }

    public String getCidTelNo() {
        return cidTelNo;
    }

    public void setCidTelNo(String cidTelNo) {
        this.cidTelNo = cidTelNo;
    }

    public String getDlvrAddr() {
        return dlvrAddr;
    }

    public void setDlvrAddr(String dlvrAddr) {
        this.dlvrAddr = dlvrAddr;
    }

    public String getDlvrAddrDtl() {
        return dlvrAddrDtl;
    }

    public void setDlvrAddrDtl(String dlvrAddrDtl) {
        this.dlvrAddrDtl = dlvrAddrDtl;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDlvrFg() {
        return dlvrFg;
    }

    public void setDlvrFg(String dlvrFg) {
        this.dlvrFg = dlvrFg;
    }

    public String getDlvrMemo() {
        return dlvrMemo;
    }

    public void setDlvrMemo(String dlvrMemo) {
        this.dlvrMemo = dlvrMemo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDeleteFg() {
        return deleteFg;
    }

    public void setDeleteFg(String deleteFg) {
        this.deleteFg = deleteFg;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getCidCallSeq() {
        return cidCallSeq;
    }

    public void setCidCallSeq(String cidCallSeq) {
        this.cidCallSeq = cidCallSeq;
    }
}
