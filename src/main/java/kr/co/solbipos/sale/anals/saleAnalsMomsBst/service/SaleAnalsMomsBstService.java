package kr.co.solbipos.sale.anals.saleAnalsMomsBst.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : SaleAnalsMomsBstService.java
 * @Description : 맘스터치 > 매출분석 > 매출분석(사업전략팀)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.27   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SaleAnalsMomsBstService{

    /** 매출분석(사업전략팀) 조회 */
    List<DefaultMap<String>> getSaleAnalsMomsBstList(SaleAnalsMomsBstVO saleAnalsMomsBstVO, SessionInfoVO sessionInfoVO);

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    List<HashMap<String, String>> getDateDiff(SaleAnalsMomsBstVO saleAnalsMomsBstVO);
}
