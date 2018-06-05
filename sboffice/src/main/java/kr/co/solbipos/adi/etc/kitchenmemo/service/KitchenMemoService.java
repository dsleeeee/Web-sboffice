package kr.co.solbipos.adi.etc.kitchenmemo.service;

import java.util.List;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * 부가서비스 > 주방메모관리
 *
 * @author 김지은
 */
public interface KitchenMemoService {

    /** 주방메모 조회 */
    <E> List<E> selectKitchenMemo(SessionInfoVO sessionInfoVO);

    /** 저장 */
    int save(KitchenMemoVO[] kitchenMemoVOs, SessionInfoVO sessionInfoVO);

    /** 주방메모 건수 조회 */
    int selectKitchenMemoCnt(KitchenMemoVO kitchenMemoVO);
}
