package kr.co.solbipos.membr.info.dlvr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DlvrMapper {

  List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO);

  List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO);

  int deleteDlvr(DlvrVO dlvrVO);

  int deleteDlvrTel(DlvrVO dlvrVO);
}