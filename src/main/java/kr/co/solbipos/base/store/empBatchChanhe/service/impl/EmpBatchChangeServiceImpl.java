package kr.co.solbipos.base.store.empBatchChanhe.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.empBatchChanhe.service.EmpBatchChangeService;
import kr.co.solbipos.base.store.empBatchChanhe.service.EmpBatchChangeVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EmpBatchChangeServiceImpl.java
 * @Description : 맘스터치 > 사원관리 > 사원정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.16  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.02.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service
public class EmpBatchChangeServiceImpl implements EmpBatchChangeService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final EmpBatchChangeMapper empBatchChangeMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public EmpBatchChangeServiceImpl(EmpBatchChangeMapper empBatchChangeMapper, MessageService messageService) {
        this.empBatchChangeMapper = empBatchChangeMapper;
        this.messageService = messageService;
    }

    /** 사원목록 조회 */
    @Override
    public List<DefaultMap<String>> getEmpList(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO) {

        empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 사원브랜드 '전체' 일때
//        if (empBatchChangeVO.getStoreHqBrandCd() == "" || empBatchChangeVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
//            String[] userBrandList = empBatchChangeVO.getUserBrands().split(",");
//            empBatchChangeVO.setUserBrandList(userBrandList);
//        }

        return empBatchChangeMapper.getEmpList(empBatchChangeVO);
    }

    /** 사원목록 조회 */
    @Override
    public List<DefaultMap<String>> getEmpList2(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO) {

        empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 사원브랜드 '전체' 일때
//        if (empBatchChangeVO.getStoreHqBrandCd() == "" || empBatchChangeVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
//            String[] userBrandList = empBatchChangeVO.getUserBrands().split(",");
//            empBatchChangeVO.setUserBrandList(userBrandList);
//        }

        return empBatchChangeMapper.getEmpList2(empBatchChangeVO);
    }

    /** 사원정보 저장 */
    @Override
    public int getEmpBatchChangeSave(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpBatchChangeVO empBatchChangeVO : empBatchChangeVOs) {

            empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            empBatchChangeVO.setRegDt(currentDt);
            empBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            empBatchChangeVO.setModDt(currentDt);
            empBatchChangeVO.setModId(sessionInfoVO.getUserId());


            if(empBatchChangeVO.getStatus() == GridDataFg.UPDATE) {
                // 저장
                storeCnt += empBatchChangeMapper.getEmpBatchChangeSave(empBatchChangeVO);
            }
        }

        return storeCnt;
    }

    /** 임시테이블 삭제 */
    @Override
    public int getEmpExcelUploadCheckDeleteAll(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        empBatchChangeVO.setSessionId(sessionInfoVO.getUserId());

        result += empBatchChangeMapper.getEmpExcelUploadCheckDeleteAll(empBatchChangeVO);

        return result;
    }

    /** 임시테이블 검증 후 저장 */
    @Override
    public int getEmpExcelUploadCheckSave(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(EmpBatchChangeVO empBatchChangeVO : empBatchChangeVOs) {

            empBatchChangeVO.setRegDt(currentDt);
            empBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            empBatchChangeVO.setModDt(currentDt);
            empBatchChangeVO.setModId(sessionInfoVO.getUserId());

            empBatchChangeVO.setSessionId(sessionInfoVO.getUserId());
            empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            empBatchChangeVO.setSeq(i);

            empBatchChangeVO.setResult("검증전");

            // 사원코드
            if(empBatchChangeMapper.getEmpNoChk(empBatchChangeVO) > 0){

                // 지사코드
                if(empBatchChangeVO.getBranchCd() != null && !"".equals(empBatchChangeVO.getBranchCd())){
                    empBatchChangeVO.setBranchCd(empBatchChangeMapper.getBranchCdChk(empBatchChangeVO));
                }
                // 팀별
                if(empBatchChangeVO.getMomsTeam() != null && !"".equals(empBatchChangeVO.getMomsTeam())){
                    empBatchChangeVO.setNmcodeGrpCd("151");
                    empBatchChangeVO.setMomsTeam(empBatchChangeMapper.getHqNmcodeChk(empBatchChangeVO));
                }
                // AC점포별
                if(empBatchChangeVO.getMomsAcShop() != null && !"".equals(empBatchChangeVO.getMomsAcShop())){
                    empBatchChangeVO.setNmcodeGrpCd("152");
                    empBatchChangeVO.setMomsAcShop(empBatchChangeMapper.getHqNmcodeChk(empBatchChangeVO));
                }
                // 지역구분
                if(empBatchChangeVO.getMomsAreaFg() != null && !"".equals(empBatchChangeVO.getMomsAreaFg())){
                    empBatchChangeVO.setNmcodeGrpCd("153");
                    empBatchChangeVO.setMomsAreaFg(empBatchChangeMapper.getHqNmcodeChk(empBatchChangeVO));
                }
                // 상권
                if(empBatchChangeVO.getMomsCommercial() != null && !"".equals(empBatchChangeVO.getMomsCommercial())){
                    empBatchChangeVO.setNmcodeGrpCd("154");
                    empBatchChangeVO.setMomsCommercial(empBatchChangeMapper.getHqNmcodeChk(empBatchChangeVO));
                }
                // 점포유형
                if(empBatchChangeVO.getMomsShopType() != null && !"".equals(empBatchChangeVO.getMomsShopType())){
                    empBatchChangeVO.setNmcodeGrpCd("155");
                    empBatchChangeVO.setMomsShopType(empBatchChangeMapper.getHqNmcodeChk(empBatchChangeVO));
                }
                // 매장관리타입
                if(empBatchChangeVO.getMomsStoreManageType() != null && !"".equals(empBatchChangeVO.getMomsStoreManageType())){
                    empBatchChangeVO.setNmcodeGrpCd("156");
                    empBatchChangeVO.setMomsStoreManageType(empBatchChangeMapper.getHqNmcodeChk(empBatchChangeVO));
                }
            } else {
                empBatchChangeVO.setResult("존재하지 않는 사원입니다");
            }

            // 검증결과 저장
            storeCnt += empBatchChangeMapper.getEmpExcelUploadCheckSave(empBatchChangeVO);
            i++;
        }

        return storeCnt;
    }

    /** 검증결과 조회 */
    @Override
    public List<DefaultMap<String>> getEmpExcelUploadCheckList(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO) {

        empBatchChangeVO.setSessionId(sessionInfoVO.getUserId());
        empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return empBatchChangeMapper.getEmpExcelUploadCheckList(empBatchChangeVO);
    }

    /** 검증결과 저장 */
    @Override
    public int getEmpExcelUploadCheckSaveAdd(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(EmpBatchChangeVO empBatchChangeVO : empBatchChangeVOs) {

            empBatchChangeVO.setRegDt(currentDt);
            empBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            empBatchChangeVO.setModDt(currentDt);
            empBatchChangeVO.setModId(sessionInfoVO.getUserId());

            empBatchChangeVO.setSessionId(sessionInfoVO.getUserId());
            empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 사원코드
            if(empBatchChangeMapper.getEmpNoChk(empBatchChangeVO) > 0){
                // 뭔가 추가되면 여기에 검증로직 넣으면 됨
            } else {
                empBatchChangeVO.setResult("존재하지 않는 사원입니다");
            }

            if (empBatchChangeVO.getResult() == null || empBatchChangeVO.getResult() == "") {
                empBatchChangeVO.setResult("검증성공");
            }

            // 검증결과 저장
            storeCnt += empBatchChangeMapper.getEmpExcelUploadCheckSave(empBatchChangeVO);
            i++;
        }

        return storeCnt;
    }

    /** 사원정보 저장 */
    @Override
    public int getSimpleSave(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpBatchChangeVO empBatchChangeVO : empBatchChangeVOs) {

            empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            empBatchChangeVO.setRegDt(currentDt);
            empBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            empBatchChangeVO.setModDt(currentDt);
            empBatchChangeVO.setModId(sessionInfoVO.getUserId());

            if(("검증성공").equals(empBatchChangeVO.getResult())){

                // 저장
                storeCnt += empBatchChangeMapper.getEmpBatchChangeSave(empBatchChangeVO);

                // 저장 완료된 검증결과 삭제
                empBatchChangeMapper.getEmpExcelUploadCheckDelete(empBatchChangeVO);
            }

        }

        return storeCnt;
    }

    /** 사원권한복사 */
    @Override
    public int copyAuthorExcept(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpBatchChangeVO empBatchChangeVO : empBatchChangeVOs) {

            empBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            empBatchChangeVO.setRegDt(currentDt);
            empBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            empBatchChangeVO.setModDt(currentDt);
            empBatchChangeVO.setModId(sessionInfoVO.getUserId());

            // targetEmpNo : 복사 대상이 되는 사원
            // originalEmpNo : 복사할 기준이 되는 사원
            // 1. 메뉴 권한 복사
            int authGrpCopy = empBatchChangeMapper.copyAuth(empBatchChangeVO);
            if(authGrpCopy <= 0) {
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 2. 기존 메뉴권한 예외값 삭제
            empBatchChangeMapper.removeAuthAll(empBatchChangeVO);

            // 3. 메뉴 권한 예외값이 있는지 확인 후, 복사
            int authExpCopy = 0;
            List<DefaultMap<String>> excepList = empBatchChangeMapper.exceptMenu(empBatchChangeVO);

            if(excepList != null && excepList.size() > 0){

                for (int i = 0; i < excepList.size(); i++) {

                    empBatchChangeVO.setResrceCd(excepList.get(i).getStr("resrceCd"));

                    if("E".equals(excepList.get(i).getStr("incldExcldFg"))){
                        empBatchChangeVO.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                    }else{
                        empBatchChangeVO.setIncldExcldFg(IncldExcldFg.INCLUDE);
                    }
                    empBatchChangeVO.setUseYn(excepList.get(i).getStr("useYn"));

                    int result = empBatchChangeMapper.copyAuthExcp(empBatchChangeVO);
                    if(result <= 0){
                        throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    } else {
                        authExpCopy ++;
                    }
                }
            }
        }

        return storeCnt;
    }

}
