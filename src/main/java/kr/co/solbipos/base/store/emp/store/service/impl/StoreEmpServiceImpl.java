package kr.co.solbipos.base.store.emp.store.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpService;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
import kr.co.solbipos.base.store.emp.store.service.enums.StoreEmpResult;
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
 * @Class Name : StoreEmpServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 * @ 2018.11.23  김지은     angular 방식으로 변경 및 로직 수정(타 페이지와 통일성 맞춤)
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

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final String STORE_AUTH_GRP_CD = "000008"; // TODO 보나비용 사용자 그룹코드 (화면에서 사용자 그룹 선택 필요)
    private final String DEFAULT_POS_PASSWORD = "1234";
    private final String EMP_NO_REGEX = "^[\\d]{4}$";
    //    private final String PASSWORD_REGEX = "^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[^\\w\\sㄱ-ㅎㅏ-ㅣ가-힣]).{6,20}$";
    private final String PASSWORD_REGEX = "^[A-Za-z0-9]{6,20}$";


    StoreEmpMapper storeEmpMapper;
    MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public StoreEmpServiceImpl(StoreEmpMapper storeEmpMapper, MessageService messageService) {
        this.storeEmpMapper = storeEmpMapper;
        this.messageService = messageService;
    }

    /** 매장 사원 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreEmpList(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO) {

        storeEmpVO.setStoreCd(sessionInfoVO.getStoreCd());

        return storeEmpMapper.getStoreEmpList(storeEmpVO);
    }

    /** 매장사원 상세정보 조회 */
    @Override
    public DefaultMap<String> getStoreEmpDtlInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO) {

        storeEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeEmpVO.setStoreCd(sessionInfoVO.getStoreCd());

        return storeEmpMapper.getStoreEmpDtlInfo(storeEmpVO);
    }

    /** 사용자 ID 사용여부 체크 (중복체크) */
    public StoreEmpResult getStoreUserIdCnt(StoreEmpVO storeEmpVO){

        if( !userIdPolicyCheck(storeEmpVO.getUserId()) ) {
            return StoreEmpResult.USER_ID_REGEXP;
        }

        if( storeEmpMapper.getStoreUserIdCnt(storeEmpVO) < 1) {
            return StoreEmpResult.SUCCESS;
        }
        else {
            return StoreEmpResult.USER_ID_DUPLICATE;
        }
    }

    /** 매장 사원 정보 등록 */
    @Override
    public StoreEmpResult insertStoreEmpInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storeEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeEmpVO.setStoreCd(sessionInfoVO.getStoreCd());
        storeEmpVO.setEmpPwd(EncUtil.setEncSHA256(storeEmpVO.getEmpNo() + DEFAULT_POS_PASSWORD)); // 포스비밀번호 (초기 비밀번호)
        storeEmpVO.setUseYn(UseYn.Y);
        storeEmpVO.setRegId(sessionInfoVO.getUserId());
        storeEmpVO.setRegDt(dt);
        storeEmpVO.setModId(sessionInfoVO.getUserId());
        storeEmpVO.setModDt(dt);

        if(storeEmpVO.getWebUseYn() == UseYn.Y ) {

            storeEmpVO.setAuthGrpCd(STORE_AUTH_GRP_CD); //todo 매장권한코드 추후 수정 필요

            // 비밀번호 정책 체크
            StoreEmpResult pwdChgResult = passwordPolicy(storeEmpVO);
            if( StoreEmpResult.SUCCESS != pwdChgResult ) {
                return pwdChgResult;
            }
        }

        // 등록
        if( storeEmpMapper.insertStoreEmpInfo(storeEmpVO) != 1 ) {
            return StoreEmpResult.FAIL;
        } else {
            if( "Y".equals(storeEmpVO.getWebUseYn()) ) {
                if( storeEmpMapper.insertWbUserInfo(storeEmpVO) != 1 ) {
                    return StoreEmpResult.FAIL;
                }
            }
        }
        return StoreEmpResult.SUCCESS;
    }

    /** 비밀번호 정책 */
    private StoreEmpResult passwordPolicy(StoreEmpVO storeEmpVO) {

        String newUserPassword = EncUtil.setEncSHA256(storeEmpVO.getUserId() + storeEmpVO.getUserPwd());

        if( newUserPassword.equals(storeEmpVO.getPriorPwd()) ) {
            return StoreEmpResult.PASSWORD_NOT_CHANGED;
        }

        if ( !passwordPolicyCheck(storeEmpVO.getUserPwd()) ) {
            return StoreEmpResult.PASSWORD_REGEXP;
        }

        storeEmpVO.setUserPwd(newUserPassword);

        return StoreEmpResult.SUCCESS;
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


    /** 매장 사원정보 수정 */
    @Override
    public StoreEmpResult saveStoreEmpInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> storeEmpDtlInfo = getStoreEmpDtlInfo(storeEmpVO, sessionInfoVO);
        String dt = currentDateTimeString();

        storeEmpVO.setPriorPwd(storeEmpMapper.getStoreEmpPassword(storeEmpVO));
        storeEmpVO.setAuthGrpCd(STORE_AUTH_GRP_CD);
        storeEmpVO.setRegId(sessionInfoVO.getUserId());
        storeEmpVO.setRegDt(dt);
        storeEmpVO.setModId(sessionInfoVO.getUserId());
        storeEmpVO.setModDt(dt);
        storeEmpVO.setRegIp(sessionInfoVO.getLoginIp());

        if( storeEmpVO.getWebUseYn() == UseYn.Y) {
            storeEmpVO.setAuthGrpCd(STORE_AUTH_GRP_CD);

            if(storeEmpVO.getPwdChgFg() && !isEmpty(storeEmpVO.getUserPwd())) { // 비밀번호 유효성체크
                StoreEmpResult pwdChgResult = passwordPolicy(storeEmpVO);
                if(StoreEmpResult.SUCCESS != pwdChgResult) {
                    return pwdChgResult;
                }
            }
        }

        if( storeEmpMapper.updateStoreEmpInfo(storeEmpVO) != 1 ) {
            return StoreEmpResult.FAIL;
        }
        else{
            if( "Y".equals(storeEmpDtlInfo.getStr("webUseYn")) || "Y".equals(storeEmpVO.getWebUseYn())) {
                if( storeEmpMapper.saveWbUserInfo(storeEmpVO) != 1 ) {
                    return StoreEmpResult.FAIL;
                }
                else {
                    // 비밀번호 변경여부로 체크
                    if(storeEmpVO.getPwdChgFg()) {
                        if (storeEmpMapper.insertPasswordHistory(storeEmpVO) != 1) {
                            return StoreEmpResult.FAIL;
                        }
                    }

                }
            }
        }
        return StoreEmpResult.SUCCESS;
    }


    //    /** 비밀번호 변경 */
    //    @Override
    //    public StoreEmpResult modifyPassword(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO) {
    //
    //        DefaultMap<String> storeEmpDtlInfo = getStoreEmpDtlInfo(storeEmpVO, sessionInfoVO);
    //        String dt = currentDateTimeString();
    //
    //        storeEmpVO.setRegId(sessionInfoVO.getUserId());
    //        storeEmpVO.setRegDt(dt);
    //        storeEmpVO.setModId(sessionInfoVO.getUserId());
    //        storeEmpVO.setModDt(dt);
    //        storeEmpVO.setPriorPwd(storeEmpMapper.getStoreEmpPassword(storeEmpVO));
    //        storeEmpVO.setRegIp(sessionInfoVO.getLoginIp());
    //        storeEmpVO.setWebUseYn(UseYn.valueOf(storeEmpDtlInfo.get("webUseYn")));
    //
    //        //webUseYn이 Y인 경우만 변경 가능
    //        if( !"Y".equals(storeEmpVO.getWebUseYn()) ) {
    //            return StoreEmpResult.FAIL;
    //        }
    //
    //        StoreEmpResult pwdChgResult = passwordPolicy(storeEmpVO);
    //        if(StoreEmpResult.SUCCESS != pwdChgResult) {
    //            return pwdChgResult;
    //        }
    //
    //        if( storeEmpMapper.updateUserPassword(storeEmpVO) != 1 ) {
    //            return StoreEmpResult.FAIL;
    //        }
    //        else {
    //            if (storeEmpMapper.insertPasswordHistory(storeEmpVO) != 1) {
    //                return StoreEmpResult.FAIL;
    //            }
    //        }
    //        return StoreEmpResult.SUCCESS;
    //    }


}
