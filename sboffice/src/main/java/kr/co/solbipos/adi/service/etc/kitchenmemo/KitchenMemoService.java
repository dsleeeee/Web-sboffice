package kr.co.solbipos.adi.service.etc.kitchenmemo;

import java.util.List;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemo;
import kr.co.solbipos.application.domain.login.SessionInfo;

/**
 * 부가서비스 > 주방메모관리
 * 
 * @author 김지은
 */
public interface KitchenMemoService {
    
    /** 주방메모 조회 */
    <E> List<E> selectKitchenMemo(SessionInfo sessionInfo);
 
    /** 주방메모 건수 조회 */
    int selectKitchenMemoCnt(KitchenMemo kitchenMemo);
    
    /** 주방메모 등록 */
    void insertKitchenMemo(KitchenMemo kitchenMemo);
    
    /** 주방메모 수정 */
    void updateKitchenMemo(KitchenMemo kitchenMemo);
    
    /** 주방메모 삭제 */
    void deleteKitchenMemo(KitchenMemo kitchenMemo);
}
