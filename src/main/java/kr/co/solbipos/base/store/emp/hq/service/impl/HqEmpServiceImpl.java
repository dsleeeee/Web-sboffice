package kr.co.solbipos.base.store.emp.hq.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpService;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
 * @ 2018.11.20  김지은      angular 방식으로 수정
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
    private final String HQ_AUTH_GRP_CD = "000007"; // TODO 보나비용 사용자 그룹코드 (화면에서 사용자 그룹 선택 필요)
    private final String DEFAULT_POS_PASSWORD = "1234";
    private final String PASSWORD_REGEX = "^[A-Za-z0-9]{6,20}$";

    private final HqEmpMapper hqEmpMapper;

    /** Constructor Injection */
    @Autowired
    public HqEmpServiceImpl(HqEmpMapper hqEmpMapper) {
        this.hqEmpMapper = hqEmpMapper;
    }

    /** 본사 사원 리스트 조회 */
    public List<DefaultMap<String>> getHqEmpList(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO){

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return hqEmpMapper.getHqEmpList(hqEmpVO);
    }


    /** 본사 사원 상세정보 조회  */
    public DefaultMap<String> getHqEmpDtlInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return hqEmpMapper.getHqEmpDtlInfo(hqEmpVO);
    }

    /** 본사 사원정보 등록 */
    @Override
    public EmpResult insertHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEmpVO.setEmpPwd(EncUtil.setEncSHA256(hqEmpVO.getEmpNo() + DEFAULT_POS_PASSWORD)); // 포스비밀번호 (초기 비밀번호)
        hqEmpVO.setUseYn(UseYn.Y);
        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);

        if(hqEmpVO.getWebUseYn() == UseYn.Y ) {

            hqEmpVO.setAuthGrpCd(HQ_AUTH_GRP_CD); //todo 본사권한코드 추후 수정 필요

            // 비밀번호 정책 체크
            EmpResult pwdChgResult = passwordPolicy(hqEmpVO);
            if( EmpResult.SUCCESS != pwdChgResult ) {
                return pwdChgResult;
            }
        }

        // 등록
        if( hqEmpMapper.insertHqEmpInfo(hqEmpVO) != 1 ) {
            return EmpResult.FAIL;
        } else {
            if( "Y".equals(hqEmpVO.getWebUseYn()) ) {
                if( hqEmpMapper.insertWbUserInfo(hqEmpVO) != 1 ) {
                    return EmpResult.FAIL;
                }
            }
        }
        return EmpResult.SUCCESS;
    }

    /** 본사 사원정보 수정 */
    @Override
    public EmpResult saveHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

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
                EmpResult pwdChgResult = passwordPolicy(hqEmpVO);
                if(EmpResult.SUCCESS != pwdChgResult) {
                    return pwdChgResult;
                }
            }
        }

        if( hqEmpMapper.updateHqEmpInfo(hqEmpVO) != 1 ) {
            return EmpResult.FAIL;
        }
        else{
            if( "Y".equals(hqEmpDtlInfo.getStr("webUseYn")) || "Y".equals(hqEmpVO.getWebUseYn())) {
                if( hqEmpMapper.saveWbUserInfo(hqEmpVO) != 1 ) {
                    return EmpResult.FAIL;
                }
                else {
                    // 비밀번호 변경여부로 체크
                    if(hqEmpVO.getPwdChgFg()) {
                        if (hqEmpMapper.insertPasswordHistory(hqEmpVO) != 1) {
                            return EmpResult.FAIL;
                        }
                    }

                }
            }
        }

        return EmpResult.SUCCESS;
    }


    /** 사용자 ID 사용여부 체크 (중복체크) */
    public EmpResult getHqUserIdCnt(HqEmpVO hqEmpVO){

        if(CmmUtil.checkUserId(hqEmpVO.getUserId()) != EmpResult.SUCCESS) {
            return CmmUtil.checkUserId(hqEmpVO.getUserId());
        }

        if( hqEmpMapper.getHqUserIdCnt(hqEmpVO) < 1) {
            return EmpResult.SUCCESS;
        }
        else {
            return EmpResult.USER_ID_DUPLICATE;
        }
    }


    /** 비밀번호 정책 */
    private EmpResult passwordPolicy(HqEmpVO hqEmpVO) {

        String newUserPassword = EncUtil.setEncSHA256(hqEmpVO.getUserId() + hqEmpVO.getUserPwd());

        if( newUserPassword.equals(hqEmpVO.getPriorPwd()) ) {
            return EmpResult.PASSWORD_NOT_CHANGED;
        }

        if ( !CmmUtil.passwordPolicyCheck(hqEmpVO.getUserPwd()) ) {
            return EmpResult.PASSWORD_REGEXP;
        }

        hqEmpVO.setUserPwd(newUserPassword);

        return EmpResult.SUCCESS;
    }


    //    /** 비밀번호 변경 */
    //    @Override
    //    public HqEmpResult modifyPassword(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {
    //
    //        DefaultMap<String> hqEmpDtlInfo = getHqEmpDtlInfo(hqEmpVO, sessionInfoVO);
    //        String dt = currentDateTimeString();
    //
    //        hqEmpVO.setRegId(sessionInfoVO.getUserId());
    //        hqEmpVO.setRegDt(dt);
    //        hqEmpVO.setModId(sessionInfoVO.getUserId());
    //        hqEmpVO.setModDt(dt);
    //        hqEmpVO.setPriorPwd(hqEmpMapper.getHqEmpPassword(hqEmpVO));
    //        hqEmpVO.setRegIp(sessionInfoVO.getLoginIp());
    //        hqEmpVO.setWebUseYn(UseYn.valueOf(hqEmpDtlInfo.get("webUseYn")));
    //
    //        //webUseYn이 Y인 경우만 변경 가능
    //        if( !"Y".equals(hqEmpVO.getWebUseYn()) ) {
    //            return HqEmpResult.FAIL;
    //        }
    //
    //        HqEmpResult pwdChgResult = passwordPolicy(hqEmpVO);
    //        if(HqEmpResult.SUCCESS != pwdChgResult) {
    //            return pwdChgResult;
    //        }
    //
    //        if( hqEmpMapper.updateUserPassword(hqEmpVO) != 1 ) {
    //            return HqEmpResult.FAIL;
    //        }
    //        else {
    //            if (hqEmpMapper.insertPasswordHistory(hqEmpVO) != 1) {
    //                return HqEmpResult.FAIL;
    //            }
    //        }
    //        return HqEmpResult.SUCCESS;
    //    }

}
