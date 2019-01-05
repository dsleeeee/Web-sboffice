package kr.co.solbipos.pos.install.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.func.service.FuncVO;
import kr.co.solbipos.pos.install.enums.InstallFg;
import kr.co.solbipos.pos.install.service.InstallManageService;
import kr.co.solbipos.pos.install.service.InstallVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : InstallManageServiceImpl.java
 * @Description : 포스관리 > 설치관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.02  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2019.01.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("installManageService")
public class InstallManageServiceImpl implements InstallManageService {

    private final InstallManageMapper installManageMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public InstallManageServiceImpl(InstallManageMapper installManageMapper, MessageService messageService) {
        this.installManageMapper = installManageMapper;
        this.messageService = messageService;
    }

    /** 설치요청 목록 조회 */
    @Override
    public List<DefaultMap<String>> getInstallRequestList(InstallVO installVO) {
        return installManageMapper.getInstallRequestList(installVO);
    }

    /** 포스 목록 */
    @Override
    public List<DefaultMap<String>> getPosList(InstallVO installVO) {
        return installManageMapper.getPosList(installVO);
    }

    /** 설치요청 등록 */
    @Override
    public int saveInstallRequest(InstallVO[] installVOs, SessionInfoVO sessionInfoVO ) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(InstallVO installVO : installVOs){

            installVO.setInstFg(InstallFg.REQ); // 요청
            installVO.setInstReqDt(currentDt);
            installVO.setInstReqId(sessionInfoVO.getUserId());
            installVO.setRegDt(currentDt);
            installVO.setRegId(sessionInfoVO.getUserId());
            installVO.setModDt(currentDt);
            installVO.setModId(sessionInfoVO.getUserId());

            result = installManageMapper.saveInstallRequest(installVO);
        }

        return result;
    }

    //    /** 기능구분상세 조회 */
//    @Override
//    public List<DefaultMap<String>> list(FuncVO funcVO) {
//        return mapper.getFuncList(funcVO);
//    }
//
//    /** 기능구분상세 저장 */
//    @Override
//    public int save(FuncVO[] funcVOs, SessionInfoVO sessionInfoVO) {
//
//        int procCnt = 0;
//
//        String insertDt = currentDateTimeString();
//
//        for(FuncVO funcVO: funcVOs) {
//
//            funcVO.setRegDt(insertDt);
//            funcVO.setModDt(insertDt);
//            funcVO.setRegId(sessionInfoVO.getUserId());
//            funcVO.setModId(sessionInfoVO.getUserId());
//
//            if(funcVO.getStatus() == GridDataFg.INSERT) {
//                String fnKeyNo = funcVO.getFnkeyFg() + funcVO.getFnkeyNo();
//
//                funcVO.setFnkeyNo(fnKeyNo);
//                funcVO.setUseYn(true);
//                procCnt += mapper.insertFunc(funcVO);
//            }
//            else if(funcVO.getStatus() == GridDataFg.UPDATE) {
//                procCnt += mapper.updateFunc(funcVO);
//
//                // 기능 상세정보 수정 프로시져 호출
//                FuncVO func = new FuncVO();
//                String storeResult = mapper.updateStoreFuncKey(funcVO);
//                func.setResult(storeResult);
//
//            }
//            else if(funcVO.getStatus() == GridDataFg.DELETE) {
//                procCnt += mapper.deleteFunc(funcVO);
//
//                // 기능키 삭제시, 적용 매장, 매장에 적용된 기능키도 함께 삭제
//                procCnt += mapper.deleteCmmStoreFunc(funcVO);
//                procCnt += mapper.deleteAllStoreFunc(funcVO);
//
//                // 모든 포스에 적용된 기능키 데이터 삭제
//                procCnt += mapper.deleteAllPosFunc(funcVO);
//            }
//        }
//
//        if(procCnt == funcVOs.length) {
//            return procCnt;
//        } else {
//            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }
//    }
//
//    /** 기능구분 등록매장 조회 */
//    @Override
//    public Map<String, Object> getFunStoreList(FuncStoreVO funcStoreVO) {
//
//        Map<String, Object> result = new HashMap<String,Object>();
//
//        // 기능키 적용매장 조회
//        funcStoreVO.setRegYn("Y");
//        List<DefaultMap<String>> regStoreList = mapper.getStoreList(funcStoreVO);
//
//        // 기능키 미적용매장 조회
//        funcStoreVO.setRegYn("N");
//        List<DefaultMap<String>> noRegStoreList = mapper.getStoreList(funcStoreVO);
//
//        result.put("regStoreList", regStoreList);
//        result.put("noRegStoreList", noRegStoreList);
//
//        return result;
//    }
//
//    /** 기능구분 적용매장 등록 및 삭제 */
//    @Override
//    public int saveFuncStore(FuncStoreVO[] funcStoreVOs, SessionInfoVO sessionInfoVO) {
//
//        int procCnt = 0;
//        String dt = currentDateTimeString();
//
//        for(FuncStoreVO funcStoreVO : funcStoreVOs) {
//
//            funcStoreVO.setRegDt(dt);
//            funcStoreVO.setRegId(sessionInfoVO.getUserId());
//            funcStoreVO.setModDt(dt);
//            funcStoreVO.setModId(sessionInfoVO.getUserId());
//
//            if(funcStoreVO.getStatus() == GridDataFg.INSERT) {
//                procCnt += mapper.insertFuncStore(funcStoreVO);
//
//                FuncStoreVO funStore = new FuncStoreVO();
//                String storeResult = mapper.insertStoreFuncKey(funcStoreVO);
//                funStore.setResult(storeResult);
//
//                FuncStoreVO funcPos = new FuncStoreVO();
//                String posResult = mapper.insertPosFuncKey(funcStoreVO);
//                funcPos.setResult(posResult);
//
//            } else if(funcStoreVO.getStatus() == GridDataFg.DELETE) {
//                procCnt += mapper.deleteFuncStore(funcStoreVO);
//
//                FuncStoreVO funStore = new FuncStoreVO();
//                String storeResult = mapper.deleteStoreFuncKey(funcStoreVO);
//                funStore.setResult(storeResult);
//
//                FuncStoreVO funcPos = new FuncStoreVO();
//                String posResult = mapper.deletePosFuncKey(funcStoreVO);
//                funcPos.setResult(posResult);
//            }
//        }
//        return procCnt;
//    }
}
