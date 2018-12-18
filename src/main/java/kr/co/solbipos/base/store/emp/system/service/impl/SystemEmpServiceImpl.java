package kr.co.solbipos.base.store.emp.system.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpService;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpVO;
import kr.co.solbipos.base.store.emp.system.service.enums.AdminFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SystemEmpServiceImpl.java
 * @Description : 시스템관리 > 사원관리 > 사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.26  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 11.26
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("systemEmpService")
@Transactional
public class SystemEmpServiceImpl implements SystemEmpService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final String AUTH_GRP_CD = "000007"; // TODO 보나비용 사용자 그룹코드 (화면에서 사용자 그룹 선택 필요)
    private final String DEFAULT_POS_PASSWORD = "1234";
    private final String PASSWORD_REGEX = "^[A-Za-z0-9]{6,20}$";

    private final SystemEmpMapper systemEmpMapper;
    private final MessageService messageService;


    /** Constructor Injection */
    @Autowired
    public SystemEmpServiceImpl(
        kr.co.solbipos.base.store.emp.system.service.impl.SystemEmpMapper systemEmpMapper, MessageService messageService) {
        this.systemEmpMapper = systemEmpMapper;
        this.messageService = messageService;
    }

    /** 사원 리스트 조회 */
    public List<DefaultMap<String>> getSystemEmpList(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO){
        return systemEmpMapper.getSystemEmpList(systemEmpVO);
    }


    /** 사원 상세정보 조회  */
    public DefaultMap<String> getSystemEmpDtlInfo(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO) {
        return systemEmpMapper.getSystemEmpDtlInfo(systemEmpVO);
    }

    /** 사원정보 등록 */
    @Override
    public EmpResult insertSystemEmpInfo(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO) {

        try{

            String dt = currentDateTimeString();

            String empNo = systemEmpMapper.getSystemEmpNo();
//            if( systemEmpVO.getAdminFg() ==  AdminFg.ADMIN ) {
//                systemEmpVO.setAgencyCd("00001"); // 시스템 관리자인 경우 '00000'
//            }

            systemEmpVO.setEmpNo(empNo);
            systemEmpVO.setEmpPwd(EncUtil.setEncSHA256(systemEmpVO.getEmpNo() + DEFAULT_POS_PASSWORD)); // 포스비밀번호 (초기 비밀번호)
            systemEmpVO.setUseYn(UseYn.Y);
            systemEmpVO.setRegId(sessionInfoVO.getUserId());
            systemEmpVO.setRegDt(dt);
            systemEmpVO.setModId(sessionInfoVO.getUserId());
            systemEmpVO.setModDt(dt);

            if(systemEmpVO.getWebUseYn() == UseYn.Y ) {
                // 비밀번호 정책 체크
                EmpResult pwdChgResult = passwordPolicy(systemEmpVO);
                if( EmpResult.SUCCESS != pwdChgResult ) {
                    return pwdChgResult;
                }
            }

            // 등록
            if( systemEmpMapper.insertSystemEmpInfo(systemEmpVO) != 1 ) {
                return EmpResult.FAIL;
            } else {
                if(systemEmpVO.getWebUseYn() == UseYn.Y ) {

                    // todo 현재는 보나비용으로 시스템, 대리점 권한을 넣음 (추후 변경 필요)
                    if(systemEmpVO.getAdminFg() == AdminFg.ADMIN) {
                        systemEmpVO.setAuthGrpCd("000005");
                    } else {
                        systemEmpVO.setAuthGrpCd("000006");
                    }

                    if( systemEmpMapper.insertWbUserInfo(systemEmpVO) != 1 ) {
                        return EmpResult.FAIL;
                    }
                }
            }
        }catch(Exception ex){
            return EmpResult.FAIL;
        }

        return EmpResult.SUCCESS;
    }

    /** 사원정보 수정 */
    @Override
    public EmpResult saveSystemEmpInfo(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO) {


        try{

            DefaultMap<String> systemEmpDtlInfo = getSystemEmpDtlInfo(systemEmpVO, sessionInfoVO);
            String dt = currentDateTimeString();

            systemEmpVO.setPriorPwd(systemEmpMapper.getSystemEmpPassword(systemEmpVO));
            systemEmpVO.setRegId(sessionInfoVO.getUserId());
            systemEmpVO.setRegDt(dt);
            systemEmpVO.setModId(sessionInfoVO.getUserId());
            systemEmpVO.setModDt(dt);
            systemEmpVO.setRegIp(sessionInfoVO.getLoginIp());

            if( systemEmpVO.getWebUseYn() == UseYn.Y) {
                systemEmpVO.setAuthGrpCd(AUTH_GRP_CD);
            }

            if( systemEmpMapper.updateSystemEmpInfo(systemEmpVO) <= 0 ) {
                return EmpResult.FAIL;
            }
            else{
                if( "Y".equals(systemEmpDtlInfo.getStr("webUseYn")) || "Y".equals(systemEmpVO.getWebUseYn())) {
                    if( systemEmpMapper.saveWbUserInfo(systemEmpVO) <= 0 ) {
                        return EmpResult.FAIL;
                    }
                }
            }

        }catch(Exception ex){
            return EmpResult.FAIL;
        }

        return EmpResult.SUCCESS;
    }

    /** 사용자 ID 사용여부 체크 (중복체크) */
    public EmpResult getSystemUserIdCnt(SystemEmpVO systemEmpVO){

        if(CmmUtil.checkUserId(systemEmpVO.getUserId()) != EmpResult.SUCCESS) {
            return CmmUtil.checkUserId(systemEmpVO.getUserId());
        }

        if( systemEmpMapper.getSystemUserIdCnt(systemEmpVO) < 1) {
            return EmpResult.SUCCESS;
        }
        else {
            return EmpResult.USER_ID_DUPLICATE;
        }
    }


    /** 비밀번호 정책 */
    private EmpResult passwordPolicy(SystemEmpVO systemEmpVO) {

        String newUserPassword = EncUtil.setEncSHA256(systemEmpVO.getUserId() + systemEmpVO.getUserPwd());

        // 비밀번호 변경시
        if(systemEmpVO.getPwdChgFg()) {

            String currentPassword = EncUtil.setEncSHA256(systemEmpVO.getUserId() + systemEmpVO.getCurrentPwd());

            // 현재 비밀번호 불일치
            if(!systemEmpVO.getPriorPwd().equals(currentPassword)) {
                return EmpResult.PASSWORD_NOT_MATCH;
            }
        }

        // 기존 비밀번호와 동일
        if( newUserPassword.equals(systemEmpVO.getPriorPwd()) ) {
            return EmpResult.PASSWORD_NOT_CHANGED;
        }

        // 비밀번호 정책과 맞지 않음
        if ( !CmmUtil.passwordPolicyCheck(systemEmpVO.getUserPwd()) ) {
            return EmpResult.PASSWORD_REGEXP;
        }

        systemEmpVO.setUserPwd(newUserPassword);

        return EmpResult.SUCCESS;
    }

    /** 비밀번호 변경 */
    @Override
    public EmpResult modifyPassword(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        systemEmpVO.setRegId(sessionInfoVO.getUserId());
        systemEmpVO.setRegDt(dt);
        systemEmpVO.setModId(sessionInfoVO.getUserId());
        systemEmpVO.setModDt(dt);
        systemEmpVO.setPriorPwd(systemEmpMapper.getSystemEmpPassword(systemEmpVO));
        systemEmpVO.setRegIp(sessionInfoVO.getLoginIp());

        // 변경 비밀번호 정책 체크
        EmpResult pwdChgResult = passwordPolicy(systemEmpVO);
        if(EmpResult.SUCCESS != pwdChgResult) {
            return pwdChgResult;
        }

        // 비밀번호 변경
        if( systemEmpMapper.updateUserPassword(systemEmpVO) != 1 ) {
            return EmpResult.FAIL;
        }
        else {
            // 변경 히스토리 등록
            if (systemEmpMapper.insertPasswordHistory(systemEmpVO) != 1) {
                return EmpResult.FAIL;
            }
        }
        return EmpResult.SUCCESS;
    }

}
