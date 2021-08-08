package kr.co.solbipos.kds.anals.chart.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kds.anals.chart.service.KdsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KdsMapper {
    /** 주문 단위 - 일자별 */
    List<DefaultMap<String>> getKdsDay(KdsVO kdsVO);

    /** 시간대별 */
    List<DefaultMap<String>> getKdsDayTime(KdsVO kdsVO);

    /** 시간대별 - 차트 */
    List<DefaultMap<String>> getKdsDayTimeChart(KdsVO kdsVO);

    /** 일별 - 상품별 */
    List<DefaultMap<String>> getKdsDayProd(KdsVO kdsVO);

    /** 월별 - 상품별 */
    List<DefaultMap<String>> getKdsMonth(KdsVO kdsVO);

    /** 매장대비 */
    List<DefaultMap<String>> getKdsStore(KdsVO kdsVO);

    /** 매장대비 */
    // List<DefaultMap<String>> getKdsStoreProd(KdsVO kdsVO);

    /** 일 - 상품 시간대별 */
    List<DefaultMap<String>> getKdsDayProdTime(KdsVO kdsVO);

    /** 일 - 상품 시간대별 차트 */
    List<DefaultMap<String>> getKdsDayProdTimeChart(KdsVO kdsVO);

    /** 일별-SERVICE TIME 구간별 영수증 수 */
    List<DefaultMap<String>> getKdsServiceTime(KdsVO kdsVO);
}
