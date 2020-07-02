package kr.co.solbipos.membr.info.dlvr.service.Impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.mony.drawhist.service.DrawHistVO;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
import kr.co.solbipos.membr.info.point.service.MemberPointVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DlvrMapper {
  List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO);
}


