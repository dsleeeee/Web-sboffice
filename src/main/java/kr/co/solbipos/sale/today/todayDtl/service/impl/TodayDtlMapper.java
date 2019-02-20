package kr.co.solbipos.sale.today.todayDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TodayDtlMapper {
    /** 당일매출상세현황 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(TodayDtlVO todayDtlVO);

    /** 당일매출상세현황 - 매장 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(TodayDtlVO todayDtlVO);

    /** 당일매출상세현황 - 할인 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(TodayDtlVO todayDtlVO);

    /** 당일매출상세현황 - 객수 컬럼 리스트 조회 */
    List<DefaultMap<String>> getGuestColList(TodayDtlVO todayDtlVO);

    /** 당일매출상세현황 - 매출종합 리스트 조회 */
    List<DefaultMap<String>> getTodayDtlList(TodayDtlVO todayDtlVO);

    /** 당일매출상세현황 - 매출상세 리스트 조회 */
    List<DefaultMap<String>> getTodayDtlDetailList(TodayDtlVO todayDtlVO);

}
