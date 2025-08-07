package kr.co.solbipos.store.manage.pwdManageSaleChk.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;

import java.util.List;
/**
 * @Class Name  : PwdManageSaleChkService.java
 * @Description : 기초관리 > 매출조회 비밀번호 관리 > 매출조회 비밀번호 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface PwdManageSaleChkService {

    List<DefaultMap<Object>> getPwdManageSaleChkList(PwdManageSaleChkVO pwdManageSaleChkVO, SessionInfoVO sessionInfoVO);

    PwChgResult getClearPwdSave(PwdManageSaleChkVO pwdManageSaleChkVO, SessionInfoVO sessionInfoVO);

    PwChgResult getModifySalePwd(PwdManageSaleChkVO pwdManageSaleChkVO);
}
