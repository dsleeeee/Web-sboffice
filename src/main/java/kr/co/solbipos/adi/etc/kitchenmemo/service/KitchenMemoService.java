package kr.co.solbipos.adi.etc.kitchenmemo.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
* @Class Name : KitchenMemoService.java
* @Description : 부가서비스 > 주방메모관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface KitchenMemoService {

    /** 주방메모 목록 조회 */
    List<DefaultMap<String>> getKitchenMemoList(KitchenMemoVO kitchenMemoVO, SessionInfoVO sessionInfoVO);

    /** 주방메모 저장 */
    int save(KitchenMemoVO[] kitchenMemoVOs, SessionInfoVO sessionInfoVO);

}
