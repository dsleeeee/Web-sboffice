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

    /** 금전현황 리스트 조회 */
    List<DefaultMap<String>> selectStatus(StatusVO statusVO, SessionInfoVO sessionInfoVO);

    /** 계정구분에 따른 계정 코드 조회(입금/출금 계정, 매장권한에서만 사용) */
    List<DefaultMap<String>> selectAccntList(StatusVO statusVO, SessionInfoVO sessionInfoVO);

    /** 금전현황 리스트 조회 Excel 다운로드 */
    List<DefaultMap<String>> monyStatusExcelList(StatusVO statusVO, SessionInfoVO sessionInfoVO);

}
