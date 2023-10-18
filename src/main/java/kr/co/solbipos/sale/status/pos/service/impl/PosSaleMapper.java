package kr.co.solbipos.sale.status.pos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.service.PosSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface PosSaleMapper {

    /** 포스별매출 일자별 탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosSaleVO posSaleVO);

    /** 포스별매출 일자별 탭 - 매장 및 포스 리스트 조회(엑셀) */
    List<DefaultMap<String>> getPosDayExcelList(PosSaleVO posSaleVO);

    /** 포스별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayList(PosSaleVO posSaleVO);

    /** 포스별매출 - 매장 코너 리스트 조회 */
    List<DefaultMap<String>> getPosNmList(PosSaleVO posSaleVO);

    /** 포스별매출 요일별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayOfWeekList(PosSaleVO posSaleVO);

    /** 포스별매출 요일별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayOfWeekChartList(PosSaleVO posSaleVO);

    /** 포스별매출 월별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosMonthList(PosSaleVO posSaleVO);

    /** 포스별매출 월별 탭 - 엑셀리스트 조회 */
    List<DefaultMap<String>> getPosMonthExcelList(PosSaleVO posSaleVO);

    /** 상품별탭 - 조회 */
    List<DefaultMap<String>> getPosProdList(PosSaleVO posSaleVO);

    /** 상품별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosProdExcelList(PosSaleVO posSaleVO);

    /** 설정기간별탭 - 조회 */
    List<DefaultMap<String>> getPosDayPeriodList(PosSaleVO posSaleVO);

    /** 설정기간별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosDayPeriodExcelList(PosSaleVO posSaleVO);

    /** 설정기간별탭 - 상세 조회 */
    List<DefaultMap<String>> getPosDayPeriodDtlList(PosSaleVO posSaleVO);

    /** 설정기간별탭 - 상세 엑셀 조회 */
    List<DefaultMap<String>> getPosDayPeriodDtlExcelList(PosSaleVO posSaleVO);

    /** 시간대별별 - 리스트 조회 */
    List<DefaultMap<String>> getPosHourList(PosSaleVO posSaleVO);
}