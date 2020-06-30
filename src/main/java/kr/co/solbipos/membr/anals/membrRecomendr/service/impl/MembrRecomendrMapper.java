package kr.co.solbipos.membr.anals.membrRecomendr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.membrRecomendr.service.MembrRecomendrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MembrRecomendrMapper {
    List<DefaultMap<Object>> getMembrPointList(MembrRecomendrVO membrRecomendrVO);
}
