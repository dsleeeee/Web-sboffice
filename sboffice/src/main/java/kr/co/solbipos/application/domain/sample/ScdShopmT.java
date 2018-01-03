package kr.co.solbipos.application.domain.sample;

import kr.co.solbipos.application.domain.Page;
import lombok.Data;

public @Data class ScdShopmT extends Page{
    private String shopCd;
    private String shopNm;
    private String hdShopCd;
    private String shopGroupCd;
    private String shopTypeFg;
    private String deleteYn;
}
