package kr.co.solbipos.adi.etc.kitchenmemo.service;

import java.util.List;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
* @Class Name : KitchenMemoService.java
* @Description : 부가서비스 > 주방메모관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2015.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface KitchenMemoService {

    /** 주방메모 조회 */
    <E> List<E> selectKitchenMemo(SessionInfoVO sessionInfoVO);

    /** 저장 */
    int save(KitchenMemoVO[] kitchenMemoVOs, SessionInfoVO sessionInfoVO);

    /** 주방메모 건수 조회 */
    int selectKitchenMemoCnt(KitchenMemoVO kitchenMemoVO);
}
