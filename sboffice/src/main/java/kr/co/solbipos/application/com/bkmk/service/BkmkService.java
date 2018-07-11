package kr.co.solbipos.application.com.bkmk.service;

/**
* @Class Name : BkmkService.java
* @Description : 어플리케이션 > 공통 > 즐겨찾기
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
public interface BkmkService {

    /** 즐겨찾기 메뉴 관리 */
    int saveBkmk( BkmkVO bkmkVO, String userId );

}
