package kr.co.solbipos.kds.anals.chart.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface KdsService {
    /** 주문 단위 - 일자별 */
    List<DefaultMap<String>> getKdsDay(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 */
    List<DefaultMap<String>> getKdsDayTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 - 차트 */
    List<DefaultMap<String>> getKdsDayTimeChart(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 일별 - 상품별 */
    List<DefaultMap<String>> getKdsDayProd(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 월별 - 상품별 */
    List<DefaultMap<String>> getKdsMonth(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 매장대비 */
    List<DefaultMap<String>> getKdsStore(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 일 - 상품 시간대별 */
    List<DefaultMap<String>> getKdsDayProdTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 일 - 상품 시간대별 차트 */
    List<DefaultMap<String>> getKdsDayProdTimeChart(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    /** 일별-SERVICE TIME 구간별 영수증 수 */
    List<DefaultMap<String>> getKdsServiceTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO);
}
