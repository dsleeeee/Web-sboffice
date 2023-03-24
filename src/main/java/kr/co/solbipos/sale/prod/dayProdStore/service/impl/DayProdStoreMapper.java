package kr.co.solbipos.sale.prod.dayProdStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayProdStoreMapper.java
 * @Description : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DayProdStoreMapper {

    /** 조회 */
    List<DefaultMap<Object>> getDayProdStoreList(DayProdStoreVO dayProdStoreVO);
    List<DefaultMap<Object>> getDayProdStoreExcelList(DayProdStoreVO dayProdStoreVO);

    /** 사용자별 브랜드 사용 조회 */
    String getUserBrandCdList(DayProdStoreVO dayProdStoreVO);

    /** 사용자별 브랜드 조회(콤보박스용) */
    List<DefaultMap<Object>> getUserBrandComboList(DayProdStoreVO dayProdStoreVO);

    /** 사용자별 코드별 공통코드 조회 */
    String getUserHqNmcodeCdList(DayProdStoreVO dayProdStoreVO);

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    List<DefaultMap<Object>> getUserHqNmcodeComboList(DayProdStoreVO dayProdStoreVO);

    /** 사용자별 지사 조회 */
    String getUserBranchCdList(DayProdStoreVO dayProdStoreVO);

    /** 사용자별 지사 콤보박스 조회 */
    List<DefaultMap<Object>> getUserBranchComboList(DayProdStoreVO dayProdStoreVO);
}