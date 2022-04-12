package kr.co.solbipos.sale.status.dlvr.prodStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdStatusService.java
 * @Description : 매출관리 - 매출현황 - 배달상품현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.11  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdStatusService {

    /** 배달상품현황 리스트 조회 */
    List<DefaultMap<String>> getProdStatusList(ProdStatusVO prodStatusVO, SessionInfoVO sessionInfoVO);
}
