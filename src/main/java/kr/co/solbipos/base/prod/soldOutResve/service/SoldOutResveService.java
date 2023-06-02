package kr.co.solbipos.base.prod.soldOutResve.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.soldOutResve.service.SoldOutResveVO;

import java.util.List;

/**
 * @Class Name : SoldOutResveService.java
 * @Description : 기초관리 - 상품관리2 - 품절관리(예약)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.30  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SoldOutResveService {

    // 예약 내역 조회
    List<DefaultMap<String>> getSoldOutResve(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO);

    // 상품 내역 조회
    List<DefaultMap<String>> getProdList(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO);

    // 품절관리 예약
    int saveSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO);

    // 예약 삭제
    int deleteSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO);

    // 예약 수정
    int modSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO);

    // 예약 내역 조회
    List<DefaultMap<String>> getSdselSoldOutResve(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO);

    // 사이드 상품 내역 조회
    List<DefaultMap<String>> getSdselProdList(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO);

    // 사이드 품절관리 예약
    int saveSdselSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO);

    // 사이드 예약 삭제
    int deleteSdselSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO);

    // 사이드 예약 수정
    int modSdselSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO);

}
