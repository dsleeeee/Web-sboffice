package kr.co.solbipos.base.store.emp.store.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpService;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public EmpResult getStoreUserIdCnt(StoreEmpVO storeEmpVO){

        if(CmmUtil.checkUserId(storeEmpVO.getUserId()) != EmpResult.SUCCESS) {
            return CmmUtil.checkUserId(storeEmpVO.getUserId());
        }

        if( storeEmpMapper.getStoreUserIdCnt(storeEmpVO) < 1) {
            return EmpResult.SUCCESS;
        }
        else {
            return EmpResult.USER_ID_DUPLICATE;
        }
    }

    /** 매장 사원 정보 등록 */
    @Override
    public EmpResult insertStoreEmpInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO) {

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
            EmpResult pwdChgResult = passwordPolicy(storeEmpVO);
            if( EmpResult.SUCCESS != pwdChgResult ) {
                return pwdChgResult;
            }
        }

        // 등록
        if( storeEmpMapper.insertStoreEmpInfo(storeEmpVO) != 1 ) {
            return EmpResult.FAIL;
        } else {
            if( "Y".equals(storeEmpVO.getWebUseYn()) ) {
                if( storeEmpMapper.insertWbUserInfo(storeEmpVO) != 1 ) {
                    return EmpResult.FAIL;
                }
            }
        }
        return EmpResult.SUCCESS;
    }

    /** 비밀번호 정책 */
    private EmpResult passwordPolicy(StoreEmpVO storeEmpVO) {

        String newUserPassword = EncUtil.setEncSHA256(storeEmpVO.getUserId() + storeEmpVO.getUserPwd());

        if( newUserPassword.equals(storeEmpVO.getPriorPwd()) ) {
            return EmpResult.PASSWORD_NOT_CHANGED;
        }

        if ( !CmmUtil.passwordPolicyCheck(storeEmpVO.getUserPwd()) ) {
            return EmpResult.PASSWORD_REGEXP;
        }

        storeEmpVO.setUserPwd(newUserPassword);

        return EmpResult.SUCCESS;
    }

    /** 매장 사원정보 수정 */
    @Override
    public EmpResult saveStoreEmpInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO) {

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
                EmpResult pwdChgResult = passwordPolicy(storeEmpVO);
                if(EmpResult.SUCCESS != pwdChgResult) {
                    return pwdChgResult;
                }
            }
        }

        if( storeEmpMapper.updateStoreEmpInfo(storeEmpVO) != 1 ) {
            return EmpResult.FAIL;
        }
        else{
            if( "Y".equals(storeEmpDtlInfo.getStr("webUseYn")) || "Y".equals(storeEmpVO.getWebUseYn())) {
                if( storeEmpMapper.saveWbUserInfo(storeEmpVO) != 1 ) {
                    return EmpResult.FAIL;
                }
                else {
                    // 비밀번호 변경여부로 체크
                    if(storeEmpVO.getPwdChgFg()) {
                        if (storeEmpMapper.insertPasswordHistory(storeEmpVO) != 1) {
                            return EmpResult.FAIL;
                        }
                    }

                }
            }
        }
        return EmpResult.SUCCESS;
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
