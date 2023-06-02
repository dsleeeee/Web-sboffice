package kr.co.solbipos.base.prod.kioskKeyMapResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMapResve.service.KioskKeyMapResveService;
import kr.co.solbipos.base.prod.kioskKeyMapResve.service.KioskKeyMapResveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("kioskKeyMapResveService")
public class KioskKeyMapResveServiceImpl implements KioskKeyMapResveService {
    private final KioskKeyMapResveMapper kioskKeyMapResveMapper;

    @Autowired
    public KioskKeyMapResveServiceImpl(KioskKeyMapResveMapper kioskKeyMapResveMapper) {
        this.kioskKeyMapResveMapper = kioskKeyMapResveMapper;
    }


    /** 일별 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskKeyMapResveList(KioskKeyMapResveVO kioskKeyMapResveVO, SessionInfoVO sessionInfoVO) {
        kioskKeyMapResveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapResveVO.setEmpNo(sessionInfoVO.getEmpNo());
        kioskKeyMapResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            kioskKeyMapResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (kioskKeyMapResveVO.getStoreHqBrandCd() == "" || kioskKeyMapResveVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = kioskKeyMapResveVO.getUserBrands().split(",");
                kioskKeyMapResveVO.setUserBrandList(userBrandList);
            }
        }

        return kioskKeyMapResveMapper.getKioskKeyMapResveList(kioskKeyMapResveVO);
    }

    // 추가 팝업 조회
    @Override
    public List<DefaultMap<Object>> getKioskKeyMapResveAddList(KioskKeyMapResveVO kioskKeyMapResveVO, SessionInfoVO sessionInfoVO) {
        kioskKeyMapResveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapResveVO.setEmpNo(sessionInfoVO.getEmpNo());
        kioskKeyMapResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            kioskKeyMapResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (kioskKeyMapResveVO.getStoreHqBrandCd() == "" || kioskKeyMapResveVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = kioskKeyMapResveVO.getUserBrands().split(",");
                kioskKeyMapResveVO.setUserBrandList(userBrandList);
            }
        }

        return kioskKeyMapResveMapper.getKioskKeyMapResveAddList(kioskKeyMapResveVO);
    }

    /** 예약 저장 */
    @Override
    public int saveKioskKeyMapResve(KioskKeyMapResveVO[] kioskKeyMapResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(KioskKeyMapResveVO kioskKeyMapResveVO : kioskKeyMapResveVOs){

            kioskKeyMapResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapResveVO.setRegDt(currentDt);
            kioskKeyMapResveVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapResveVO.setModDt(currentDt);
            kioskKeyMapResveVO.setModId(sessionInfoVO.getUserId());

            if(kioskKeyMapResveMapper.getKioskKeyMapResveCnt(kioskKeyMapResveVO) == 0) {
                result += kioskKeyMapResveMapper.saveKioskKeyMapResve(kioskKeyMapResveVO);
            }
        }

        return result;
    }

    /** 예약 삭제 */
    @Override
    public int deleteKioskKeyMapResve(KioskKeyMapResveVO[] kioskKeyMapResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(KioskKeyMapResveVO kioskKeyMapResveVO : kioskKeyMapResveVOs){
            result += kioskKeyMapResveMapper.deleteKioskKeyMapResve(kioskKeyMapResveVO);
        }

        return result;
    }

    /** 예약 수정 */
    @Override
    public int modKioskKeyMapResve(KioskKeyMapResveVO[] kioskKeyMapResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(KioskKeyMapResveVO kioskKeyMapResveVO : kioskKeyMapResveVOs){

            kioskKeyMapResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapResveVO.setRegDt(currentDt);
            kioskKeyMapResveVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapResveVO.setModDt(currentDt);
            kioskKeyMapResveVO.setModId(sessionInfoVO.getUserId());
            // 기존 예약 삭제
            if(kioskKeyMapResveMapper.deleteKioskKeyMapResve(kioskKeyMapResveVO) > 0){
                // 예약 저장
                result += kioskKeyMapResveMapper.saveKioskKeyMapResve(kioskKeyMapResveVO);
            }
        }

        return result;
    }

}