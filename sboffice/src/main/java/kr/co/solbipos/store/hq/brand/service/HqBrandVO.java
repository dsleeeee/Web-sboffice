package kr.co.solbipos.store.hq.brand.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : HqBrandVO.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqBrandVO extends CmmVO {

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
