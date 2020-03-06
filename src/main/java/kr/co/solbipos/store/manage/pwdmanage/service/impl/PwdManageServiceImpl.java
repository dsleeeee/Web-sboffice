package kr.co.solbipos.store.manage.pwdmanage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.HttpUtils;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.session.auth.enums.UserStatFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdmanage.enums.PwdChgFg;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageService;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PwdManageServiceImpl.java
 * @Description : 가맹점관리 > 매장관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("pwdManageService")
public class PwdManageServiceImpl implements PwdManageService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private final MessageService messageService;
    private final PwdManageMapper pwdManageMapper;

    /** Constructor Injection */
    @Autowired
    public PwdManageServiceImpl(PwdManageMapper pwdManageMapper, MessageService messageService) {
        this.pwdManageMapper = pwdManageMapper;
        this.messageService = messageService;
    }

    /** 비밀번호 임의변경 대상 조회 */
    @Override
    public List<DefaultMap<String>> getPwdManageList(PwdManageVO pwdManageVO, SessionInfoVO sessionInfoVO) {

        // 접속한 사용자의 소속구분
        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        pwdManageVO.setOrgnFg(orgnFg);
        pwdManageVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 소속 코드
        if(orgnFg == OrgnFg.AGENCY) {
            pwdManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        } else if(orgnFg == OrgnFg.HQ) {
            pwdManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        } else if(orgnFg == OrgnFg.STORE){
            pwdManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            pwdManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return pwdManageMapper.getPwdManageList(pwdManageVO);
    }

    /** 비밀번호 변경 */
    @Override
    public PwChgResult modifyPwd(PwdManageVO pwdManageVO) {

        // 기존 비밀번호 조회
        String oldPassword = pwdManageMapper.getOldPassword(pwdManageVO);

        String newPassword = "";
        // 기본값 세팅
        // 비밀번호 암호화
        if ( pwdManageVO.getPwdChgFg() == PwdChgFg.WEB ) {
            pwdManageVO.setLastPwdChgDt(currentDateTimeString());
            newPassword = EncUtil.setEncSHA256(pwdManageVO.getUserId() + pwdManageVO.getNewPassword());

            /** 패스워드 정책 체크 */
            PwChgResult pwdChk ;
            try {
                pwdChk = CmmUtil.checkPasswd(pwdManageVO.getNewPassword());
                if(pwdChk != PwChgResult.CHECK_OK) {
                    return pwdChk;
                }
            } catch (Exception e) {
                throw new AuthenticationException(messageService.get("login.pwchg.error"), "");
            }

        } else {
            pwdManageVO.setModDt(currentDateTimeString());
            newPassword = EncUtil.setEncSHA256(pwdManageVO.getEmpNo() + pwdManageVO.getNewPassword());
        }

        /** 변경 패스워드가 기존 비밀번호가 같은지 체크 */
        if ( oldPassword.equals(newPassword) ) {
            return PwChgResult.PASSWORD_NEW_OLD_MATH;
        }
        /** 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인 */
        if ( !pwdManageVO.getNewPassword().equals(pwdManageVO.getConfirmPassword()) ) {
            return PwChgResult.NEW_PASSWORD_NOT_MATCH;
        }

        // 암호화된 비밀번호 Set 후 업데이트
        pwdManageVO.setNewPassword(newPassword);
        // 비밀번호 업데이트
        int updateResult = pwdManageMapper.updatePassword(pwdManageVO);

        // 비밀번호 변경내역 저장
        pwdManageVO.setPriorPwd(oldPassword);
        pwdManageVO.setRegDt(currentDateTimeString());
        pwdManageVO.setRegIp(HttpUtils.getClientIp(WebUtil.getRequest()));

        int insertResult = pwdManageMapper.insertPasswordHistory(pwdManageVO);

        return PwChgResult.CHECK_OK;

    }

    /** 로그인 잠금해제 */
    @Override
    public PwChgResult updatePasswordUnLock(PwdManageVO pwdManageVO , SessionInfoVO sessionInfoVO) {

        // 사용자 상태 - 정상
        pwdManageVO.setUserStatFg(UserStatFg.NORMAL);

        if(pwdManageMapper.updatePasswordUnLock(pwdManageVO) > 0){
            return PwChgResult.CHECK_OK;
        } else{
            return PwChgResult.CHECK_NOK;
        }
    }

}
