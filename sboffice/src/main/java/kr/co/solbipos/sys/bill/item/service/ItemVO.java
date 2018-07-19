package kr.co.solbipos.sys.bill.item.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : ItemVO.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력코드 구성
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
public class ItemVO extends CmmVO {
    
    private static final long serialVersionUID = 3145750355286359515L;
    
    /** 출력물코드 */
    private String prtCd;
    
    /** 출력물명 */
    private String prtNm;
    
    /** 예제사용여부 */
    private String samplYn;
    
    /** 예제 */
    private String content;

}
