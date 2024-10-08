package kr.co.solbipos.iostock.loan.virtualAccount.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : VirtualAccountService.java
 * @Description : 수불관리 > 주문관리 > 가상계좌내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VirtualAccountService {

    /** 가상계좌내역 - 조회 */
    List<DefaultMap<Object>> getVirtualAccountList(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO);

    /** 가상계좌 키값 리스트 조회 */
    List<DefaultMap<String>> getVirtualAccountKeyColList(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO);

    /** 가상계좌 API URL 조회 */
    String getVirtualAccountApiTargetUrl(VirtualAccountVO virtualAccountVO);

    /** 가상계좌 등록순번 조회(자동채번) */
    String getVirtualAccountReqSeq(VirtualAccountVO virtualAccountVO);

    /** 가상계좌 입금 생성 팝업 - 가상계좌 발급 저장 Insert */
    int getVirtualAccountRegisterSaveInsert(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO);

    /** 가상계좌 입금 생성 팝업 - 가상계좌 발급 저장 update */
    int getVirtualAccountRegisterSaveUpdate(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO);
}