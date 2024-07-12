package kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : RtnInstockConfmStoreService.java
 * @Description : 수불관리 > 반품관리 > 반품본사입고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.09  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.07.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface RtnInstockConfmStoreService {

    /** 반품본사입고현황 - 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmStoreList(RtnInstockConfmStoreVO rtnInstockConfmStoreVO, SessionInfoVO sessionInfoVO);

    /** 반품본사입고현황 - 전표 상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnInstockConfmStoreVO rtnInstockConfmStoreVO, SessionInfoVO sessionInfoVO);

    /** 반품본사입고현황 - 전표 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmStoreDtlList(RtnInstockConfmStoreVO rtnInstockConfmStoreVO, SessionInfoVO sessionInfoVO);

}
