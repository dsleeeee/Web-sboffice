package kr.co.solbipos.dlvr.anals.dayDlvr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.anals.dayDlvr.service.DayDlvrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayDlvrMapper {
  List<DefaultMap<Object>> getDayDlvrList(DayDlvrVO dayDlvrVO);
}
