package kr.co.solbipos.adi.service.etc.kitchenmemo;

import java.util.List;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemo;
import kr.co.solbipos.application.domain.login.SessionInfo;

/**
 * 
 * @author 김지은
 *
 */
public interface KitchenMemoService {
    
    /** 주방메모 조회 */
    <E> List<E> selectKitchenMemo(SessionInfo sessionInfo);
 
    int selectKitchenMemoCnt(KitchenMemo kitchenMemo);
    
    ///** 주방메모 저장 */
    //void saveKitchenMemo(KitchenMemo[] kitchenMemo);
    //String saveKitchenMemo(KitchenMemo[] kitchenMemo);
    void insertKitchenMemo(KitchenMemo kitchenMemo);
    void updateKitchenMemo(KitchenMemo kitchenMemo);
    void deleteKitchenMemo(KitchenMemo kitchenMemo);
}
