package kr.co.sample.application.domain;

import kr.co.solbipos.application.common.service.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SslTrdtlTVO extends PageVO {
    private String shopCd;
    private String saleDate;
    private String posNo;
    private String billNo;

    private String dtlNo;

    private String regiSeq;

    private String saleYn;

    private Long saleFg;

    private String prodCd;

    private String prodTypeFg;

    private String cornerCd;

    private String chgBillNo;

    private String taxYn;

    private String dlvPackFg;

    private String orgSaleMgCd;

    private Long orgSaleUprc;

    private Long normalUprc;

    private String saleMgCd;

    private Long saleQty;

    private Long saleUprc;

    private Long saleAmt;

    private Long dcAmt;

    private Long etcAmt;

    private Long svcTipAmt;

    private Long dcmSaleAmt;

    private Long vatAmt;

    private String svcCd;

    private String tkCpnCd;

    private Long dcAmtGen;

    private Long dcAmtSvc;

    private Long dcAmtJcd;

    private Long dcAmtCpn;

    private Long dcAmtCst;

    private Long dcAmtFod;

    private Long dcAmtPrm;

    private Long dcAmtCrd;

    private Long dcAmtPack;

    private Long cstSalePoint;

    private Long cstUsePoint;

    private String prmProcYn;

    private String prmCd;

    private String prmSeq;

    private String sdaCd;

    private String sdsOrgDtlNo;

    private String posInsDt;

    private String empNo;

    private String insDt;

    private String updDt;

    private String updId;

    private Long costAmt;

    private String setProdFg;

    private String localPointYn;
}
