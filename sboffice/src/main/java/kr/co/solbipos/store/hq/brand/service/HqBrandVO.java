package kr.co.solbipos.store.hq.brand.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 본사정보 > 브랜드정보관리
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqBrandVO extends CmmVO {

    
    private static final long serialVersionUID = -2091942215914948161L;

    /** 본사코드 */
    private String hqOfficeCd;
    
    /** 본사명 */
    private String hqOfficeNm;
    
    /** 브랜드코드 */
    private String hqBrandCd;
    
    /** 브랜드명 */
    private String hqBrandNm;
    
    /** 사용여부 */
    private UseYn UseYn;
    
}
