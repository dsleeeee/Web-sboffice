package kr.co.solbipos.dlvr.anals.dlvrInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DlvrInfoMapper {
  public List<DefaultMap<Object>> getDlvrInfoList(DlvrInfoVO dlvrInfoVO);
}
