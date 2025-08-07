package kr.co.solbipos.store.manage.pwdManageSaleChk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdManageSaleChk.service.PwdManageSaleChkService;
import kr.co.solbipos.store.manage.pwdManageSaleChk.service.PwdManageSaleChkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : PwdManageSaleChkServiceImpl.java
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
@Service("pwdManageSaleChkService")
@Transactional
public class PwdManageSaleChkServiceImpl implements PwdManageSaleChkService {

    private final PwdManageSaleChkMapper pwdManageSaleChkMapper;

    @Autowired
    public PwdManageSaleChkServiceImpl(PwdManageSaleChkMapper pwdManageSaleChkMapper) {
        this.pwdManageSaleChkMapper = pwdManageSaleChkMapper;
    }


    @Override
    public List<DefaultMap<Object>> getPwdManageSaleChkList(PwdManageSaleChkVO pwdManageSaleChkVO, SessionInfoVO sessionInfoVO) {
        pwdManageSaleChkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            pwdManageSaleChkVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return pwdManageSaleChkMapper.getPwdManageSaleChkList(pwdManageSaleChkVO);
    }

    @Override
    public PwChgResult getClearPwdSave(PwdManageSaleChkVO pwdManageSaleChkVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();
        /** 사용자 ID가 없는지 체크 */
        if ((pwdManageSaleChkVO.getUserId() == "" || pwdManageSaleChkVO.getUserId() == null)){
            return PwChgResult.ID_IS_NULL;
        }
        pwdManageSaleChkVO.setNewPassword("0000");
        pwdManageSaleChkVO.setModDt(currentDt);

        int updateResult = pwdManageSaleChkMapper.getClearPwdSave(pwdManageSaleChkVO);

        return PwChgResult.CHECK_OK;
    }

    @Override
    public PwChgResult getModifySalePwd(PwdManageSaleChkVO pwdManageSaleChkVO) {

        String currentDt = currentDateTimeString();
        /** 사용자 ID가 없는지 체크 */
        if ((pwdManageSaleChkVO.getUserId() == "" || pwdManageSaleChkVO.getUserId() == null)){
            return PwChgResult.ID_IS_NULL;
        }

        // 기존 비밀번호 조회
        String salePwd = pwdManageSaleChkVO.getSalePwd();

        String password = pwdManageSaleChkVO.getPassword();

        System.out.println(salePwd + "세일비번");
        System.out.println(password + "입력비밀번호");

        /** 현재 비밀번호 맞게 입력했는지 확인 */
        if(!password.equals(salePwd)){
            return PwChgResult.PASSWORD_NOT_MATCH;
        }

        /** 비밀번호 4자리 입력했는지 확인 */
        if(pwdManageSaleChkVO.getNewPassword().length() != 4){
            return PwChgResult.PASSWORD_NOT_MATCH_LENGTH;
        }

        /** 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인 */
        if ( !pwdManageSaleChkVO.getNewPassword().equals(pwdManageSaleChkVO.getConfirmPassword()) ) {
            return PwChgResult.NEW_PASSWORD_NOT_MATCH;
        }

        pwdManageSaleChkVO.setModDt(currentDt);

        int getModifySalePwd = pwdManageSaleChkMapper.getModifySalePwd(pwdManageSaleChkVO);

        return PwChgResult.CHECK_OK;
    }

}
