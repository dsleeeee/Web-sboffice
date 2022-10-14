package kr.co.solbipos.sale.store.storeAvg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeAvg.service.StoreAvgVO;

import java.util.List;

/**
 * @Class Name : StoreAvgService.java
 * @Description : 맘스터치 > 점포매출 > 점포별 매출 평균 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreAvgService {

    /** 조회 */
    List<DefaultMap<Object>> getStoreAvgList(StoreAvgVO storeAvgeVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getStoreAvgExcelList(StoreAvgVO storeAvgeVO, SessionInfoVO sessionInfoVO);

}