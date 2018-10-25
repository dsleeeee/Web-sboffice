package kr.co.solbipos.base.store.emp.store.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.BizException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.HttpUtils;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpService;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
import kr.co.solbipos.base.store.emp.store.service.WebUserVO;
import kr.co.solbipos.base.store.emp.store.service.enums.StoreEmpResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : StoreEmpServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 *
 * @author NHN한국사이버결제 이한빈
 * @since 2018.08.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeEmpService")
@Transactional
public class StoreEmpServiceImpl implements StoreEmpService {
    private static String PASSWORD_REGEX =
                    "^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[^\\w\\sㄱ-ㅎㅏ-ㅣ가-힣]).{6,20}$";

    StoreEmpMapper storeEmpMapper;
    MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public StoreEmpServiceImpl(StoreEmpMapper storeEmpMapper, MessageService messageService) {
        this.storeEmpMapper = storeEmpMapper;
        this.messageService = messageService;
    }

    @Override
    public List<DefaultMap<String>> getStoreEmpList(StoreEmpVO storeEmpVO) {
        return storeEmpMapper.getStoreEmpList(storeEmpVO);
    }

    @Override
    public DefaultMap<String> getStoreEmp(StoreEmpVO storeEmpVO) {
        return storeEmpMapper.getStoreEmp(storeEmpVO);
    }

    @Override
    public StoreEmpResult saveStoreEmp(StoreEmpVO storeEmpVO) {
        StoreEmpResult result;

        // 등록
        if( storeEmpVO.getEmpRegist() ) {
            // TODO : 초기 매장사원 POS 패스워드 1234
            storeEmpVO.setEmpPwd(EncUtil.setEncSHA256(storeEmpVO.getEmpNo() + "1234"));
            result = storeEmpMapper.insertStoreEmp(storeEmpVO) == 1 ? StoreEmpResult.SUCC : StoreEmpResult.REGIST_FAIL;
        }
        // 수정
        else {
            result = storeEmpMapper.updateStoreEmp(storeEmpVO) == 1 ? StoreEmpResult.SUCC : StoreEmpResult.MODIFY_FAIL;
        }

        // 웹사용자 정보 수정
        if( result == StoreEmpResult.SUCC  && !StringUtil.isEmpty(storeEmpVO.getUserId()) )
            result = updateWebUser(storeEmpVO);

        // 처리 실패 시 Exception 발생
        if( result != StoreEmpResult.SUCC )
            throw new BizException(messageService.get(result.getValue()));

        return result;
    }

    @Override
    public StoreEmpResult updateWebUser(StoreEmpVO storeEmpVO) {
        StoreEmpResult result = StoreEmpResult.SUCC;

        // TODO : 현재 패스워드관련 변경은 TB_WB_USER_INFO 기준
        // 새로운 비밀번호가 입력되었을 경우에 웹 사용자 등록/수정 으로 본다
        if( storeEmpVO.getWebUseYn() == UseYn.Y && !StringUtil.isEmpty(storeEmpVO.getNewPwd()) ){
            result = saveWebUser(storeEmpVO);
        }

        if( result != StoreEmpResult.SUCC )
            return result;

        WebUserVO webUserVO = new WebUserVO();
        webUserVO.setUserId(storeEmpVO.getUserId());
        webUserVO.setUseYn(storeEmpVO.getWebUseYn());
        webUserVO.setRegId(storeEmpVO.getRegId());
        webUserVO.setRegDt(storeEmpVO.getRegDt());
        result = storeEmpMapper.updateWebUserUseYn(webUserVO) == 1 ? StoreEmpResult.SUCC : StoreEmpResult.WEBUSER_SAVE_FAIL;

        return result;
    }

    @Override
    public StoreEmpResult saveWebUser(StoreEmpVO storeEmpVO) {
        StoreEmpResult result;

        String priorPwd = storeEmpMapper.getCurrentPwd(storeEmpVO);

        WebUserVO webUserVO = new WebUserVO();
        webUserVO.setUserId(storeEmpVO.getUserId());
        webUserVO.setUserPwd(storeEmpVO.getNewPwd());
        webUserVO.setLastPwdChgDt(currentDateString());
        webUserVO.setUseYn(storeEmpVO.getWebUseYn());
        webUserVO.setRegId(storeEmpVO.getRegId());
        webUserVO.setRegDt(storeEmpVO.getRegDt());

        result = storeEmpMapper.saveWebUser(webUserVO) == 1 ? StoreEmpResult.SUCC : StoreEmpResult.WEBUSER_SAVE_FAIL;

        // 패스워드 변경(이전 비밀번호 존재)일 경우에만 이력 등록
        if( result == StoreEmpResult.SUCC && !StringUtils.isEmpty(priorPwd) ) {
            // 패스워드 변경이력 관련 파라메터 세팅
            webUserVO.setPriorPwd(priorPwd);
            webUserVO.setRegIp(HttpUtils.getClientIp(WebUtil.getRequest()));

            // 패스워드 변경이력 등록
            result = storeEmpMapper.insertWebPwdChangeHistory(webUserVO) == 1 ? StoreEmpResult.SUCC : StoreEmpResult.PWDHIST_SAVE_FAIL;
        }

        return result;
    }

    @Override
    public boolean checkDuplicateUserId(String userId) {
        return storeEmpMapper.getExistUserId(userId) != null;
    }

    @Override
    public String getValidPwd( StoreEmpVO storeEmpVO ) {
        if( storeEmpVO == null || StringUtil.isEmpty(storeEmpVO.getNewPwd()) || StringUtil.isEmpty(storeEmpVO.getNewPwdConfirm()) )
            throw new BizException(messageService.get("storeEmp.layer.pwd") + messageService.get("cmm.require.text"));

        StoreEmpResult checkResult = StoreEmpResult.SUCC;

        /** 새 패스워드와 새 패스워드 확인이 일치하는지 확인 */
        if ( !storeEmpVO.getNewPwd().equals(storeEmpVO.getNewPwdConfirm()) )
            checkResult = StoreEmpResult.PASSWORD_NOT_MATCH;

        /** 패스워드 정책 체크 */
        // 비밀번호 6-20자리, 비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수기호 모두가 포함되어야 합니다.
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher m = pattern.matcher(storeEmpVO.getNewPwd());
        if (!m.find()) {
            checkResult = StoreEmpResult.PASSWORD_NOT_VALID_FORMAT;
        }
//        if ( !EncUtil.passwordPolicyCheck(storeEmpVO.getNewPwd()) )
//            checkResult = StoreEmpResult.PASSWORD_NOT_VALID_FORMAT;

        String newPwd = EncUtil.setEncSHA256(storeEmpVO.getUserId() + storeEmpVO.getNewPwd());
        String priorPwd = storeEmpMapper.getCurrentPwd(storeEmpVO);

        /** 변경 패스워드가 기존 비밀번호가 같은지 체크 */
        if( newPwd.equals(priorPwd) )
            checkResult =  StoreEmpResult.PASSWORD_NEW_OLD_MATCH;

        if( checkResult != StoreEmpResult.SUCC )
            throw new BizException(messageService.get(checkResult.getValue()));

        return newPwd;
    }
}
