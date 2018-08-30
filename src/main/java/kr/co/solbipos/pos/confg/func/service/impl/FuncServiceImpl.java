package kr.co.solbipos.pos.confg.func.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.solbipos.pos.confg.func.service.FuncStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.func.service.FuncService;
import kr.co.solbipos.pos.confg.func.service.FuncVO;

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

    @Autowired
    FuncMapper mapper;
    @Autowired
    MessageService messageService;

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
                funcVO.setUseYn("Y");
                procCnt += mapper.insertFunc(funcVO);
            }
            else if(funcVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateFunc(funcVO);
            }
            else if(funcVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteFunc(funcVO);
            }
        }

        if(procCnt == funcVOs.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 기능구분 등록매장 조회 */
    @Override
    public Map<String, Object> getFunStoreList(FuncStoreVO funcStoreVO) {

        Map<String, Object> result = new HashMap<String,Object>();

        // 기능키 적용매장 조회
        funcStoreVO.setRegYn("Y");
        List<DefaultMap<String>> regStoreList = mapper.getStoreList(funcStoreVO);

        // 기능키 미적용매장 조회
        funcStoreVO.setRegYn("N");
        List<DefaultMap<String>> noRegStoreList = mapper.getStoreList(funcStoreVO);

        result.put("regStoreList", regStoreList);
        result.put("noRegStoreList", noRegStoreList);

        return result;
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
            } else if(funcStoreVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteFuncStore(funcStoreVO);
            }
        }

        return procCnt;
    }
}
