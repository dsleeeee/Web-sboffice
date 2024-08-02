package kr.co.solbipos.mobile.stock.status.currUnity.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : MobileCurrUnityService.java
 * @Description : (모바일)재고현황 > 본사매장통합현재고
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
public interface MobileCurrUnityService {

    /** 본사매장통합현재고 - 본사매장통합현재고 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO);
    /** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityHqDtlList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO);
    /** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityStoreDtlList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO);

    /** 본사매장통합현재고 - 본사매장통합현재고 전체 엑셀 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityExcelList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO);
    /** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 전체 엑셀 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityHqDtlExcelList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO);
    /** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 전체 엑셀 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityStoreDtlExcelList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO);
}
