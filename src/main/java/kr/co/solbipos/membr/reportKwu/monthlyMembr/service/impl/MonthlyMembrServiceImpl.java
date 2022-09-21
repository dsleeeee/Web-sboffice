package kr.co.solbipos.membr.reportKwu.monthlyMembr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.reportKwu.instructorMembr.service.InstructorMembrVO;
import kr.co.solbipos.membr.reportKwu.monthlyMembr.service.MonthlyMembrService;
import kr.co.solbipos.membr.reportKwu.monthlyMembr.service.MonthlyMembrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : MonthlyMembrServiceImpl.java
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

@Service("momthlyMembrService")
public class MonthlyMembrServiceImpl implements MonthlyMembrService {

    private final MonthlyMembrMapper monthlyMembrMapper;

    @Autowired
    public MonthlyMembrServiceImpl(MonthlyMembrMapper monthlyMembrMapper) {
        this.monthlyMembrMapper = monthlyMembrMapper;
    }

    /** 본사 명칭 콤보조회 */
    @Override
    public List<DefaultMap<String>> selectHqCodeList(MonthlyMembrVO monthlyMembrVO, SessionInfoVO sessionInfoVO) {

        monthlyMembrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return monthlyMembrMapper.selectHqCodeList(monthlyMembrVO);
    }

    /** 월별회원등록현황 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMonthlyMembrList(MonthlyMembrVO monthlyMembrVO, SessionInfoVO sessionInfoVO) {

        monthlyMembrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        return monthlyMembrMapper.getMonthlyMembrList(monthlyMembrVO);
    }

}
