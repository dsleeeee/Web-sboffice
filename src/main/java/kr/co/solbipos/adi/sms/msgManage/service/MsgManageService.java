package kr.co.solbipos.adi.sms.msgManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MsgManageService.java
 * @Description : 부가서비스 > SMS관리 > 메세지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MsgManageService {

    /** 메세지관리 - 그룹 조회 */
    List<DefaultMap<Object>> getMsgManageList(MsgManageVO msgManageVO, SessionInfoVO sessionInfoVO);

    /** 메세지관리 - 그룹 저장 */
    int getMsgManageSave(MsgManageVO[] msgManageVOs, SessionInfoVO sessionInfoVO);

    /** 메세지관리 - 메세지서식 조회 */
    List<DefaultMap<Object>> getMsgManageDtlList(MsgManageVO msgManageVO, SessionInfoVO sessionInfoVO);

    /** 메세지관리 - 메세지서식 저장 */
    int getMsgManageDtlSave(MsgManageVO msgManageVO, SessionInfoVO sessionInfoVO);
}