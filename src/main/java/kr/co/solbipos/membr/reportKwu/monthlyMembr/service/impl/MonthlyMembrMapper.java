package kr.co.solbipos.membr.reportKwu.monthlyMembr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.reportKwu.monthlyMembr.service.MonthlyMembrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthlyMembrMapper.java
 * @Description : 광운대 > 리포트 > 월별회원등록현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MonthlyMembrMapper {

    /** 본사 명칭 콤보조회 */
    List<DefaultMap<String>> selectHqCodeList(MonthlyMembrVO monthlyMembrVO);

    /** 월별회원등록현황 - 리스트 조회 */
    List<DefaultMap<String>> getMonthlyMembrList(MonthlyMembrVO monthlyMembrVO);
}
