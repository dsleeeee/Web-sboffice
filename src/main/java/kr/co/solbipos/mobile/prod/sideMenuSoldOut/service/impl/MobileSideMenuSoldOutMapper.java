package kr.co.solbipos.mobile.prod.sideMenuSoldOut.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.prod.sideMenuSoldOut.service.MobileSideMenuSoldOutVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileSideMenuSoldOutMapper.java
 * @Description : 상품관리 > 품절관리(선택메뉴)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MobileSideMenuSoldOutMapper {

    /** 선택그룹 조회 */
    List<DefaultMap<String>> getMobileSideMenuGrpList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO);

    /** 선택분류 조회 */
    List<DefaultMap<String>> getMobileSideMenuClassList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO);

    /** 선택상품 조회 */
    List<DefaultMap<String>> getMobileSideMenuProdList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO);

    /** 저장 */
    int getMobileSideMenuSoldOutSave(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO);

}
