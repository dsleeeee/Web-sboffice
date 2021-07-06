package kr.co.solbipos.base.store.guestManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : GuestManageService.java
 * @Description : 기초관리 > 매장관리 > 객층관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.07.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface GuestManageService {

    /** 객층관리 - 조회 */
    List<DefaultMap<Object>> getGuestManageList(GuestManageVO guestManageVO, SessionInfoVO sessionInfoVO);

    /** 객층관리 - 저장 */
    int getGuestManageSave(GuestManageVO[] guestManageVOs, SessionInfoVO sessionInfoVO);
}