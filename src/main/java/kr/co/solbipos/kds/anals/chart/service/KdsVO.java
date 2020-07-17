package kr.co.solbipos.kds.anals.chart.service;

import kr.co.solbipos.application.common.service.PageVO;

import java.util.List;
import java.util.Map;

public class KdsVO extends PageVO {
    private String hqOfficeCd;
    private String storeCd;
    private String saleDate;
    private String startDate;
    private String endDate;
    private String startHh;
    private String endHh;
    private String saleHh;
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
    private String kdsDayStartDate;
    private String kdsDayEndDate;
    private String makeDate;
    private String makeDateSec;
    private String picDate;
    private String picDateSec;
//    private List<Map<String, Object>> kdsTimeList;
    private List<String> kdsTimeList;


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

    public String getKdsDayStartDate() {
        return kdsDayStartDate;
    }

    public void setKdsDayStartDate(String kdsDayStartDate) {
        this.kdsDayStartDate = kdsDayStartDate;
    }

    public String getKdsDayEndDate() {
        return kdsDayEndDate;
    }

    public void setKdsDayEndDate(String kdsDayEndDate) {
        this.kdsDayEndDate = kdsDayEndDate;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public String getMakeDateSec() {
        return makeDateSec;
    }

    public void setMakeDateSec(String makeDateSec) {
        this.makeDateSec = makeDateSec;
    }

    public String getPicDate() {
        return picDate;
    }

    public void setPicDate(String picDate) {
        this.picDate = picDate;
    }

    public String getPicDateSec() {
        return picDateSec;
    }

    public void setPicDateSec(String picDateSec) {
        this.picDateSec = picDateSec;
    }

    public String getStartHh() {
        return startHh;
    }

    public void setStartHh(String startHh) {
        this.startHh = startHh;
    }

    public String getEndHh() {
        return endHh;
    }

    public void setEndHh(String endHh) {
        this.endHh = endHh;
    }

    public String getSaleHh() {
        return saleHh;
    }

    public void setSaleHh(String saleHh) {
        this.saleHh = saleHh;
    }

//    public List<Map<String, Object>> getKdsTimeList() {
//        return kdsTimeList;
//    }
//
//    public void setKdsTimeList(List<Map<String, Object>> kdsTimeList) {
//        this.kdsTimeList = kdsTimeList;
//    }

    public List<String> getKdsTimeList() {
        return kdsTimeList;
    }

    public void setKdsTimeList(List<String> kdsTimeList) {
        this.kdsTimeList = kdsTimeList;
    }
}
