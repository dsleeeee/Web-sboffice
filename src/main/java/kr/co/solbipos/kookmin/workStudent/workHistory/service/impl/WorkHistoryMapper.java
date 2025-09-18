package kr.co.solbipos.kookmin.workStudent.workHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.workStudent.workHistory.service.WorkHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkHistoryMapper {

    /** 근무내역 조회 */
    List<DefaultMap<Object>> getWorkHistoryList(WorkHistoryVO workHistoryVO);

    /** 근무내역 저장 */
    int mergeWorkHistory(WorkHistoryVO workHistoryVO);
}
