package kr.co.solbipos.excclc.excclc.depositDdc.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;

import java.util.List;

/**
 * @Class Name : DepositDdcService.java
 * @Description : 수불관리 > 수주관리 > 입금/공제관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.20  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.04.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface DepositDdcService {

    /** 매장별집계 탭 - 매장별집계 리스트 조회 */
    List<DefaultMap<String>> getStoreTotalList(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO);

    /** 매장별집계 탭 - 상세내역 리스트 조회 */
    List<DefaultMap<String>> getStoreTotalDtlList(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO);

    /** 매장별집계 탭 - 입금/기타공제 계정 콤보박스 데이터 조회 */
    List<DefaultMap<String>> getMoneyFgCombo(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO);

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 저장 */
    int saveDepositDdc(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO);

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 조회 */
    DefaultMap<String> getDepositDdc(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO);

}
