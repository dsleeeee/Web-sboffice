package kr.co.sample.application.domain;

import kr.co.solbipos.application.domain.cmm.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TbMsStoreVO extends PageVO {
  private String storeCd;
  private String storeNm;
  private String ownerNm;
  private String hqCd;
  private String hqNm;
  private String hqBrandCd;
  private String bizNo;
  private String bizTypeCd;
  private String bizItemCd;
  private String bizStoreNm;
  private String telNo;
  private String faxNo;
  private String emailAddr;
  private String hmpgAddr;
  private String postNo;
  private String addr;
  private String addrDtl;
  private String areaCd;
  private String sysStatFg;
  private String sysOpenDate;
  private String sysClosureDate;
  private String vanCd;
  private String agencyCd;
  private String remark;
  private String regDt;
  private String regId;
  private String modDt;
  private String modId;
}
