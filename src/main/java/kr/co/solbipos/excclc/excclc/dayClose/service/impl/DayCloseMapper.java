package kr.co.solbipos.excclc.excclc.dayClose.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.dayClose.service.DayCloseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayCloseMapper.java
 * @Description : 광운대 > 광운대일마감 > 광운대일마감
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface DayCloseMapper {

    /** 조회 */
    List<DefaultMap<String>> getDayCloseList(DayCloseVO dayCloseVO);
    /** 마감데이터 조회 */
    DefaultMap<String> getDayCloseDtl(DayCloseVO dayCloseVO);
    /** 마감 */
    int saveClose(DayCloseVO dayCloseVO);
    /** 마감취소 */
    int closeCancel(DayCloseVO dayCloseVO);

}
