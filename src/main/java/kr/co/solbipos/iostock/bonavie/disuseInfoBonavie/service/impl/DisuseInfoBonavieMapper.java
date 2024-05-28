package kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service.DisuseInfoBonavieVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DisuseInfoBonavieServiceImpl.java
 * @Description : 수불관리 > 보나비 > 폐기내역조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.05.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DisuseInfoBonavieMapper {

    /** 폐기내역조회 - 조회 */
    List<DefaultMap<Object>> getDisuseInfoBonavieList(DisuseInfoBonavieVO disuseInfoBonavieVO);

    /** 폐기내역조회 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDisuseInfoBonavieExcelList(DisuseInfoBonavieVO disuseInfoBonavieVO);

}
