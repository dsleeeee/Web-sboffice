package kr.co.solbipos.sale.benson.storeOpenCloseBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.storeOpenCloseBenson.service.StoreOpenCloseBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreOpenCloseBensonMapper.java
 * @Description : (벤슨) 매장분석 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreOpenCloseBensonMapper {

    /** 매장 오픈/마감 현황 - 일별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonDayList(StoreOpenCloseBensonVO storeOpenCloseBensonVO);

    /** 매장 오픈/마감 현황 - 일별 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonDayDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO);  // 개점/마감
    List<DefaultMap<String>> getStoreOpenCloseBensonDayDtlNoneList(StoreOpenCloseBensonVO storeOpenCloseBensonVO);// 미개점

    /** 매장 오픈/마감 현황 - 월별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonMonthList(StoreOpenCloseBensonVO storeOpenCloseBensonVO);

    /** 매장 오픈/마감 현황 - 월별 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonMonthDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO);

}
