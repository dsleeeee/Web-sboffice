package kr.co.solbipos.application.com.fixing.service;

/**
* @Class Name : FixingService.java
* @Description : 어플리케이션 > 공통 > 고정메뉴
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
public interface FixingService {

    /** 고정메뉴 관리 */
    int saveFixing( FixingVO bkmkVO, String userId );

}
