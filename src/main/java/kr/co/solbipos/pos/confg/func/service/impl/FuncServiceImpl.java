package kr.co.solbipos.pos.confg.func.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.func.service.FuncService;
import kr.co.solbipos.pos.confg.func.service.FuncStoreVO;
import kr.co.solbipos.pos.confg.func.service.FuncVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : FuncServiceImpl.java
 * @Description : 포스관리 > POS 설정관리 > POS 기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("funcService")
public class FuncServiceImpl implements FuncService {

    private final FuncMapper mapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public FuncServiceImpl(FuncMapper mapper, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
    }

    /** 기능구분상세 조회 */
    @Override
    public List<DefaultMap<String>> list(FuncVO funcVO) {
        return mapper.getFuncList(funcVO);
    }

    /** 기능구분상세 저장 */
    @Override
    public int save(FuncVO[] funcVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String insertDt = currentDateTimeString();

        for(FuncVO funcVO: funcVOs) {

            funcVO.setRegDt(insertDt);
            funcVO.setModDt(insertDt);
            funcVO.setRegId(sessionInfoVO.getUserId());
            funcVO.setModId(sessionInfoVO.getUserId());

            if(funcVO.getStatus() == GridDataFg.INSERT) {
                String fnKeyNo = funcVO.getFnkeyFg() + funcVO.getFnkeyNo();

                funcVO.setFnkeyNo(fnKeyNo);
                funcVO.setUseYn(true);
                procCnt += mapper.insertFunc(funcVO);
            }
            else if(funcVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateFunc(funcVO);

                // 기능 상세정보 수정 프로시져 호출
                FuncVO func = new FuncVO();
                String storeResult = mapper.updateStoreFuncKey(funcVO);
                func.setResult(storeResult);

            }
            else if(funcVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteFunc(funcVO);

                // 기능키 삭제시, 적용 매장, 매장에 적용된 기능키도 함께 삭제
                procCnt += mapper.deleteCmmStoreFunc(funcVO);
                procCnt += mapper.deleteAllStoreFunc(funcVO);

                // 모든 포스에 적용된 기능키 데이터 삭제
                procCnt += mapper.deleteAllPosFunc(funcVO);
            }
        }

//        if(procCnt == funcVOs.length) {
//            return procCnt;
//        } else {
//            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }

        return procCnt;
    }

    /** 기능구분 등록매장 조회 */
    @Override
    public List<DefaultMap<String>> getFunStoreList(FuncStoreVO funcStoreVO) {
        return mapper.getStoreList(funcStoreVO);
    }

    /** 기능구분 적용매장 등록 및 삭제 */
    @Override
    public int saveFuncStore(FuncStoreVO[] funcStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(FuncStoreVO funcStoreVO : funcStoreVOs) {

            funcStoreVO.setRegDt(dt);
            funcStoreVO.setRegId(sessionInfoVO.getUserId());
            funcStoreVO.setModDt(dt);
            funcStoreVO.setModId(sessionInfoVO.getUserId());

            if(funcStoreVO.getStatus() == GridDataFg.INSERT) {
                procCnt += mapper.insertFuncStore(funcStoreVO);

                FuncStoreVO funStore = new FuncStoreVO();
                String storeResult = mapper.insertStoreFuncKey(funcStoreVO);
                funStore.setResult(storeResult);

                FuncStoreVO funcPos = new FuncStoreVO();
                String posResult = mapper.insertPosFuncKey(funcStoreVO);
                funcPos.setResult(posResult);

            } else if(funcStoreVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteFuncStore(funcStoreVO);

                FuncStoreVO funStore = new FuncStoreVO();
                String storeResult = mapper.deleteStoreFuncKey(funcStoreVO);
                funStore.setResult(storeResult);

                FuncStoreVO funcPos = new FuncStoreVO();
                String posResult = mapper.deletePosFuncKey(funcStoreVO);
                funcPos.setResult(posResult);
            }
        }
        return procCnt;
    }

    /** 매장리스트 */
    @Override
    public List<DefaultMap<String>> selectStoreList(FuncStoreVO funcStoreVO, SessionInfoVO sessionInfoVO) {
        return mapper.selectStoreList(funcStoreVO);
    }

    /** 매장 기본 기능키 셋팅 */
    @Override
    public int saveDefaultFunc(FuncStoreVO funcStoreVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        funcStoreVO.setRegDt(dt);
        funcStoreVO.setRegId(sessionInfoVO.getUserId());
        funcStoreVO.setModDt(dt);
        funcStoreVO.setModId(sessionInfoVO.getUserId());

        // 1-1. 기존 포스기능키 적용매장 정보 삭제
        mapper.delPosFnkeyStore(funcStoreVO);
        // 1-2.기본 포스기능키 등록 (적용매장 정보 등록)
        procCnt += mapper.regDefaultFuncStore(funcStoreVO);
        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 2-1. 기존 매장별 포스기능키 정보 삭제
        mapper.delStoreFnkey(funcStoreVO);
        // 2-2.기본 포스기능키 등록 (매장별 포스기능키 등록)
        procCnt += mapper.regDefaultFuncStoreFnkey(funcStoreVO);
        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 3-1. 기존 포스별 포스기능키 정보 삭제
        mapper.delPosFnkey(funcStoreVO);
        // 3-2.기본 포스기능키 등록 포스별 포스기능키 등록)
        procCnt += mapper.regDefaultFuncPosFnkey(funcStoreVO);
        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return procCnt;
    }
}
