package kr.co.solbipos.application.com.bizno.service;

/**
* @Class Name : BizNoService.java
* @Description : 어플리케이션 > 공통 > 사업자번호 유효성검사
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2015.06.01  노현수      최초생성
*
* @author 솔비포스 차세대개발실 노현수
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface BizNoService {

    /** 사업자번호 유효성검사 */
    boolean bizNoVerify(BizNoVO bizNoVO);

}
