package kr.co.solbipos.sale.prod.prodStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.prodStore.service.ProdStoreVO;

import java.util.List;

/**
 * @Class Name : ProdStoreService.java
 * @Description : 맘스터치 > 승인관리2 > 상품별 점포매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdStoreService {

    /** 조회 */
    List<DefaultMap<Object>> getProdStoreList(ProdStoreVO prodStoreVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getProdStoreExcelList(ProdStoreVO prodStoreVO, SessionInfoVO sessionInfoVO);

}