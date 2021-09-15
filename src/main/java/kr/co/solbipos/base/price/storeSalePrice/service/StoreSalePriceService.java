package kr.co.solbipos.base.price.storeSalePrice.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;

import java.util.List;

/**
 * @Class Name : StoreSalePriceService.java
 * @Description : 기초관리 - 가격관리 - 매장판매가현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.10  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreSalePriceService {

    /** 상품별 가격정보 조회 */
    List<DefaultMap<String>> getSalePriceList(StoreSalePriceVO storeSalePriceVO, SessionInfoVO sessionInfoVO);
    
    /** 상품별 가격정보 엑셀다운로드 */
    List<DefaultMap<String>> getSalePriceExcelList(StoreSalePriceVO storeSalePriceVO, SessionInfoVO sessionInfoVO);

}
