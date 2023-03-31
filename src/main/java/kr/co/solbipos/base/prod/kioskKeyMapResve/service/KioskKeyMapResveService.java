package kr.co.solbipos.base.prod.kioskKeyMapResve.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.kioskKeyMapResve.service.KioskKeyMapResveVO;

import java.util.List;

public interface KioskKeyMapResveService {

    /** 리스트 조회 */
    List<DefaultMap<Object>> getKioskKeyMapResveList(KioskKeyMapResveVO kioskKeyMapResveVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getKioskKeyMapResveAddList(KioskKeyMapResveVO kioskKeyMapResveVO, SessionInfoVO sessionInfoVO);
    
    /** 판매터치키(예약) 저장 */
    int saveKioskKeyMapResve(KioskKeyMapResveVO[] kioskKeyMapResveVOs, SessionInfoVO sessionInfoVO);
    /** 판매터치키(예약) 삭제 */
    int deleteKioskKeyMapResve(KioskKeyMapResveVO[] kioskKeyMapResveVOs, SessionInfoVO sessionInfoVO);
    /** 판매터치키(예약) 수정 */
    int modKioskKeyMapResve(KioskKeyMapResveVO[] kioskKeyMapResveVOs, SessionInfoVO sessionInfoVO);
}
