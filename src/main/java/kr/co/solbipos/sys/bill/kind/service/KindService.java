package kr.co.solbipos.sys.bill.kind.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : KindService.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 종류
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface KindService {
    
    /** 출력물종류 목록 조회 */
    List<DefaultMap<String>> getPrintList(KindVO kindVO);
    
    /** 출력물매핑 목록 조회 */
    List<DefaultMap<String>> getPrintMapngList(KindVO kindVO);

    /** 출력물매핑 목록 팝업 조회 */
    List<DefaultMap<String>> getPrintMapngUnUsedList(KindVO kindVO);

    /** 출력물종류 목록 저장 */
    int savePrintList(KindVO[] kindVOs, SessionInfoVO sessionInfoVO);
    
    /** 출력물매핑 목록 저장 */
    int savePrintMapngList(KindVO[] kindVOs, SessionInfoVO sessionInfoVO);

}
