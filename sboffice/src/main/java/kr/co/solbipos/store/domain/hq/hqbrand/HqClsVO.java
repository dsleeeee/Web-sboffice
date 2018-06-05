package kr.co.solbipos.store.domain.hq.hqbrand;

import java.util.List;
import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.domain.cmm.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 본사정보 > 브랜드정보관리
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqClsVO extends CmmVO {

    
    /** 본사코드 */
    private String hqOfficeCd;
    
    /** 브랜드코드 */
    private String hqBrandCd;

    /** 상품분류코드 */
    private String prodClassCd;
    
    /** 상품분류명 */
    private String prodClassNm;

    /** 상위상품분류코드 */
    private String pProdClassCd;

    /** Child Items */
    private List<HqClsVO> items;
}
