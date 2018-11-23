package kr.co.solbipos.base.store.emp.hq.service.impl;

import kr.co.common.data.enums.UseYn;
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
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("HqEmpService")
@Transactional
public class HqEmpServiceImpl implements HqEmpService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final String HQ_AUTH_GRP_CD = "000005"; // TODO 보나비용 사용자 그룹코드 (화면에서 사용자 그룹 선택 필요)
    private final String DEFAULT_POS_PASSWORD = "1234";
    private final String EMP_NO_REGEX = "^[\\d]{4}$";
//    private final String PASSWORD_REGEX = "^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[^\\w\\sㄱ-ㅎㅏ-ㅣ가-힣]).{6,20}$";
    private final String PASSWORD_REGEX = "^[A-Za-z0-9]{6,20}$";

    private final HqEmpMapper hqEmpMapper;

    /** Constructor Injection */
    @Autowired
    public HqEmpServiceImpl(HqEmpMapper hqEmpMapper) {
        this.hqEmpMapper = hqEmpMapper;
    }

    /** 본사 사원 리스트 조회 */
    public <E> List<E> getHqEmpList(HqEmpVO hqEmpVO){
        return hqEmpMapper.getHqEmpList(hqEmpVO);
    }

    /** 본사 사원정보 등록 */
    @Override
    public HqEmpResult insertHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEmpVO.setEmpPwd(EncUtil.setEncSHA256(hqEmpVO.getEmpNo() + DEFAULT_POS_PASSWORD)); // 포스비밀번호 (초기 비밀번호)
        hqEmpVO.setPriorPwd(hqEmpMapper.getHqEmpPassword(hqEmpVO));
        hqEmpVO.setUseYn(UseYn.Y);
        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);

        if(hqEmpVO.getWebUseYn() == UseYn.Y ) {

            hqEmpVO.setAuthGrpCd(HQ_AUTH_GRP_CD); //todo 본사권한코드 추후 수정 필요

            // 비밀번호 정책 체크
            HqEmpResult pwdChgResult = passwordPolicy(hqEmpVO);
            if( HqEmpResult.SUCCESS != pwdChgResult ) {
                return pwdChgResult;
            }
        }

        // 등록
        if( hqEmpMapper.insertHqEmpInfo(hqEmpVO) != 1 ) {
            return HqEmpResult.FAIL;
        } else {
            if( "Y".equals(hqEmpVO.getWebUseYn()) ) {
                if( hqEmpMapper.insertWbUserInfo(hqEmpVO) != 1 ) {
                    return HqEmpResult.FAIL;
                }
            }
        }
        return HqEmpResult.SUCCESS;
    }

    /** 본사 사원정보 수정 */
    @Override
    public HqEmpResult saveHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> hqEmpDtlInfo = getHqEmpDtlInfo(hqEmpVO, sessionInfoVO);
        String dt = currentDateTimeString();

        hqEmpVO.setPriorPwd(hqEmpMapper.getHqEmpPassword(hqEmpVO));
        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);
        hqEmpVO.setRegIp(sessionInfoVO.getLoginIp());

        if( hqEmpVO.getWebUseYn() == UseYn.Y) {
            hqEmpVO.setAuthGrpCd(HQ_AUTH_GRP_CD);

            if(hqEmpVO.getPwdChgFg() && !isEmpty(hqEmpVO.getUserPwd())) { // 비밀번호 유효성체크
                HqEmpResult pwdChgResult = passwordPolicy(hqEmpVO);
                if(HqEmpResult.SUCCESS != pwdChgResult) {
                    return pwdChgResult;
                }
            }
        }

        if( hqEmpMapper.updateHqEmpInfo(hqEmpVO) != 1 ) {
            return HqEmpResult.FAIL;
        }
        else{
            if( "Y".equals(hqEmpDtlInfo.getStr("webUseYn")) || "Y".equals(hqEmpVO.getWebUseYn())) {
                if( hqEmpMapper.saveWbUserInfo(hqEmpVO) != 1 ) {
                    return HqEmpResult.FAIL;
                }
                else {
                    // 비밀번호 변경여부로 체크
                    if(hqEmpVO.getPwdChgFg()) {
                        if (hqEmpMapper.insertPasswordHistory(hqEmpVO) != 1) {
                            return HqEmpResult.FAIL;
                        }
                    }

                }
            }
        }

        return HqEmpResult.SUCCESS;
    }

    /** 비밀번호 변경 */
    @Override
    public HqEmpResult modifyPassword(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> hqEmpDtlInfo = getHqEmpDtlInfo(hqEmpVO, sessionInfoVO);
        String dt = currentDateTimeString();

        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);
        hqEmpVO.setPriorPwd(hqEmpMapper.getHqEmpPassword(hqEmpVO));
        hqEmpVO.setRegIp(sessionInfoVO.getLoginIp());
        hqEmpVO.setWebUseYn(UseYn.valueOf(hqEmpDtlInfo.get("webUseYn")));

        //webUseYn이 Y인 경우만 변경 가능
        if( !"Y".equals(hqEmpVO.getWebUseYn()) ) {
            return HqEmpResult.FAIL;
        }

        HqEmpResult pwdChgResult = passwordPolicy(hqEmpVO);
        if(HqEmpResult.SUCCESS != pwdChgResult) {
            return pwdChgResult;
        }

        if( hqEmpMapper.updateUserPassword(hqEmpVO) != 1 ) {
            return HqEmpResult.FAIL;
        }
        else {
            if (hqEmpMapper.insertPasswordHistory(hqEmpVO) != 1) {
                return HqEmpResult.FAIL;
            }
        }
        return HqEmpResult.SUCCESS;
    }

    /** 사원번호 사용여부 체크 (중복체크) */
    public HqEmpResult getHqEmpNoCnt(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO){

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!empNoPolicyCheck(hqEmpVO.getEmpNo())) {
            return HqEmpResult.EMP_NO_REGEXP;
        }

        if(isEmpty(hqEmpVO.getHqOfficeCd())) {
            return HqEmpResult.FAIL;
        }

        if( hqEmpMapper.getHqEmpNoCnt(hqEmpVO) == 0 ) {
            return HqEmpResult.SUCCESS;
        }
        else {
            return HqEmpResult.EMP_NO_DUPLICATE;
        }
    }

    /** 사용자 ID 사용여부 체크 (중복체크) */
    public HqEmpResult getHqUserIdCnt(HqEmpVO hqEmpVO){

        if( !userIdPolicyCheck(hqEmpVO.getUserId()) ) {
            return HqEmpResult.USER_ID_REGEXP;
        }

        if( hqEmpMapper.getHqUserIdCnt(hqEmpVO) < 1) {
            return HqEmpResult.SUCCESS;
        }
        else {
            return HqEmpResult.USER_ID_DUPLICATE;
        }
    }


    /** 본사 사원 상세정보 조회  */
    public DefaultMap<String> getHqEmpDtlInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return hqEmpMapper.getHqEmpDtlInfo(hqEmpVO);
    }

    /** 비밀번호 정책 */
    private HqEmpResult passwordPolicy(HqEmpVO hqEmpVO) {

        String newUserPassword = EncUtil.setEncSHA256(hqEmpVO.getUserId() + hqEmpVO.getUserPwd());

        if( newUserPassword.equals(hqEmpVO.getPriorPwd()) ) {
            return HqEmpResult.PASSWORD_NOT_CHANGED;
        }

        if ( !passwordPolicyCheck(hqEmpVO.getUserPwd()) ) {
            return HqEmpResult.PASSWORD_REGEXP;
        }

        hqEmpVO.setUserPwd(newUserPassword);

        return HqEmpResult.SUCCESS;
    }

    /** 비밀번호 정책 체크 */
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

    /** 사원번호 정책체크 */
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

    /** 사용자 ID 정책 체크 */
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
