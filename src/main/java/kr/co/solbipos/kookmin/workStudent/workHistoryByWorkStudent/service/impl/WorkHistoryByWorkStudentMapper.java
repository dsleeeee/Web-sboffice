package kr.co.solbipos.kookmin.workStudent.workHistoryByWorkStudent.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.workStudent.workHistoryByWorkStudent.service.WorkHistoryByWorkStudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : WorkHistoryByWorkStudentMapper.java
 * @Description : 국민대 > 근로학생관리 > 근로학생별 근무내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.19  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface WorkHistoryByWorkStudentMapper {

    /** 근로학생별 근무내역 조회 */
    List<DefaultMap<Object>> getWorkHistoryByWorkStudentList(WorkHistoryByWorkStudentVO workStudentByWorkHistoryVO);
}
