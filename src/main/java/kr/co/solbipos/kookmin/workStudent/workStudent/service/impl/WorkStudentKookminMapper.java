package kr.co.solbipos.kookmin.workStudent.workStudent.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.workStudent.workStudent.service.WorkStudentKookminVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : WorkStudentKookminMapper.java
 * @Description : 국민대 > 근로학생관리 > 근로학생관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.31  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface WorkStudentKookminMapper {
    List<DefaultMap<Object>> getWorkStudentKookminList(WorkStudentKookminVO workStudentKookminVO);

    int insertWorkStudent(WorkStudentKookminVO workStudentKookminVO);

    int updateWorkStudent(WorkStudentKookminVO workStudentKookminVO);

    int deleteWorkStudent(WorkStudentKookminVO workStudentKookminVO);

    int getChkStudentNo(WorkStudentKookminVO workStudentKookminVO);
}
