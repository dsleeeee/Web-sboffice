package kr.co.solbipos.sale.status.reportKwu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ReportKwuService.java
 * @Description : 광운대 > 리포트 > 분류별결제수단별 매출내역
 *                광운대 > 리포트 > 결제수단별 매출내역
 *                광운대 > 리포트 > 신용카드 매출내역
 *                광운대 > 리포트 > 현금영수증 발행내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.13  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface ReportKwuService {

   /** 분류별결제수단별 매출내역 - 리스트 조회 */
   List<DefaultMap<String>> getProdClassPayFgSaleList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO);

   /** 결제수단별 매출내역 - 리스트 조회 */
   List<DefaultMap<String>> getPayFgSaleList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO);

   /** 신용카드 매출내역 - 리스트 조회 */
   List<DefaultMap<String>> getPayCardSaleList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO);

   /** 현금영수증 발행내역 - 리스트 조회 */
   List<DefaultMap<String>> getCashBillInfoList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO);

}
