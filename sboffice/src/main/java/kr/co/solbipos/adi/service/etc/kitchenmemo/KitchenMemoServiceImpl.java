package kr.co.solbipos.adi.service.etc.kitchenmemo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemo;
import kr.co.solbipos.adi.persistence.etc.kitchenmemo.KitchenMemoMapper;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.service.session.SessionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        return kichenMemoMapper.selectKitchenMemoCnt(kitchenMemo);
    }

    @Override
    public void insertKitchenMemo(KitchenMemo kitchenMemo) {
        kichenMemoMapper.insertKitchenMemo(kitchenMemo);
    }

    @Override
    public void updateKitchenMemo(KitchenMemo kitchenMemo) {
        kichenMemoMapper.updateKitchenMemo(kitchenMemo);
    }

    @Override
    public void deleteKitchenMemo(KitchenMemo kitchenMemo) {
        kichenMemoMapper.deleteKitchenMemo(kitchenMemo);
    }
}
