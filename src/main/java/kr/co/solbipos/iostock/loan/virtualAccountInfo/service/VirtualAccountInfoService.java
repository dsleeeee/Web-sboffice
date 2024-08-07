package kr.co.solbipos.iostock.loan.virtualAccountInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : VirtualAccountInfoService.java
 * @Description : 수불관리 > 주문관리 > 가상계좌-기초정보등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VirtualAccountInfoService {

    /** 가상계좌-기초정보등록 - 조회 */
    List<DefaultMap<Object>> getVirtualAccountInfoList(VirtualAccountInfoVO virtualAccountInfoVO, SessionInfoVO sessionInfoVO);

    /** 가상계좌-기초정보등록 - 저장 */
    int getVirtualAccountInfoSave(VirtualAccountInfoVO[] virtualAccountInfoVOs, SessionInfoVO sessionInfoVO);
}