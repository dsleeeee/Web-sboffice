package kr.co.solbipos.base.prod.prodPrintYn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prodPrintYn.service.ProdPrintYnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdPrintYnMapper.java
 * @Description : 기초관리 > 상품관리2 > 출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdPrintYnMapper {

    /** 옵션관리 탭 - 조회 */
    List<DefaultMap<Object>> getProdOptionPrintYnList(ProdPrintYnVO prodPrintYnVO);

    /** 옵션관리 탭 - 저장 update */
    int getProdOptionPrintYnSaveUpdate(ProdPrintYnVO prodPrintYnVO);

    /** 사이드메뉴관리 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuProdPrintYnList(ProdPrintYnVO prodPrintYnVO);

    /** 사이드메뉴관리 탭 - 저장 update */
    int getSideMenuProdPrintYnSaveUpdate(ProdPrintYnVO prodPrintYnVO);

    /** 사이드메뉴관리 탭 - 매장 저장 update */
    int getStoreSideMenuProdPrintYnSaveUpdate(ProdPrintYnVO prodPrintYnVO);
}
