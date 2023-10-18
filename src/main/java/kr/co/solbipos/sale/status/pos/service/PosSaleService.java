package kr.co.solbipos.sale.status.pos.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosSaleService {

    /** 포스별매출일자별 - 매장 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 리스트 조회(엑셀) */
    List<DefaultMap<String>> getPosDayExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 매장 포스 리스트 조회 */
    List<DefaultMap<String>> getPosNmList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출요일별 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayOfWeekList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출요일별 - 차트 조회 */
    List<DefaultMap<String>> getPosDayOfWeekChartList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 리스트 조회 */
    List<DefaultMap<String>> getPosMonthList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 엑셀리스트 조회 */
    List<DefaultMap<String>> getPosMonthExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 조회 */
    List<DefaultMap<String>> getPosProdList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosProdExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별탭 - 조회 */
    List<DefaultMap<String>> getPosDayPeriodList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosDayPeriodExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별탭 - 상세 조회 */
    List<DefaultMap<String>> getPosDayPeriodDtlList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별탭 - 상세 엑셀 조회 */
    List<DefaultMap<String>> getPosDayPeriodDtlExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

    /** 시간대별별 - 리스트 조회 */
    List<DefaultMap<String>> getPosHourList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO);

}
