package kr.co.solbipos.application.com.bkmk.service.impl;

import org.apache.ibatis.annotations.Mapper;
import kr.co.solbipos.application.com.bkmk.service.BkmkVO;

/**
* @Class Name : BkmkMapper.java
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
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
public interface BkmkMapper {

    /** 즐겨찾기 메뉴 등록 */
    int insertBkmk( BkmkVO bkmkVO );

    /** 즐겨찾기 메뉴 삭제 */
    int deleteBkmk( BkmkVO bkmkVO );

}
