package kr.co.solbipos.sale.day.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayMapper {
    /** 일자별 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(DayVO dayVO);
    /** 일자별 - 결제수단 컬럼 리스트 조회(현금영수증 포함) */
    List<DefaultMap<String>> getPayColAddList(DayVO dayVO);

    /** 일자별 - 할인 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(DayVO dayVO);

    /** 코너별 탭 - 코너 컬럼 리스트 조회 */
    List<DefaultMap<String>> getCornerColList(DayVO dayVO);

    /** 외식테이블 탭 - 외식테이블 컬럼 리스트 조회 */
    List<DefaultMap<String>> getTableColList(DayVO dayVO);

    /** 포스별 탭 - 포스 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPosColList(DayVO dayVO);


    /** 일자별 - 일별종합 리스트 조회 */
    List<DefaultMap<String>> getDayTotalList(DayVO dayVO);
    
    /** 일자(현금) 종합 - 일별종합 리스트 조회 */
    List<DefaultMap<String>> getDayCashTotalList(DayVO dayVO);

    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    List<DefaultMap<String>> getDayStoreDtlList(DayVO dayVO);

    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    List<DefaultMap<String>> getDayProdDtlList(DayVO dayVO);

    /** 일자별(일별종합 탭) - 일자 매출현황 리스트 조회 */
    List<DefaultMap<String>> getDayDtlList(DayVO dayVO);

    /** 일자별(일별종합 탭) - 일자 매출현황 리스트 조회 */
    List<DefaultMap<String>> getDaySumAvgList(DayVO dayVO);

    /** 일자별(일별종합 탭) - 일자 매장별 할인현황 리스트 조회 */
    List<DefaultMap<String>> getDayStoreDcList(DayVO dayVO);

    /** 일자별(할인구분별 탭) - 할인구분 리스트 조회 */
    List<DefaultMap<String>> getDayDcList(DayVO dayVO);

    /** 일자별(과면세별 탭) - 과면세 리스트 조회 */
    List<DefaultMap<String>> getDayTaxList(DayVO dayVO);

    /** 일자별(시간대별 탭) - 시간대별 리스트 조회 */
    List<DefaultMap<String>> getDayTimeList(DayVO dayVO);

    /** 일자별(상품분류 탭) - 상품분류 MAX(depth) 값 가져오기 */
    int getDayProdClassMaxLevel(DayVO dayVO);

    /** 일자별(상품분류 탭) - 분류레벨에 따른 상품분류 가져오기 */
    List<DefaultMap<String>> getDayProdClassLevel(DayVO dayVO);

    /** 일자별(상품분류 탭) - 상품분류별 리스트 조회 */
    List<DefaultMap<String>> getDayProdClassList(DayVO dayVO);

    /** 코너별 - 코너별 매출조회 */
    List<DefaultMap<Object>> getDayCornerList(DayVO dayVO);

    /** 외식테이블별 - 외식테이블별매출조회 */
    List<DefaultMap<Object>> getDayTableList(DayVO dayVO);

    /** 상품매출 상세내역 팝업 - 상품매출 상세내역 조회 */
    List<DefaultMap<Object>> getDayProdSaleDtlList(DayVO dayVO);

    /** 포스별 - 포스별매출조회 */
    List<DefaultMap<Object>> getDayPosList(DayVO dayVO);

    /** 사원카드별탭 - 사원카드별 매출조회 */
    List<DefaultMap<Object>> getDayEmpCardList(DayVO dayVO);
}
