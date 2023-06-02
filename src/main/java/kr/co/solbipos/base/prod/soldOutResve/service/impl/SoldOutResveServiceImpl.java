package kr.co.solbipos.base.prod.soldOutResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.soldOutResve.service.SoldOutResveService;
import kr.co.solbipos.base.prod.soldOutResve.service.SoldOutResveVO;
import kr.co.solbipos.base.prod.soldOutResve.service.impl.SoldOutResveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SoldOutResveServiceImpl.java
 * @Description : 기초관리 - 상품관리2 - 품절관리(예약)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.30  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("SoldOutResveService")
public class SoldOutResveServiceImpl implements SoldOutResveService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SoldOutResveMapper soldOutResveMapper;

    /** Constructor Injection */
    @Autowired
    public SoldOutResveServiceImpl(SoldOutResveMapper soldOutResveMapper) {
        this.soldOutResveMapper = soldOutResveMapper;
    }

    // 예약 내역 조회
    @Override
    public List<DefaultMap<String>> getSoldOutResve(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        soldOutResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        soldOutResveVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = soldOutResveVO.getStoreCds().split(",");
        soldOutResveVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (soldOutResveVO.getProdCds() != null && !"".equals(soldOutResveVO.getProdCds())) {
            String[] prodCdList = soldOutResveVO.getProdCds().split(",");
            soldOutResveVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (soldOutResveVO.getStoreHqBrandCd() == "" || soldOutResveVO.getStoreHqBrandCd() == null || soldOutResveVO.getProdHqBrandCd() == "" || soldOutResveVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (soldOutResveVO.getUserBrands() != null && !"".equals(soldOutResveVO.getUserBrands())) {
                    String[] userBrandList = soldOutResveVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        soldOutResveVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return soldOutResveMapper.getSoldOutResve(soldOutResveVO);
    }

    // 상품 내역 조회
    @Override
    public List<DefaultMap<String>> getProdList(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        soldOutResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        soldOutResveVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = soldOutResveVO.getStoreCds().split(",");
        soldOutResveVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (soldOutResveVO.getProdCds() != null && !"".equals(soldOutResveVO.getProdCds())) {
            String[] prodCdList = soldOutResveVO.getProdCds().split(",");
            soldOutResveVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (soldOutResveVO.getStoreHqBrandCd() == "" || soldOutResveVO.getStoreHqBrandCd() == null || soldOutResveVO.getProdHqBrandCd() == "" || soldOutResveVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (soldOutResveVO.getUserBrands() != null && !"".equals(soldOutResveVO.getUserBrands())) {
                    String[] userBrandList = soldOutResveVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        soldOutResveVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }
        return soldOutResveMapper.getProdList(soldOutResveVO);
    }

    // 품절관리 예약
    @Override
    public int saveSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SoldOutResveVO soldOutResveVO : soldOutResveVOs) {

            soldOutResveVO.setRegDt(currentDt);
            soldOutResveVO.setRegId(sessionInfoVO.getUserId());
            soldOutResveVO.setModDt(currentDt);
            soldOutResveVO.setModId(sessionInfoVO.getUserId());

            if(soldOutResveMapper.getSoldOutResveCnt(soldOutResveVO) == 0){
                procCnt = soldOutResveMapper.saveSoldOutResve(soldOutResveVO);
            }
        }

        return procCnt;
    }

    /** 예약 삭제 */
    @Override
    public int deleteSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(SoldOutResveVO soldOutResveVO : soldOutResveVOs){
            result += soldOutResveMapper.deleteSoldOutResve(soldOutResveVO);
        }

        return result;
    }

    /** 예약 수정 */
    @Override
    public int modSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for(SoldOutResveVO soldOutResveVO : soldOutResveVOs){

            soldOutResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            soldOutResveVO.setRegDt(currentDt);
            soldOutResveVO.setRegId(sessionInfoVO.getUserId());
            soldOutResveVO.setModDt(currentDt);
            soldOutResveVO.setModId(sessionInfoVO.getUserId());
            // 기존 예약 삭제
            if(soldOutResveMapper.deleteSoldOutResve(soldOutResveVO) > 0){
                // 예약 저장
                result += soldOutResveMapper.saveSoldOutResve(soldOutResveVO);
            }
        }

        return result;
    }


    // 사이드 예약 내역 조회
    @Override
    public List<DefaultMap<String>> getSdselSoldOutResve(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        soldOutResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        soldOutResveVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = soldOutResveVO.getStoreCds().split(",");
        soldOutResveVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (soldOutResveVO.getProdCds() != null && !"".equals(soldOutResveVO.getProdCds())) {
            String[] prodCdList = soldOutResveVO.getProdCds().split(",");
            soldOutResveVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (soldOutResveVO.getStoreHqBrandCd() == "" || soldOutResveVO.getStoreHqBrandCd() == null || soldOutResveVO.getProdHqBrandCd() == "" || soldOutResveVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (soldOutResveVO.getUserBrands() != null && !"".equals(soldOutResveVO.getUserBrands())) {
                    String[] userBrandList = soldOutResveVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        soldOutResveVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return soldOutResveMapper.getSdselSoldOutResve(soldOutResveVO);
    }

    // 사이드 상품 구분
    @Override
    public List<DefaultMap<String>> getSdselProdList(SoldOutResveVO soldOutResveVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutResveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        soldOutResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        soldOutResveVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = soldOutResveVO.getStoreCds().split(",");
        soldOutResveVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (soldOutResveVO.getProdCds() != null && !"".equals(soldOutResveVO.getProdCds())) {
            String[] prodCdList = soldOutResveVO.getProdCds().split(",");
            soldOutResveVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (soldOutResveVO.getStoreHqBrandCd() == "" || soldOutResveVO.getStoreHqBrandCd() == null || soldOutResveVO.getProdHqBrandCd() == "" || soldOutResveVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (soldOutResveVO.getUserBrands() != null && !"".equals(soldOutResveVO.getUserBrands())) {
                    String[] userBrandList = soldOutResveVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        soldOutResveVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }
        return soldOutResveMapper.getSdselProdList(soldOutResveVO);
    }

    // 사이드 품절관리 예약
    @Override
    public int saveSdselSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SoldOutResveVO soldOutResveVO : soldOutResveVOs) {

            soldOutResveVO.setRegDt(currentDt);
            soldOutResveVO.setRegId(sessionInfoVO.getUserId());
            soldOutResveVO.setModDt(currentDt);
            soldOutResveVO.setModId(sessionInfoVO.getUserId());

            if(soldOutResveMapper.getSdselSoldOutResveCnt(soldOutResveVO) == 0){
                procCnt = soldOutResveMapper.saveSdselSoldOutResve(soldOutResveVO);
            }
        }

        return procCnt;
    }

    /** 사이드 예약 삭제 */
    @Override
    public int deleteSdselSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(SoldOutResveVO soldOutResveVO : soldOutResveVOs){
            result += soldOutResveMapper.deleteSdselSoldOutResve(soldOutResveVO);
        }

        return result;
    }

    /** 사이드 예약 수정 */
    @Override
    public int modSdselSoldOutResve(SoldOutResveVO[] soldOutResveVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for(SoldOutResveVO soldOutResveVO : soldOutResveVOs){

            soldOutResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            soldOutResveVO.setRegDt(currentDt);
            soldOutResveVO.setRegId(sessionInfoVO.getUserId());
            soldOutResveVO.setModDt(currentDt);
            soldOutResveVO.setModId(sessionInfoVO.getUserId());
            // 기존 예약 삭제
            if(soldOutResveMapper.deleteSdselSoldOutResve(soldOutResveVO) > 0){
                // 예약 저장
                result += soldOutResveMapper.saveSdselSoldOutResve(soldOutResveVO);
            }
        }

        return result;
    }
}