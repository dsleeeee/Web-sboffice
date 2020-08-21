package kr.co.solbipos.membr.info.chgBatch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.chgBatch.service.ChgBatchVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChgBatchMapper {
    List<DefaultMap<String>> getMembrChgBatchList(ChgBatchVO chgBatchVO);

    List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

    List<DefaultMap<String>> getMemberChgBatchList(ChgBatchVO chgBatchVO);

    int updateChgBatchInfo(ChgBatchVO chgBatchVO);
}
