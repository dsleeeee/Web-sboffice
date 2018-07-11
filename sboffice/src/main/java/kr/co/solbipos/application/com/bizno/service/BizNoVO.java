package kr.co.solbipos.application.com.bizno.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @Class Name : BizNoVO.java
* @Description : 어플리케이션 > 공통 > 사업자번호 유효성검사
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  노현수      최초생성
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
public class BizNoVO extends CmmVO {

    private static final long serialVersionUID = 7527886695969858538L;

    /** 사업자번호 */
    private String bizNo;

}
