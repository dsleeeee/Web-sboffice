package kr.co.solbipos.membr.reportKwu.instructorMembr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.reportKwu.instructorMembr.service.InstructorMembrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : InstructorMembrMapper.java
 * @Description : 광운대 > 리포트 > 강사별회원관리내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.19  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface InstructorMembrMapper {

    /** 본사 명칭 콤보조회 */
    List<DefaultMap<String>> selectHqCodeList(InstructorMembrVO instructorMembrVO);

    /** 강사별회원관리내역 - 리스트 조회 */
    List<DefaultMap<String>> getInstructorMembrList(InstructorMembrVO instructorMembrVO);

}
