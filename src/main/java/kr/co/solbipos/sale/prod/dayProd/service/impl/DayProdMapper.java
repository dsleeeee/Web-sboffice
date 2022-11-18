package kr.co.solbipos.sale.prod.dayProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayProdMapper.java
 * @Description : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DayProdMapper {

    /** 상품별 매출 순위 */
    List<DefaultMap<Object>> getDayProdList(DayProdVO dayProdVO);
    List<DefaultMap<Object>> getDayProdExcelList(DayProdVO dayProdVO);

    /** 사용자별 브랜드 사용 조회 */
    String getUserBrandCdList(DayProdVO dayProdVO);

    /** 사용자별 브랜드 조회(콤보박스용) */
    List<DefaultMap<Object>> getUserBrandComboList(DayProdVO dayProdVO);

    /** 사용자별 코드별 공통코드 조회 */
    String getUserHqNmcodeCdList(DayProdVO dayProdVO);

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    List<DefaultMap<Object>> getUserHqNmcodeComboList(DayProdVO dayProdVO);

    /** 사용자별 지사 조회 */
    String getUserBranchCdList(DayProdVO dayProdVO);

    /** 사용자별 지사 콤보박스 조회 */
    List<DefaultMap<Object>> getUserBranchComboList(DayProdVO dayProdVO);
}