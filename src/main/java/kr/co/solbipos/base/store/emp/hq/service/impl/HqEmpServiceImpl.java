package kr.co.solbipos.base.store.emp.hq.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.common.exception.JsonException;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpMenuVO;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpService;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

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
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public HqEmpServiceImpl(MessageService messageService, HqEmpMapper hqEmpMapper) {

        this.messageService = messageService;
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

        // 신규 사원번호 조회
        String empNo = hqEmpMapper.getHqEmpNo(hqEmpVO);

        hqEmpVO.setEmpNo(empNo);
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
            if( hqEmpVO.getWebUseYn() == UseYn.Y) {
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
        }

        if( hqEmpMapper.updateHqEmpInfo(hqEmpVO) != 1 ) {
            return EmpResult.FAIL;
        }
        else{
            if( "Y".equals(hqEmpDtlInfo.getStr("webUseYn")) || "Y".equals(hqEmpVO.getWebUseYn())) {
                if( hqEmpMapper.saveWbUserInfo(hqEmpVO) != 1 ) {
                    return EmpResult.FAIL;
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

        // 비밀번호 변경시
        if(hqEmpVO.getPwdChgFg()) {

            // 현재 비밀번호 불일치
            String currentPassword = EncUtil.setEncSHA256(hqEmpVO.getUserId() + hqEmpVO.getCurrentPwd());

            if(!hqEmpVO.getPriorPwd().equals(currentPassword)) {
                return EmpResult.PASSWORD_NOT_MATCH;
            }
        }

        if( newUserPassword.equals(hqEmpVO.getPriorPwd()) ) {
            return EmpResult.PASSWORD_NOT_CHANGED;
        }

        if ( !CmmUtil.passwordPolicyCheck(hqEmpVO.getUserPwd()) ) {
            return EmpResult.PASSWORD_REGEXP;
        }

        hqEmpVO.setUserPwd(newUserPassword);

        return EmpResult.SUCCESS;
    }


    /** 비밀번호 변경 */
    @Override
    public EmpResult modifyPassword(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        hqEmpVO.setRegId(sessionInfoVO.getUserId());
        hqEmpVO.setRegDt(dt);
        hqEmpVO.setModId(sessionInfoVO.getUserId());
        hqEmpVO.setModDt(dt);
        hqEmpVO.setPriorPwd(hqEmpMapper.getHqEmpPassword(hqEmpVO));
        hqEmpVO.setRegIp(sessionInfoVO.getLoginIp());

        EmpResult pwdChgResult = passwordPolicy(hqEmpVO);
        if(EmpResult.SUCCESS != pwdChgResult) {
            return pwdChgResult;
        }

        if( hqEmpMapper.updateUserPassword(hqEmpVO) != 1 ) {
            return EmpResult.FAIL;
        }
        else {
            if (hqEmpMapper.insertPasswordHistory(hqEmpVO) != 1) {
                return EmpResult.FAIL;
            }
        }
        return EmpResult.SUCCESS;
    }

    /** 권한복사를 위한 본사 사원 리스트 조회 */
    public List<DefaultMap<String>> authHqEmpList(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO){

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return hqEmpMapper.authHqEmpList(hqEmpVO);
    }

    /** 사용 메뉴 */
    @Override
    public List<DefaultMap<String>> avlblMenu(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return hqEmpMapper.avlblMenu(hqEmpVO);
    }

    /** 미사용 메뉴 */
    @Override
    public List<DefaultMap<String>> beUseMenu(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO) {

        hqEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return hqEmpMapper.beUseMenu(hqEmpVO);
    }

    /** 메뉴권한복사 */
    @Override
    public int copyAuth(HqEmpMenuVO hqEmpMenuVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        hqEmpMenuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEmpMenuVO.setRegDt(dt);
        hqEmpMenuVO.setRegId(sessionInfoVO.getUserId());
        hqEmpMenuVO.setModDt(dt);
        hqEmpMenuVO.setModId(sessionInfoVO.getUserId());

        // empCd : 복사 대상이 되는 사원
        // copyEmpCd : 복사할 기준이 되는 사원

        // 1. 메뉴 권한 복사
        int authGrpCopy = hqEmpMapper.copyAuth(hqEmpMenuVO);
        if(authGrpCopy <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        // 2. 기존 메뉴권한 예외값 삭제
        hqEmpMapper.removeAuthAll(hqEmpMenuVO);

        // 3. 메뉴 권한 예외값이 있는지 확인 후, 복사
        int authExpCopy = 0;
        List<DefaultMap<String>> excepList = hqEmpMapper.exceptMenu(hqEmpMenuVO);

        if(excepList != null && excepList.size() > 0){

            for (int i = 0; i < excepList.size(); i++) {

                hqEmpMenuVO.setResrceCd(excepList.get(i).getStr("resrceCd"));

                if("E".equals(excepList.get(i).getStr("incldExcldFg"))){
                    hqEmpMenuVO.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                }else{
                    hqEmpMenuVO.setIncldExcldFg(IncldExcldFg.INCLUDE);
                }
                hqEmpMenuVO.setUseYn(excepList.get(i).getStr("useYn"));

                int result = hqEmpMapper.copyAuthExcp(hqEmpMenuVO);
                if(result <= 0){
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                } else {
                    authExpCopy ++;
                }
            }
        }

        return (authGrpCopy+authExpCopy);
    }

    /** 사용메뉴 등록 */
    @Override
    public int addAuth(HqEmpMenuVO[] hqEmpMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(HqEmpMenuVO hqEmpMenu : hqEmpMenus){

            hqEmpMenu.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqEmpMenu.setIncldExcldFg(IncldExcldFg.EXCLUDE);
            hqEmpMenu.setRegDt(insertDt);
            hqEmpMenu.setRegId(sessionInfoVO.getUserId());
            hqEmpMenu.setModDt(insertDt);
            hqEmpMenu.setModId(sessionInfoVO.getUserId());

            // 권한 추가 테이블에 있는지 조회 후, 사용중인 권한이 있으면 삭제
            int isAuth = hqEmpMapper.isAuth(hqEmpMenu);

            if(isAuth > 0) {
                procCnt = hqEmpMapper.removeAuth(hqEmpMenu);
            }

            /*// 권한 추가 처리
            hqMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            procCnt = mapper.addAuth(hqMenu);*/
        }
        return procCnt;
    }

    /** 미사용메뉴 등록 */
    @Override
    public int removeAuth(HqEmpMenuVO[] hqEmpMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(HqEmpMenuVO hqEmpMenu : hqEmpMenus){

            hqEmpMenu.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqEmpMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            hqEmpMenu.setRegDt(insertDt);
            hqEmpMenu.setRegId(sessionInfoVO.getUserId());
            hqEmpMenu.setModDt(insertDt);
            hqEmpMenu.setModId(sessionInfoVO.getUserId());

            // 권한 예외 테이블에 있는지 조회 후, 예외로 들어간 권한이 있으면 삭제
            int isAuth = hqEmpMapper.isAuth(hqEmpMenu);

            if(isAuth > 0) {
                procCnt = hqEmpMapper.removeAuth(hqEmpMenu);
            }

            // 권한 삭제 처리
            hqEmpMenu.setIncldExcldFg(IncldExcldFg.EXCLUDE);
            procCnt = hqEmpMapper.addAuth(hqEmpMenu);

        }
        return procCnt;
    }



}
