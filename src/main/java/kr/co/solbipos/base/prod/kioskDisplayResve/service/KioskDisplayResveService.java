package kr.co.solbipos.base.prod.kioskDisplayResve.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : KioskDisplayResveService.java
 * @Description : 기초관리 - 상품관리2 - 비노출관리(예약)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.24  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface KioskDisplayResveService {

    // 예약 내역 조회
    List<DefaultMap<String>> getKioskDisplayResve(KioskDisplayResveVO kioskDisplayResveVO, SessionInfoVO sessionInfoVO);

    // 상품 내역 조회
    List<DefaultMap<String>> getProdList(KioskDisplayResveVO kioskDisplayResveVO, SessionInfoVO sessionInfoVO);

    // 비노출관리 예약
    int saveKioskDisplayResve(KioskDisplayResveVO[] kioskDisplayResveVOs, SessionInfoVO sessionInfoVO);
    
    // 예약 삭제
    int deleteKioskDisplayResve(KioskDisplayResveVO[] kioskDisplayResveVOs, SessionInfoVO sessionInfoVO);

    // 예약 수정
    int modKioskDisplayResve(KioskDisplayResveVO[] kioskDisplayResveVOs, SessionInfoVO sessionInfoVO);


}
