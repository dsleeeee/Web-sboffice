package kr.co.sample.application.domain;

import kr.co.solbipos.application.domain.cmm.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ScdShopmTVO extends PageVO{
    private String shopCd;
    private String shopNm;
    private String hdShopCd;
    private String shopGroupCd;
    private String shopTypeFg;
    private String deleteYn;
}
