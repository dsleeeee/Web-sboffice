package kr.co.solbipos.kookmin.base.termInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name  : TermInfoService.java
 * @Description : 국민대 > 기초관리 > 학기정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface TermInfoService {

    /** 학기정보 조회 */
    List<DefaultMap<Object>> getTermInfoList(TermInfoVO termInfoVO, SessionInfoVO sessionInfoVO);

    /** 학기정보 저장 */
    int saveTermInfo(TermInfoVO[] termInfoVOS, SessionInfoVO sessionInfoVO);
}
