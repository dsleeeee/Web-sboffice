package kr.co.solbipos.pos.confg.func.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;
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

    @Override
    public List<DefaultMap<String>> list(FuncVO funcVO) {
        return mapper.getFuncList(funcVO);
    }

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
}
