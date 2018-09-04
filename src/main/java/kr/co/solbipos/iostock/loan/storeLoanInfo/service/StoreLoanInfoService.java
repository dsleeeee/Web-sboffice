package kr.co.solbipos.iostock.loan.storeLoanInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreLoanInfoService.java
 * @Description : 수불관리 > 수주관리 > 매장여신관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreLoanInfoService {
    /** 매장별여신상세현황 목록 조회 */
    List<DefaultMap<String>> getStoreLoanInfoList(StoreLoanInfoVO storeLoanInfoVO);
}
