package kr.co.solbipos.membr.reportKwu.instructorMembr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.membr.reportKwu.instructorMembr.service.InstructorMembrService;
import kr.co.solbipos.membr.reportKwu.instructorMembr.service.InstructorMembrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : InstructorMembrServiceImpl.java
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

@Service("instructorMembrService")
public class InstructorMembrServiceImpl implements InstructorMembrService {

    private final InstructorMembrMapper instructorMembrMapper;

    @Autowired
    public InstructorMembrServiceImpl(InstructorMembrMapper instructorMembrMapper) {
        this.instructorMembrMapper = instructorMembrMapper;
    }

    /** 본사 명칭 콤보조회 */
    @Override
    public List<DefaultMap<String>> selectHqCodeList(InstructorMembrVO instructorMembrVO, SessionInfoVO sessionInfoVO) {

        instructorMembrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return instructorMembrMapper.selectHqCodeList(instructorMembrVO);
    }

    /** 강사별회원관리내역 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getInstructorMembrList(InstructorMembrVO instructorMembrVO, SessionInfoVO sessionInfoVO) {

        instructorMembrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        return instructorMembrMapper.getInstructorMembrList(instructorMembrVO);
    }

}
