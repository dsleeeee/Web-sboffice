package kr.co.solbipos.kds.anals.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kds.anals.day.service.KdsDayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KdsDayMapper {
    List<DefaultMap<String>> getKdsDay(KdsDayVO kdsDayVO);
}
