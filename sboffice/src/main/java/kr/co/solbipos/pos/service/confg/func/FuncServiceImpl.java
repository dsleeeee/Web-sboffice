package kr.co.solbipos.pos.service.confg.func;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.enums.UseYn;
import kr.co.solbipos.exception.JsonException;
import kr.co.solbipos.pos.domain.confg.func.Func;
import kr.co.solbipos.pos.persistence.confg.func.FuncMapper;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;

@Service
public class FuncServiceImpl implements FuncService{

    
    @Autowired
    FuncMapper mapper;

    @Autowired
    MessageService messageService;
    
    @Override
    public List<DefaultMap<String>> list(Func func) {
        return mapper.getFuncList(func);
    }

    @Override
    public int save(Func[] funcs, SessionInfo sessionInfo) {

        int procCnt = 0;
        
        String insertDt = currentDateTimeString();

        for(Func func: funcs) {

            func.setRegDt(insertDt);
            func.setModDt(insertDt);
            func.setRegId(sessionInfo.getUserId());
            func.setModId(sessionInfo.getUserId());
            
            if(func.getStatus() == GridDataFg.INSERT) {
                String fnKeyNo = func.getFnkeyFg() + func.getFnkeyNo();
                
                func.setFnkeyNo(fnKeyNo);
                func.setUseYn("Y");
                procCnt += mapper.insertFunc(func);
            }
            else if(func.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateFunc(func);
            }
            else if(func.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteFunc(func);
            }
        }
        
        if(procCnt == funcs.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
