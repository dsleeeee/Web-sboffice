package kr.co.solbipos.common.popup.selectSdselClass.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SelectSdselClassService.java
 * @Description : (공통) 선택분류 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SelectSdselClassService {

    /** 선택분류 공통 - 선택분류 리스트 조회 */
    List<DefaultMap<String>> getSelectSdselClassList(SelectSdselClassVO selectSdselClassVO, SessionInfoVO sessionInfoVO);
}