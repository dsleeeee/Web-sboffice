package kr.co.solbipos.adi.service.etc.kitchenmemo;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemo;
import kr.co.solbipos.adi.persistence.etc.kitchenmemo.KitchenMemoMapper;
import kr.co.solbipos.application.domain.login.SessionInfo;
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
    public int selectKitchenMemoCnt(KitchenMemo kitchenMemo) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo();

        kitchenMemo.setStoreCd(sessionInfo.getOrgnCd());
        kitchenMemo.setRegId(sessionInfo.getUserId());
        kitchenMemo.setModId(sessionInfo.getUserId());
        
        return kichenMemoMapper.selectKitchenMemoCnt(kitchenMemo);
    }

    @Override
    public void insertKitchenMemo(KitchenMemo kitchenMemo) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo();

        String currentTime = currentDateTimeString();
        
        kitchenMemo.setStoreCd(sessionInfo.getOrgnCd());
        kitchenMemo.setRegId(sessionInfo.getUserId());
        kitchenMemo.setRegDt(currentTime);
        kitchenMemo.setModId(sessionInfo.getUserId());
        kitchenMemo.setModDt(currentTime);
        
        kichenMemoMapper.insertKitchenMemo(kitchenMemo);
    }

    @Override
    public void updateKitchenMemo(KitchenMemo kitchenMemo) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo();

        String currentTime = currentDateTimeString();
        
        kitchenMemo.setStoreCd(sessionInfo.getOrgnCd());
        kitchenMemo.setRegId(sessionInfo.getUserId());
        kitchenMemo.setRegDt(currentTime);
        kitchenMemo.setModId(sessionInfo.getUserId());
        kitchenMemo.setModDt(currentTime);
        
        kichenMemoMapper.updateKitchenMemo(kitchenMemo);
    }

    @Override
    public void deleteKitchenMemo(KitchenMemo kitchenMemo) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo();
        
        kitchenMemo.setStoreCd(sessionInfo.getOrgnCd());
        
        kichenMemoMapper.deleteKitchenMemo(kitchenMemo);
    }
}
