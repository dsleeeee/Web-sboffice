package kr.co.solbipos.base.price.salePriceResve.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SalePriceResveService.java
 * @Description : 기초관리 - 가격관리 - 가격예약
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.05  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SalePriceResveService {

    /** 가격예약(본사판매가) 리스트 조회 */
    List<DefaultMap<String>> getHqSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO);

    /** 가격예약(본사판매가) 추가 */
    int saveHqSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO);

    /** 가격예약(본사판매가) 수정 */
    int modHqSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO);

    /** 가격예약(본사판매가) 상품가격정보 조회 */
    List<DefaultMap<String>> getHqSalePriceInfo(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO);

    /** 가격예약(매장판매가) [상품별 판매가관리] 리스트 조회 */
    List<DefaultMap<String>> getStoreProdSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO);

    /** 가격예약(매장판매가) [매장별 판매가관리] 리스트 조회 */
    List<DefaultMap<String>> getStoreStoreSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO);

    /** 가격예약(매장판매가) 추가 */
    int saveStoreProdSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO);

    /** 가격예약(매장판매가) 수정 */
    int modStoreProdSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO);

    /** 가격예약(매장판매가) 상품가격정보 조회 */
    List<DefaultMap<String>> getStoreSalePriceInfo(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO);

    /** 가격예약(판매가관리) 리스트 조회 */
    List<DefaultMap<String>> getSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO);

    /** 가격예약(판매가관리) 추가 */
    int saveSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO);

    /** 가격예약(판매가관리) 수정 */
    int modSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO);

    /** 가격예약(판매가관리) 상품가격정보 조회 */
    List<DefaultMap<String>> getSalePriceInfo(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO);

    /** 가격예약(본사판매가) 엑셀업로드 탭 - 판매가 저장 */
    int getHqSalePriceResveExcelUploadSave(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO);
}
