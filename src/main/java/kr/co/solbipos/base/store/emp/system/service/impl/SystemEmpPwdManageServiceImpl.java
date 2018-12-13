package kr.co.solbipos.base.store.emp.system.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.HttpUtils;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpPwdManageService;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpPwdManageVO;
import kr.co.solbipos.store.manage.pwdmanage.enums.PwdChgFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SystemEmpServiceImpl.java
 * @Description : 시스템관리 > 사원관리 > 비밀번호 임의 변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.12  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("systemEmpPwdManageService")
@Transactional
public class SystemEmpPwdManageServiceImpl implements SystemEmpPwdManageService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SystemEmpPwdManageMapper systemEmpPwdManageMapper;

    /** Constructor Injection */
    @Autowired
    public SystemEmpPwdManageServiceImpl(SystemEmpPwdManageMapper systemEmpPwdManageMapper, MessageService messageService) {
        this.systemEmpPwdManageMapper = systemEmpPwdManageMapper;
        this.messageService = messageService;
    }

    /** 비밀번호 임의변경 대상 조회 */
    @Override
    public List<DefaultMap<String>> getPwdManageList(SystemEmpPwdManageVO systemEmpPwdManageVO, SessionInfoVO sessionInfoVO) {
        // todo 총판이나 대리점으로 접속하면, 조회 목록이 달라져야 함
        return systemEmpPwdManageMapper.getPwdManageList(systemEmpPwdManageVO);
    }

    /** 비밀번호 변경 */
    @Override
    public PwChgResult modifyPwd(SystemEmpPwdManageVO systemEmpPwdManageVO) {

        // 기존 비밀번호 조회
        String oldPassword = systemEmpPwdManageMapper.getOldPassword(systemEmpPwdManageVO);

        String newPassword = "";
        // 기본값 세팅
        // 비밀번호 암호화
        if ( systemEmpPwdManageVO.getPwdChgFg() == PwdChgFg.WEB ) {
            systemEmpPwdManageVO.setLastPwdChgDt(currentDateTimeString());
            newPassword = EncUtil.setEncSHA256(systemEmpPwdManageVO.getUserId() + systemEmpPwdManageVO.getNewPassword());

            /** 패스워드 정책 체크 */
            PwChgResult pwdChk ;
            try {
                pwdChk = CmmUtil.checkPasswd(systemEmpPwdManageVO.getNewPassword());
                if(pwdChk != PwChgResult.CHECK_OK) {
                    return pwdChk;
                }
            } catch (Exception e) {
                throw new AuthenticationException(messageService.get("login.pwchg.error"), "");
            }

        } else {
            systemEmpPwdManageVO.setModDt(currentDateTimeString());
            newPassword = EncUtil.setEncSHA256(systemEmpPwdManageVO.getEmpNo() + systemEmpPwdManageVO.getNewPassword());
        }

        /** 변경 패스워드가 기존 비밀번호가 같은지 체크 */
        if ( oldPassword.equals(newPassword) ) {
            return PwChgResult.PASSWORD_NEW_OLD_MATH;
        }
        /** 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인 */
        if ( !systemEmpPwdManageVO.getNewPassword().equals(systemEmpPwdManageVO.getConfirmPassword()) ) {
            return PwChgResult.NEW_PASSWORD_NOT_MATCH;
        }

        // 암호화된 비밀번호 Set 후 업데이트
        systemEmpPwdManageVO.setNewPassword(newPassword);
        // 비밀번호 업데이트
        int updateResult = systemEmpPwdManageMapper.updatePassword(systemEmpPwdManageVO);

        // 비밀번호 변경내역 저장
        systemEmpPwdManageVO.setPriorPwd(oldPassword);
        systemEmpPwdManageVO.setRegDt(currentDateTimeString());
        systemEmpPwdManageVO.setRegIp(HttpUtils.getClientIp(WebUtil.getRequest()));

        int insertResult = systemEmpPwdManageMapper.insertPasswordHistory(systemEmpPwdManageVO);

        return PwChgResult.CHECK_OK;
    }

}
