package kr.co.solbipos.mobile.stock.curr.hqCurr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;

import java.util.List;
/**
 * @Class Name : MobileHqCurrService.java
 * @Description : (모바일)재고현황 > 현재고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileHqCurrService {

    /** 현재고현황 - 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getHqCurrList(MobileHqCurrVO mobileHqCurrVO, SessionInfoVO sessionInfoVO);

    /** 현재고현황 - 현재고현황 상세리스트 조회 */
    List<DefaultMap<String>> gethqCurrDtlList(MobileHqCurrVO mobileHqCurrVO, SessionInfoVO sessionInfoVO);

    /** 현재고현황 - 현재고현황 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getHqCurrExcelList(MobileHqCurrVO mobileHqCurrVO, SessionInfoVO sessionInfoVO);
}
