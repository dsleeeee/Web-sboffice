package kr.co.solbipos.sys.bill.kind.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : KindVO.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 종류
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KindVO extends CmmVO {
    
    private static final long serialVersionUID = 1785828866965883036L;
    
    /** 출력물분류코드 */
    private String prtClassCd;
    
    /** 출력물분류명 */
    private String prtClassNm;
    
    /** 일반사용여부 */
    private String general;
    
    /** 외식사용여부 */
    private String food;
    
    /** 출력물코드 */
    private String prtCd;
    
    /** 표기순번 */
    private String dispSeq;
    
    /** 출력물명 */
    private String prtNm;
}
