package kr.co.solbipos.base.prod.touchKeyResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.touchKeyResve.service.TouchKeyResveService;
import kr.co.solbipos.base.prod.touchKeyResve.service.TouchKeyResveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("touchKeyResveService")
public class TouchKeyResveServiceImpl implements TouchKeyResveService {
    private final TouchKeyResveMapper touchKeyResveMapper;

    @Autowired
    public TouchKeyResveServiceImpl(TouchKeyResveMapper touchKeyResveMapper) {
        this.touchKeyResveMapper = touchKeyResveMapper;
    }


    /** 일별 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getTouchKeyResveList(TouchKeyResveVO touchKeyResveVO, SessionInfoVO sessionInfoVO) {
        touchKeyResveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyResveVO.setEmpNo(sessionInfoVO.getEmpNo());
        touchKeyResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            touchKeyResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (touchKeyResveVO.getStoreHqBrandCd() == "" || touchKeyResveVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = touchKeyResveVO.getUserBrands().split(",");
                touchKeyResveVO.setUserBrandList(userBrandList);
            }
        }

        return touchKeyResveMapper.getTouchKeyResveList(touchKeyResveVO);
    }

    // 추가 팝업 조회
    @Override
    public List<DefaultMap<Object>> getTouchKeyResveAddList(TouchKeyResveVO touchKeyResveVO, SessionInfoVO sessionInfoVO) {
        touchKeyResveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyResveVO.setEmpNo(sessionInfoVO.getEmpNo());
        touchKeyResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            touchKeyResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (touchKeyResveVO.getStoreHqBrandCd() == "" || touchKeyResveVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = touchKeyResveVO.getUserBrands().split(",");
                touchKeyResveVO.setUserBrandList(userBrandList);
            }
        }

        return touchKeyResveMapper.getTouchKeyResveAddList(touchKeyResveVO);
    }

    /** 예약 저장 */
    @Override
    public int saveTouchKeyResve(TouchKeyResveVO[] touchKeyResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(TouchKeyResveVO touchKeyResveVO : touchKeyResveVOs){

            touchKeyResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            touchKeyResveVO.setRegDt(currentDt);
            touchKeyResveVO.setRegId(sessionInfoVO.getUserId());
            touchKeyResveVO.setModDt(currentDt);
            touchKeyResveVO.setModId(sessionInfoVO.getUserId());

            result += touchKeyResveMapper.saveTouchKeyResve(touchKeyResveVO);
        }

        return result;
    }

    /** 예약 삭제 */
    @Override
    public int deleteTouchKeyResve(TouchKeyResveVO[] touchKeyResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(TouchKeyResveVO touchKeyResveVO : touchKeyResveVOs){
            result += touchKeyResveMapper.deleteTouchKeyResve(touchKeyResveVO);
        }

        return result;
    }

    /** 예약 수정 */
    @Override
    public int modTouchKeyResve(TouchKeyResveVO[] touchKeyResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(TouchKeyResveVO touchKeyResveVO : touchKeyResveVOs){

            touchKeyResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            touchKeyResveVO.setRegDt(currentDt);
            touchKeyResveVO.setRegId(sessionInfoVO.getUserId());
            touchKeyResveVO.setModDt(currentDt);
            touchKeyResveVO.setModId(sessionInfoVO.getUserId());
            // 기존 예약 삭제
            touchKeyResveMapper.deleteTouchKeyResve(touchKeyResveVO);
            // 예약 저장
            result += touchKeyResveMapper.saveTouchKeyResve(touchKeyResveVO);
        }

        return result;
    }

}