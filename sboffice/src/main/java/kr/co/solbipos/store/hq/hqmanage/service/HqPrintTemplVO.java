package kr.co.solbipos.store.hq.hqmanage.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 본사정보 > 본사정보관리
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqPrintTemplVO extends CmmVO {
    
    /** 본사브랜드코드 */
    private String hqBrandCd;
    
    /** 출력물분류코드 */
    private String prtClassCd;
    
    /** 템플릿코드 */
    private String templtCd;
    
    /** 템플릿명 */
    private String templtNm;
    
    /** 출력물폼 */
    private String prtForm;
}
