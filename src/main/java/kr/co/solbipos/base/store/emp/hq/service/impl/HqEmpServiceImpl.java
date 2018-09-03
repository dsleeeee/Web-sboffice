package kr.co.solbipos.base.store.emp.hq.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpService;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import kr.co.solbipos.base.store.emp.hq.service.enums.HqEmpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @Class Name : HqEmpServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 본사사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("HqEmpService")
@Transactional
public class HqEmpServiceImpl implements HqEmpService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final String HQ_AUTH_GRP_CD = "000001";
    private final String DEFAULT_POS_PASSWORD = "1234";
    private final String EMP_NO_REGEX = "^[\\d]{4}$";
    private final String PASSWORD_REGEX =
            "^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[^\\w\\sㄱ-ㅎㅏ-ㅣ가-힣]).{6,20}$";

    @Autowired
    private HqEmpMapper mapper;

    public <E> List<E> selectHqEmpList(HqEmpVO hqEmpVO){
        return mapper.selectHqEmpList(hqEmpVO);
    }

    @Override
    public HqEmpResult insertHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);
        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEmpVO.setUseYn("Y");
        hqEmpVO.setEmpPwd(EncUtil.setEncSHA256(hqEmpVO.getEmpNo() + DEFAULT_POS_PASSWORD));
        hqEmpVO.setPriorPwd(mapper.selectHqEmpPassword(hqEmpVO));

        if( selectHqEmpNoCnt(hqEmpVO,sessionInfoVO) != HqEmpResult.SUCCESS ) {
            return HqEmpResult.EMP_NO_DUPLICATE;
        }

        if( "Y".equals(hqEmpVO.getWebUseYn()) ) {

            hqEmpVO.setAuthGrpCd(HQ_AUTH_GRP_CD);

            if( !userIdPolicyCheck(hqEmpVO.getUserId()) ) {
                return HqEmpResult.USER_ID_REGEXP;
            }

            if( selectHqUserIdCnt(hqEmpVO) != HqEmpResult.SUCCESS ){
                return HqEmpResult.USER_ID_DUPLICATE;
            }

            HqEmpResult pwdChgResult = passwordPolicy(hqEmpVO);
            if( HqEmpResult.SUCCESS != pwdChgResult ) {
                return pwdChgResult;
            }
        }

        if( mapper.insertHqEmpInfo(hqEmpVO) != 1 ) {
            return HqEmpResult.FAIL;
        }
        else{
            if( "Y".equals(hqEmpVO.getWebUseYn()) ) {
                if( mapper.insertWbUserInfo(hqEmpVO) != 1 ) {
                    return HqEmpResult.FAIL;
                }
            }
        }

        return HqEmpResult.SUCCESS;
    }

    @Override
    public HqEmpResult saveHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> hqEmpDtlInfo = selectHqEmpDtlInfo(hqEmpVO);
        String dt = currentDateTimeString();

        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);
        hqEmpVO.setPriorPwd(mapper.selectHqEmpPassword(hqEmpVO));
        hqEmpVO.setRegIp(sessionInfoVO.getLoginIp());


        if( "Y".equals(hqEmpVO.getWebUseYn()) ) {
            hqEmpVO.setAuthGrpCd(HQ_AUTH_GRP_CD);

            if( !userIdPolicyCheck(hqEmpVO.getUserId()) ) {
                return HqEmpResult.USER_ID_REGEXP;
            }

            if(!isEmpty(hqEmpVO.getNewUserPwd())) {
                HqEmpResult pwdChgResult = passwordPolicy(hqEmpVO);
                if(HqEmpResult.SUCCESS != pwdChgResult) {
                    return pwdChgResult;
                }
            }

            if( !hqEmpVO.getUserId().equals(hqEmpDtlInfo.getStr("userId")) ) {
                if( selectHqUserIdCnt(hqEmpVO) != HqEmpResult.SUCCESS ){
                    return HqEmpResult.USER_ID_DUPLICATE;
                }
            }
        }

        if( mapper.updateHqEmpInfo(hqEmpVO) != 1 ) {
            return HqEmpResult.FAIL;
        }
        else{
            if( "Y".equals(hqEmpDtlInfo.getStr("webUseYn")) || "Y".equals(hqEmpVO.getWebUseYn())) {
                if( mapper.saveWbUserInfo(hqEmpVO) != 1 ) {
                    return HqEmpResult.FAIL;
                }
                else {
                    if(!isEmpty(hqEmpVO.getNewUserPwd()) && !isEmpty(hqEmpVO.getPriorPwd())) {
                        if (mapper.insertPasswordHistory(hqEmpVO) != 1) {
                            return HqEmpResult.FAIL;
                        }
                    }

                }
            }
        }

        return HqEmpResult.SUCCESS;
    }

    @Override
    public HqEmpResult modifyPassword(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> hqEmpDtlInfo = selectHqEmpDtlInfo(hqEmpVO);
        String dt = currentDateTimeString();

        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);
        hqEmpVO.setPriorPwd(mapper.selectHqEmpPassword(hqEmpVO));
        hqEmpVO.setRegIp(sessionInfoVO.getLoginIp());
        hqEmpVO.setWebUseYn(hqEmpDtlInfo.getStr("webUseYn"));

        //webUseYn이 Y인 경우만 변경 가능
        if( !"Y".equals(hqEmpVO.getWebUseYn()) ) {
            return HqEmpResult.FAIL;
        }

        HqEmpResult pwdChgResult = passwordPolicy(hqEmpVO);
        if(HqEmpResult.SUCCESS != pwdChgResult) {
            return pwdChgResult;
        }

        if( mapper.updateUserPassword(hqEmpVO) != 1 ) {
            return HqEmpResult.FAIL;
        }
        else {
            if (mapper.insertPasswordHistory(hqEmpVO) != 1) {
                return HqEmpResult.FAIL;
            }
        }
        return HqEmpResult.SUCCESS;
    }

    public HqEmpResult selectHqEmpNoCnt(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO){

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!empNoPolicyCheck(hqEmpVO.getEmpNo())) {
            return HqEmpResult.EMP_NO_REGEXP;
        }

        if(isEmpty(hqEmpVO.getHqOfficeCd())) {
            return HqEmpResult.FAIL;
        }

        if( mapper.selectHqEmpNoCnt(hqEmpVO) == 0 ) {
            return HqEmpResult.SUCCESS;
        }
        else {
            return HqEmpResult.EMP_NO_DUPLICATE;
        }
    }

    public HqEmpResult selectHqUserIdCnt(HqEmpVO hqEmpVO){

        if( !userIdPolicyCheck(hqEmpVO.getUserId()) ) {
            return HqEmpResult.USER_ID_REGEXP;
        }

        if( mapper.selectHqUserIdCnt(hqEmpVO) < 1) {
            return HqEmpResult.SUCCESS;
        }
        else {
            return HqEmpResult.USER_ID_DUPLICATE;
        }
    }


    public DefaultMap<String> selectHqEmpDtlInfo(HqEmpVO hqEmpVO) {
        return mapper.selectHqEmpDtlInfo(hqEmpVO);
    }

    private HqEmpResult passwordPolicy(HqEmpVO hqEmpVO) {

        String newUserPassword = EncUtil.setEncSHA256(hqEmpVO.getUserId() + hqEmpVO.getUserPwd());

        if( !hqEmpVO.getUserPwd().equals(hqEmpVO.getUserPwdCfm()) ) {
            return HqEmpResult.PASSWORD_NOT_MATCH;
        }

        if( newUserPassword.equals(hqEmpVO.getPriorPwd()) ) {
            return HqEmpResult.PASSWORD_NOT_CHANGED;
        }

        if ( !passwordPolicyCheck(hqEmpVO.getUserPwd()) ) {
            return HqEmpResult.PASSWORD_REGEXP;
        }

        hqEmpVO.setUserPwd(newUserPassword);

        return HqEmpResult.SUCCESS;
    }

    public  boolean empNoPolicyCheck(String value) {
        if ( isEmpty(value) ) {
            LOGGER.warn("emp_no policy check emp_no null. value:{}", value);
            return false;
        }
        // 숫자 4자리 사용.
        Pattern pattern = Pattern.compile(EMP_NO_REGEX);

        Matcher m = pattern.matcher(value);

        if ( m.find() ) {
            return true;
        }

        LOGGER.info("emp_no policy check false");
        return false;
    }

    public  boolean passwordPolicyCheck(String value) {
        if ( isEmpty(value) ) {
            LOGGER.warn("password-policy check password null. password:{}", value);
            return false;
        }

        // 비밀번호 6자리 ~ 20 자리, 비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수기호 사용.
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);

        Matcher m = pattern.matcher(value);

        if ( m.find() ) {
            return true;
        }

        LOGGER.info("password policy check false");
        return false;
    }

    public boolean userIdPolicyCheck(String value) {

        if ( isEmpty(value) ) {
            LOGGER.warn("user_id policy check value null. value:{}", value);
            return false;
        }

        int len = value.length();

        // 사용자아이디 6자리 ~ 12 자리, 영문 소문자, 숫자 사용.
        boolean flag = Pattern.matches(".*[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]+.*$", value);
        boolean flag2 = Pattern.matches(".*[a-zA-Z]+.*", value);
        boolean flag3 = Pattern.matches("[a-zA-Z0-9]*", value);
        boolean flag4 = Pattern.matches(".*[A-Z]+.*", value);

        if( len > 12 || len < 6 ) {
            return false;
        } else if( flag == true ) {
            return false;
        } else if( flag2 == false ) {
            return false;
        } else if( flag3 == false ) {
            return false;
        } else if( flag4 == true ) {
            return false;
        } else {
            return true;
        }
    }

}
