package kr.co.solbipos.kds.anals.day.service;

import kr.co.solbipos.application.common.service.PageVO;

public class KdsDayVO extends PageVO {
    private String storeCd;
    private String saleDate;
    private Integer orderCnt;
    private Integer sCkToECk;
    private Integer avgSCkToECk;
    private Integer lOdToECk;
    private Integer avgLOdToECk;
    private Integer sOdToEPk;
    private Integer avgSOdToEPk;
    private Integer eCkToEPk;
    private Integer avgECkToEPk;
    private String prodCd;
    private Integer salePty;
    private Integer lOdToDCk;

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getOrderCnt() {
        return orderCnt;
    }

    public void setOrderCnt(Integer orderCnt) {
        this.orderCnt = orderCnt;
    }

    public Integer getsCkToECk() {
        return sCkToECk;
    }

    public void setsCkToECk(Integer sCkToECk) {
        this.sCkToECk = sCkToECk;
    }

    public Integer getAvgSCkToECk() {
        return avgSCkToECk;
    }

    public void setAvgSCkToECk(Integer avgSCkToECk) {
        this.avgSCkToECk = avgSCkToECk;
    }

    public Integer getlOdToECk() {
        return lOdToECk;
    }

    public void setlOdToECk(Integer lOdToECk) {
        this.lOdToECk = lOdToECk;
    }

    public Integer getAvgLOdToECk() {
        return avgLOdToECk;
    }

    public void setAvgLOdToECk(Integer avgLOdToECk) {
        this.avgLOdToECk = avgLOdToECk;
    }

    public Integer getsOdToEPk() {
        return sOdToEPk;
    }

    public void setsOdToEPk(Integer sOdToEPk) {
        this.sOdToEPk = sOdToEPk;
    }

    public Integer getAvgSOdToEPk() {
        return avgSOdToEPk;
    }

    public void setAvgSOdToEPk(Integer avgSOdToEPk) {
        this.avgSOdToEPk = avgSOdToEPk;
    }

    public Integer geteCkToEPk() {
        return eCkToEPk;
    }

    public void seteCkToEPk(Integer eCkToEPk) {
        this.eCkToEPk = eCkToEPk;
    }

    public Integer getAvgECkToEPk() {
        return avgECkToEPk;
    }

    public void setAvgECkToEPk(Integer avgECkToEPk) {
        this.avgECkToEPk = avgECkToEPk;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public Integer getSalePty() {
        return salePty;
    }

    public void setSalePty(Integer salePty) {
        this.salePty = salePty;
    }

    public Integer getlOdToDCk() {
        return lOdToDCk;
    }

    public void setlOdToDCk(Integer lOdToDCk) {
        this.lOdToDCk = lOdToDCk;
    }
}
