package kr.co.solbipos.kds.anals.chart.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kds.anals.chart.service.KdsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KdsMapper {
    List<DefaultMap<String>> getKdsDay(KdsVO kdsVO);

    List<DefaultMap<String>> getKdsDayTime(KdsVO kdsVO);

    List<DefaultMap<String>> getKdsDayTimeChart(KdsVO kdsVO);
}
