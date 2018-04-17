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
    
    /** 저장 */
    int save(KitchenMemo[] kitchenMemo, SessionInfo sessionInfo);
    
    /** 주방메모 건수 조회 */
    int selectKitchenMemoCnt(KitchenMemo kitchenMemo);
}
