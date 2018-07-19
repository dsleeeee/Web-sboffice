package kr.co.solbipos.sys.etc.vancard.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : VanCardVO.java
 * @Description : 시스템관리 > VAN/CARD사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VanCardVO extends CmmVO {
    
    private static final long serialVersionUID = -8172035887190498958L;
    
    /** 밴사코드 */
    private String vanCd;
    
    /** 밴사카드사코드 */
    private String vanCardcoCd;
    
    /** 밴사카드사명 */
    private String vanCardcoNm;
    
    /** 카드사코드 */
    private String cardcoCd;

}
