package kr.co.solbipos.base.prod.kioskDisplayResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskDisplayResve.service.KioskDisplayResveService;
import kr.co.solbipos.base.prod.kioskDisplayResve.service.KioskDisplayResveVO;
import kr.co.solbipos.base.prod.kioskDisplayResve.service.KioskDisplayResveVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskDisplayResveServiceImpl.java
 * @Description : 기초관리 - 상품관리2 - 비노출관리(예약)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.24  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("KioskDisplayResveService")
public class KioskDisplayResveServiceImpl implements KioskDisplayResveService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KioskDisplayResveMapper kioskDisplayResveMapper;

    /** Constructor Injection */
    @Autowired
    public KioskDisplayResveServiceImpl(KioskDisplayResveMapper kioskDisplayResveMapper) {
        this.kioskDisplayResveMapper = kioskDisplayResveMapper;
    }

    // 예약 내역 조회
    @Override
    public List<DefaultMap<String>> getKioskDisplayResve(KioskDisplayResveVO kioskDisplayResveVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            kioskDisplayResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        kioskDisplayResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskDisplayResveVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = kioskDisplayResveVO.getStoreCds().split(",");
        kioskDisplayResveVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (kioskDisplayResveVO.getProdCds() != null && !"".equals(kioskDisplayResveVO.getProdCds())) {
            String[] prodCdList = kioskDisplayResveVO.getProdCds().split(",");
            kioskDisplayResveVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (kioskDisplayResveVO.getStoreHqBrandCd() == "" || kioskDisplayResveVO.getStoreHqBrandCd() == null || kioskDisplayResveVO.getProdHqBrandCd() == "" || kioskDisplayResveVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskDisplayResveVO.getUserBrands() != null && !"".equals(kioskDisplayResveVO.getUserBrands())) {
                    String[] userBrandList = kioskDisplayResveVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskDisplayResveVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return kioskDisplayResveMapper.getKioskDisplayResve(kioskDisplayResveVO);
    }

    // 상품 내역 조회
    @Override
    public List<DefaultMap<String>> getProdList(KioskDisplayResveVO kioskDisplayResveVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            kioskDisplayResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        kioskDisplayResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskDisplayResveVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = kioskDisplayResveVO.getStoreCds().split(",");
        kioskDisplayResveVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (kioskDisplayResveVO.getProdCds() != null && !"".equals(kioskDisplayResveVO.getProdCds())) {
            String[] prodCdList = kioskDisplayResveVO.getProdCds().split(",");
            kioskDisplayResveVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (kioskDisplayResveVO.getStoreHqBrandCd() == "" || kioskDisplayResveVO.getStoreHqBrandCd() == null || kioskDisplayResveVO.getProdHqBrandCd() == "" || kioskDisplayResveVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskDisplayResveVO.getUserBrands() != null && !"".equals(kioskDisplayResveVO.getUserBrands())) {
                    String[] userBrandList = kioskDisplayResveVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskDisplayResveVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }
        return kioskDisplayResveMapper.getProdList(kioskDisplayResveVO);
    }

    // 비노출관리 예약
    @Override
    public int saveKioskDisplayResve(KioskDisplayResveVO[] kioskDisplayResveVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(KioskDisplayResveVO kioskDisplayResveVO : kioskDisplayResveVOs) {

            kioskDisplayResveVO.setRegDt(currentDt);
            kioskDisplayResveVO.setRegId(sessionInfoVO.getUserId());
            kioskDisplayResveVO.setModDt(currentDt);
            kioskDisplayResveVO.setModId(sessionInfoVO.getUserId());

            if(kioskDisplayResveMapper.getKioskDisplayResveCnt(kioskDisplayResveVO) == 0){
                procCnt = kioskDisplayResveMapper.saveKioskDisplayResve(kioskDisplayResveVO);
            }
        }

        return procCnt;
    }

    /** 예약 삭제 */
    @Override
    public int deleteKioskDisplayResve(KioskDisplayResveVO[] kioskDisplayResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(KioskDisplayResveVO kioskDisplayResveVO : kioskDisplayResveVOs){
            result += kioskDisplayResveMapper.deleteKioskDisplayResve(kioskDisplayResveVO);
        }

        return result;
    }

    /** 예약 수정 */
    @Override
    public int modKioskDisplayResve(KioskDisplayResveVO[] kioskDisplayResveVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for(KioskDisplayResveVO kioskDisplayResveVO : kioskDisplayResveVOs){

            kioskDisplayResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskDisplayResveVO.setRegDt(currentDt);
            kioskDisplayResveVO.setRegId(sessionInfoVO.getUserId());
            kioskDisplayResveVO.setModDt(currentDt);
            kioskDisplayResveVO.setModId(sessionInfoVO.getUserId());
            // 기존 예약 삭제
            if(kioskDisplayResveMapper.deleteKioskDisplayResve(kioskDisplayResveVO) > 0){
                // 예약 저장
                result += kioskDisplayResveMapper.saveKioskDisplayResve(kioskDisplayResveVO);
            }
        }

        return result;
    }
}