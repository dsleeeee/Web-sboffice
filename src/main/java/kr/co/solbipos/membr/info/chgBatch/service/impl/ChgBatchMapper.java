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

    /** 회원등급 리스트 조회 */
    List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

    /** 회원정보 리스트 조회 */
    List<DefaultMap<String>> getMemberChgBatchList(ChgBatchVO chgBatchVO);

    /** 등급포인트 적립 저장 */
    int updateChgBatchInfo(ChgBatchVO chgBatchVO);
}
