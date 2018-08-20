package kr.co.solbipos.store.manage.pwdmanage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.HttpUtils;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageService;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
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
    
    @Autowired
    PwdManageMapper pwdManageMapper;
    
    /** 비밀번호 임의변경 대상 조회 */
    @Override
    public List<DefaultMap<String>> getPwdManageList(PwdManageVO pwdManageVO) {
        return pwdManageMapper.getPwdManageList(pwdManageVO);
    }
    
    /** 비밀번호 변경 */
    @Override
    public PwChgResult modifyPwd(PwdManageVO pwdManageVO) {
        
        // 기본값 세팅
        pwdManageVO.setModDt(currentDateTimeString());
        pwdManageVO.setLastPwdChgDate(currentDateString());

        // 비밀번호 암호화
        String newPassword = EncUtil.setEncSHA256(pwdManageVO.getUserId() + pwdManageVO.getNewPassword());
        // 기존 비밀번호 조회
        String oldPassword = pwdManageMapper.getOldPassword(pwdManageVO); 
        
        /** 변경 패스워드가 기존 비밀번호가 같은지 체크 */
        if ( oldPassword.equals(newPassword) ) {
            return PwChgResult.PASSWORD_NEW_OLD_MATH;
        }
        /** 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인 */
        if ( !pwdManageVO.getNewPassword().equals(pwdManageVO.getConfirmPassword()) ) {
            return PwChgResult.NEW_PASSWORD_NOT_MATCH;
        }
        /** 패스워드 정책 체크 */
//        if ( !CmmUtil.passwordPolicyCheck(pwdManageVO.getNewPassword())
//                || !CmmUtil.passwordPolicyCheck(pwdManageVO.getConfirmPassword()) ) {
//            return PwChgResult.PASSWORD_REGEXP;
//        }

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
    
    
}
