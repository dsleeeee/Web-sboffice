package kr.co.solbipos.membr.anals.incln.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.incln.service.InclnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InclnMapper {

  List<DefaultMap<Object>> getInclnList(InclnVO inclnVO);

}