package kr.co.solbipos.adi.persistence.etc.kitchenmemo;

import java.util.List;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemo;
import kr.co.solbipos.application.domain.login.SessionInfo;

/**
 * 부가서비스 > 주망메모관리
 * 
 * @author 김지은
 */
public interface KitchenMemoMapper {
    
    /** 주방메모 조회 */
    public <E> List<E> selectKitchenMemo(SessionInfo sessionInfo);

    /** 주방메모 건수 조회 */
    public int selectKitchenMemoCnt(KitchenMemo memo);
    
    /** 주방메모 저장 */
    public int save(KitchenMemo[] kitchenMemos, SessionInfo sessionInfo);
    
    /** 주방메모 등록 */
//    void insertKitchenMemo(KitchenMemo kitchenMemo);
    int insertKitchenMemo(KitchenMemo kitchenMemo);
    
    /** 주방메모 수정 */
//    void updateKitchenMemo(KitchenMemo kitchenMemo);
    int updateKitchenMemo(KitchenMemo kitchenMemo);
    
    /** 주방메모 삭제 */
//    void deleteKitchenMemo(KitchenMemo kitchenMemo);
    int deleteKitchenMemo(KitchenMemo kitchenMemo);

}
