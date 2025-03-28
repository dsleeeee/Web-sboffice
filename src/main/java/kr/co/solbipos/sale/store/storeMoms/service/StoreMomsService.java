package kr.co.solbipos.sale.store.storeMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreMomsService.java
 * @Description : 맘스터치 > 점포매출 > 점포별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreMomsService {

    /** 조회 */
    List<DefaultMap<Object>> getStoreMomsList(StoreMomsVO storeMomseVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getStoreMomsExcelList(StoreMomsVO storeMomseVO, SessionInfoVO sessionInfoVO);

}