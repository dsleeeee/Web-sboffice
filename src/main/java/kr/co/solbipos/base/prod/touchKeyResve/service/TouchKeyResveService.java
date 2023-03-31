package kr.co.solbipos.base.prod.touchKeyResve.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface TouchKeyResveService {

    /** 리스트 조회 */
    List<DefaultMap<Object>> getTouchKeyResveList(TouchKeyResveVO touchKeyResveVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getTouchKeyResveAddList(TouchKeyResveVO touchKeyResveVO, SessionInfoVO sessionInfoVO);
    
    /** 판매터치키(예약) 저장 */
    int saveTouchKeyResve(TouchKeyResveVO[] touchKeyResveVOs, SessionInfoVO sessionInfoVO);
    /** 판매터치키(예약) 삭제 */
    int deleteTouchKeyResve(TouchKeyResveVO[] touchKeyResveVOs, SessionInfoVO sessionInfoVO);
    /** 판매터치키(예약) 수정 */
    int modTouchKeyResve(TouchKeyResveVO[] touchKeyResveVOs, SessionInfoVO sessionInfoVO);
}
