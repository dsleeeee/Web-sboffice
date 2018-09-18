package kr.co.solbipos.adi.mony.status.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
* @Class Name : DrawHistService.java
* @Description : 부가서비스 > 금전처리 > 금전현황
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.09.09  김태수      최초생성
*
* @author NHN한국사이버결제 KCP 김태수
* @since 2018.09.09
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface StatusService {

    // 본사(HQ) 조회
    List<StatusVO> selectStatus(StatusVO statusVO, SessionInfoVO sessionInfoVO);

    // 입출금(accntFg)에 따른 계정 조회
    List<DefaultMap<String>> selectAccntList(StatusVO statusVO);

}
