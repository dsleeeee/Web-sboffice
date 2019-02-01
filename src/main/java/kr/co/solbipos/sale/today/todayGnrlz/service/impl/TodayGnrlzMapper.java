package kr.co.solbipos.sale.today.todayGnrlz.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.today.todayGnrlz.service.TodayGnrlzVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TodayGnrlzMapper {
    /** 당일매출종합현황 - 매출종합 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzList(TodayGnrlzVO todayGnrlzVO);

    /** 당일매출종합현황 - 결제수단별 매출 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzPayList(TodayGnrlzVO todayGnrlzVO);

    /** 당일매출종합현황 - 회원 Point 적립/사용 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzMemberList(TodayGnrlzVO todayGnrlzVO);

    /** 당일매출종합현황 - 상품별 매출현황 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzProdList(TodayGnrlzVO todayGnrlzVO);

}
