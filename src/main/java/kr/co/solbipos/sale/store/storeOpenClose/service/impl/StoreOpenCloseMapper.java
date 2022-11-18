package kr.co.solbipos.sale.store.storeOpenClose.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeOpenClose.service.StoreOpenCloseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreOpenCloseMapper.java
 * @Description : 매출관리 > 배달현황 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.11.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreOpenCloseMapper {

    /** 매장 오픈/마감 현황 - 일별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseDayList(StoreOpenCloseVO storeOpenCloseVO);

    /** 매장 오픈/마감 현황 - 일별 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseDayDtlList(StoreOpenCloseVO storeOpenCloseVO);  // 개점/마감
    List<DefaultMap<String>> getStoreOpenCloseDayDtlNoneList(StoreOpenCloseVO storeOpenCloseVO);// 미개점

    /** 매장 오픈/마감 현황 - 월별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseMonthList(StoreOpenCloseVO storeOpenCloseVO);
    
    /** 매장 오픈/마감 현황 - 월별 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseMonthDtlList(StoreOpenCloseVO storeOpenCloseVO);

}
