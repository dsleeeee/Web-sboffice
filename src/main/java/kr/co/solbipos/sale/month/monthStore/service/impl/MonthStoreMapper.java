package kr.co.solbipos.sale.month.monthStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.month.monthStore.service.MonthStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthStoreMapper.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황(매장합산)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MonthStoreMapper {

    /** 조회 */
    List<DefaultMap<Object>> getMonthStoreList(MonthStoreVO monthStoreVO);
    List<DefaultMap<Object>> getMonthStoreExcelList(MonthStoreVO monthStoreVO);
}