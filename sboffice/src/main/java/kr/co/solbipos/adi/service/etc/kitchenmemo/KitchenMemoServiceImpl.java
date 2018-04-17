package kr.co.solbipos.adi.service.etc.kitchenmemo;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemo;
import kr.co.solbipos.adi.persistence.etc.kitchenmemo.KitchenMemoMapper;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import kr.co.solbipos.service.session.SessionService;

@Service
public class KitchenMemoServiceImpl implements KitchenMemoService {

    @Autowired
    SessionService sessionService;

    @Autowired
    KitchenMemoMapper kichenMemoMapper;
    
    @Override
    public <E> List<E> selectKitchenMemo(SessionInfo sessionInfo) {
        return kichenMemoMapper.selectKitchenMemo(sessionInfo);
    }

    @Override
    public int save(KitchenMemo[] kitchenMemos, SessionInfo sessionInfo) {
        
        int procCnt = 0;
        String insertDt = currentDateTimeString();
        
        for(KitchenMemo kitchenMemo : kitchenMemos){
            kitchenMemo.setStoreCd(sessionInfo.getOrgnCd());
            kitchenMemo.setRegId(sessionInfo.getUserId());
            kitchenMemo.setRegDt(insertDt);
            kitchenMemo.setModId(sessionInfo.getUserId());
            kitchenMemo.setModDt(insertDt);

            if(kitchenMemo.getStatus() == GridDataFg.INSERT) {
                procCnt += kichenMemoMapper.insertKitchenMemo(kitchenMemo);
            }
            else if(kitchenMemo.getStatus() == GridDataFg.UPDATE) {
                procCnt += kichenMemoMapper.updateKitchenMemo(kitchenMemo);
            }
            else if(kitchenMemo.getStatus() == GridDataFg.DELETE) {
                procCnt += kichenMemoMapper.deleteKitchenMemo(kitchenMemo);
            }
        }
        return procCnt;
    }
    
    @Override
    public int selectKitchenMemoCnt(KitchenMemo kitchenMemo) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo();

        kitchenMemo.setStoreCd(sessionInfo.getOrgnCd());
        kitchenMemo.setRegId(sessionInfo.getUserId());
        kitchenMemo.setModId(sessionInfo.getUserId());
        
        return kichenMemoMapper.selectKitchenMemoCnt(kitchenMemo);
    }
}
