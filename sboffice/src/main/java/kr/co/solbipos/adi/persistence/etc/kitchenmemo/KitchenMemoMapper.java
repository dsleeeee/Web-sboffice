package kr.co.solbipos.adi.persistence.etc.kitchenmemo;

import java.util.List;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemoVO;
import kr.co.solbipos.application.domain.login.SessionInfoVO;

/**
 * 부가서비스 > 주망메모관리
 *
 * @author 김지은
 */
public interface KitchenMemoMapper {

    /** 주방메모 조회 */
    public <E> List<E> selectKitchenMemo(SessionInfoVO sessionInfoVO);

    /** 주방메모 건수 조회 */
    public int selectKitchenMemoCnt(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 저장 */
    public int save(KitchenMemoVO[] kitchenMemoVOs, SessionInfoVO sessionInfoVO);

    /** 주방메모 등록 */
//    void insertKitchenMemo(KitchenMemo kitchenMemoVO);
    int insertKitchenMemo(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 수정 */
//    void updateKitchenMemo(KitchenMemo kitchenMemoVO);
    int updateKitchenMemo(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 삭제 */
//    void deleteKitchenMemo(KitchenMemo kitchenMemoVO);
    int deleteKitchenMemo(KitchenMemoVO kitchenMemoVO);

}
