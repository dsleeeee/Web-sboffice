package kr.co.solbipos.base.prod.basicSideMenu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelClassVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelGroupVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BasicSideMenuMapper.java
 * @Description : 기초관리 > 상품관리 > (기준)사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.08.07
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface BasicSideMenuMapper {

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 목록 조회 */
    List<DefaultMap<String>> getMenuGrpList(BasicSideMenuSelGroupVO basicSideMenuSelGroupVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 그룹코드 생성 */
    String getMenuGrpCode(BasicSideMenuSelGroupVO basicSideMenuSelGroupVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 생성 */
    int insertMenuGrpList(BasicSideMenuSelGroupVO basicSideMenuSelGroupVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 수정 */
    int updateMenuGrpList(BasicSideMenuSelGroupVO basicSideMenuSelGroupVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 삭제 */
    int deleteMenuGrpList(BasicSideMenuSelGroupVO basicSideMenuSelGroupVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 목록 조회 */
    List<DefaultMap<String>> getMenuClassList(BasicSideMenuSelClassVO basicSideMenuSelClassVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류코드 생성 */
    String getMenuClassCode(BasicSideMenuSelClassVO basicSideMenuSelClassVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 생성 */
    int insertMenuClassList(BasicSideMenuSelClassVO basicSideMenuSelClassVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 수정 */
    int updateMenuClassList(BasicSideMenuSelClassVO basicSideMenuSelClassVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 삭제 */
    int deleteMenuClassList(BasicSideMenuSelClassVO basicSideMenuSelClassVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 추가팝업 상품리스트 조회 */
   List<DefaultMap<String>> getProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO);

   /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 목록 조회 */
   List<DefaultMap<String>> getMenuProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO);

   /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 생성 */
   int insertMenuProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 수정 */
   int updateMenuProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 삭제 */
   int deleteMenuProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO);



}
