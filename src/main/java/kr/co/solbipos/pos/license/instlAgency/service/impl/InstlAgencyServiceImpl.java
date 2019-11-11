package kr.co.solbipos.pos.license.instlAgency.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpVO;
import kr.co.solbipos.base.store.emp.system.service.impl.SystemEmpMapper;
import kr.co.solbipos.pos.license.instlAgency.service.InstlAgencyService;
import kr.co.solbipos.pos.license.instlAgency.service.InstlAgencyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("instlAgencyService")
@Transactional
public class InstlAgencyServiceImpl implements InstlAgencyService {

    private final String DEFAULT_POS_PASSWORD = "1234";

    private final InstlAgencyMapper instlAgencyMapper;
    private final SystemEmpMapper systemEmpMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public InstlAgencyServiceImpl(InstlAgencyMapper instlAgencyMapper, SystemEmpMapper systemEmpMapper, MessageService messageService) {
        this.instlAgencyMapper = instlAgencyMapper;
        this.systemEmpMapper = systemEmpMapper;
        this.messageService = messageService;
    }

    /** 설치업체관리 조회 */
    @Override
    public List<DefaultMap<String>> getInstlAgency(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        instlAgencyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlAgencyVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY && !sessionInfoVO.getpAgencyCd().equals("00000")) {
            instlAgencyVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return instlAgencyMapper.getInstlAgency(instlAgencyVO);
    }

    /** 설치업체관리 상세정보 조회 */
    @Override
    public DefaultMap<String> getInstlAgencyDtl(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO) {

        return instlAgencyMapper.getInstlAgencyDtl(instlAgencyVO);
    }

    /** 설치업체관리 저장 */
    @Override
    public int saveAgency(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        instlAgencyVO.setRegDt(dt);                         //등록일시
        instlAgencyVO.setRegId(sessionInfoVO.getUserId());  //등록아이디
        instlAgencyVO.setModDt(dt);                         //수정일시
        instlAgencyVO.setModId(sessionInfoVO.getUserId());  //수정아이디

        if(instlAgencyVO.getSaveType().equals("MOD")){

            result = instlAgencyMapper.updateAgency(instlAgencyVO);

        }else{
            // 업체구분, 업태 추후 개발 현재는 기본 00000으로 사용 중(2019.10.22 이다솜)
            instlAgencyVO.setBizTypeCd("00000");               //업체구분
            instlAgencyVO.setBizItemCd("00000");               //업태

            // 주소코드 추후 개발 현재는 기본 01로 사용 중(2019.10.22 이다솜)
            instlAgencyVO.setAreaCd("01");                     //주소코드

            result = instlAgencyMapper.insertAgency(instlAgencyVO);
        }

        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 설치업체 사원목록 조회 */
    @Override
    public List<DefaultMap<String>> getAgencyEmp(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO) {

        return instlAgencyMapper.getAgencyEmp(instlAgencyVO);
    }

    /** 설치업체 사원상세 조회 */
    @Override
    public DefaultMap<String> getAgencyEmpDtl(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO) {

        return instlAgencyMapper.getAgencyEmpDtl(instlAgencyVO);
    }

    /** 설치업체 사원저장 */
    @Override
    public EmpResult saveAgencyEmp(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO){

        try {

            SystemEmpVO systemEmpVO = new SystemEmpVO();
            String dt = currentDateTimeString();

            instlAgencyVO.setRegDt(dt);                         //등록일시
            instlAgencyVO.setRegId(sessionInfoVO.getUserId());  //등록아이디
            instlAgencyVO.setModDt(dt);                         //수정일시
            instlAgencyVO.setModId(sessionInfoVO.getUserId());  //수정아이디

            systemEmpVO.setAgencyCd(instlAgencyVO.getAgencyCd());
            systemEmpVO.setEmpNo(instlAgencyVO.getEmpNo());
            systemEmpVO.setUserId(instlAgencyVO.getUserId());
            systemEmpVO.setUserPwd(instlAgencyVO.getEmpPwd());
            systemEmpVO.setAuthGrpCd(sessionInfoVO.getAuthGrpCd());
            if(instlAgencyVO.getWebUseYn().equals("Y")){systemEmpVO.setWebUseYn(UseYn.Y);}else{systemEmpVO.setWebUseYn(UseYn.N);}
            systemEmpVO.setRegDt(dt);
            systemEmpVO.setRegId(sessionInfoVO.getUserId());
            systemEmpVO.setModDt(dt);
            systemEmpVO.setModId(sessionInfoVO.getUserId());

            // 수정 시
            if (instlAgencyVO.getSaveType().equals("MOD")) {
                if (instlAgencyMapper.updateEmployee(instlAgencyVO) != 1) {
                    return EmpResult.FAIL;
                }else{
                    if(instlAgencyVO.getUserId() != ""){
                        if( systemEmpMapper.saveWbUserInfo(systemEmpVO) <= 0 ) {
                            return EmpResult.FAIL;
                        }
                    }
                }
            } else { // 등록 시

                if (instlAgencyVO.getWebUseYn().equals("Y")) {

                    instlAgencyVO.setEmpPwd(EncUtil.setEncSHA256(instlAgencyVO.getUserId() + instlAgencyVO.getEmpPwd())); // 비밀번호 암호화

                    // 비밀번호 정책 체크
                    EmpResult pwdChgResult = passwordPolicy(instlAgencyVO);
                    if (EmpResult.SUCCESS != pwdChgResult) {
                        return pwdChgResult;
                    }
                }else{
                    instlAgencyVO.setEmpPwd(EncUtil.setEncSHA256(instlAgencyVO.getEmpNo() + DEFAULT_POS_PASSWORD)); // 포스비밀번호 (초기 비밀번호)
                }

                if (instlAgencyMapper.insertEmployee(instlAgencyVO) != 1) {
                    return EmpResult.FAIL;
                }

                if (instlAgencyVO.getWebUseYn().equals("Y")) {
                    if (systemEmpMapper.insertWbUserInfo(systemEmpVO) != 1) {
                        return EmpResult.FAIL;
                    }
                }
            }
        }catch(Exception ex){
            return EmpResult.FAIL;
        }

        return EmpResult.SUCCESS;
    }

    /** 비밀번호 정책 */
    private EmpResult passwordPolicy(InstlAgencyVO instlAgencyVO) {

        String newUserPassword = EncUtil.setEncSHA256(instlAgencyVO.getUserId() + instlAgencyVO.getEmpPwd());

        if ( !CmmUtil.passwordPolicyCheck(instlAgencyVO.getEmpPwd()) ) {
            return EmpResult.PASSWORD_REGEXP;
        }

        instlAgencyVO.setEmpPwd(newUserPassword);

        return EmpResult.SUCCESS;
    }

}
