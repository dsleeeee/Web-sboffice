package kr.co.solbipos.base.prod.sidemenu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuAttrCdVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuAttrClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SideMenuMapper.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SideMenuMapper {

    /** 사이드메뉴-속성탭-속성분류 목록 조회 */
    List<DefaultMap<String>> getAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 생성 */
    int insertAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 수정 */
    int updateAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 삭제 */
    int deleteAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성 목록 조회 */
    List<DefaultMap<String>> getAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 생성 */
    int insertAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 수정 */
    int updateAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 삭제 */
    int deleteAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

}
