package kr.co.solbipos.sale.prod.dayProdStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreService;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : DayProdStoreServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayProdStoreService")
@Transactional
public class DayProdStoreServiceImpl implements DayProdStoreService {
    private final DayProdStoreMapper dayProdStoreMapper;

    public DayProdStoreServiceImpl(DayProdStoreMapper dayProdStoreMapper) {
        this.dayProdStoreMapper = dayProdStoreMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayProdStoreVO.getStoreCds().split(",");
        dayProdStoreVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (dayProdStoreVO.getProdCds() != null && !"".equals(dayProdStoreVO.getProdCds())) {
            String[] prodCdList = dayProdStoreVO.getProdCds().split(",");
            dayProdStoreVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdStoreVO.getStoreHqBrandCd() == "" || dayProdStoreVO.getStoreHqBrandCd() == null || dayProdStoreVO.getProdHqBrandCd() == "" || dayProdStoreVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreVO.getUserBrands().split(",");
                dayProdStoreVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoreMapper.getDayProdStoreList(dayProdStoreVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreExcelList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayProdStoreVO.getStoreCds().split(",");
        dayProdStoreVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (dayProdStoreVO.getProdCds() != null && !"".equals(dayProdStoreVO.getProdCds())) {
            String[] prodCdList = dayProdStoreVO.getProdCds().split(",");
            dayProdStoreVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdStoreVO.getStoreHqBrandCd() == "" || dayProdStoreVO.getStoreHqBrandCd() == null || dayProdStoreVO.getProdHqBrandCd() == "" || dayProdStoreVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreVO.getUserBrands().split(",");
                dayProdStoreVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoreMapper.getDayProdStoreExcelList(dayProdStoreVO);
    }

    /** 사용자별 브랜드 조회(콤보박스용) */
    @Override
    public List<DefaultMap<Object>> getUserBrandComboList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayProdStoreVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 브랜드 사용 조회
            String userBrands = dayProdStoreMapper.getUserBrandCdList(dayProdStoreVO);
            dayProdStoreVO.setUserBrands(userBrands);

            if (dayProdStoreVO.getUserBrands() != null && !"".equals(dayProdStoreVO.getUserBrands())) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreVO.getUserBrands().split(",");
                dayProdStoreVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoreMapper.getUserBrandComboList(dayProdStoreVO);
    }

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getUserHqNmcodeComboList(SessionInfoVO sessionInfoVO, String nmcodeGrpCd) {

        DayProdStoreVO dayProdStoreVO = new DayProdStoreVO();
        dayProdStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayProdStoreVO.setUserId(sessionInfoVO.getUserId());
        dayProdStoreVO.setNmcodeGrpCd(nmcodeGrpCd);

        List<DefaultMap<Object>> resultList = new ArrayList<DefaultMap<Object>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 코드별 공통코드 조회
            String userHqNmcodeCd = dayProdStoreMapper.getUserHqNmcodeCdList(dayProdStoreVO);
            dayProdStoreVO.setUserHqNmcodeCd(userHqNmcodeCd);

            resultList = dayProdStoreMapper.getUserHqNmcodeComboList(dayProdStoreVO);
        }

        return resultList;
    }

    /** 사용자별 지사 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getUserBranchComboList(SessionInfoVO sessionInfoVO) {

        DayProdStoreVO dayProdStoreVO = new DayProdStoreVO();
        dayProdStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            dayProdStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayProdStoreVO.setUserId(sessionInfoVO.getUserId());

        List<DefaultMap<Object>> resultList = new ArrayList<DefaultMap<Object>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            // 사사용자별 지사 조회
            String userHqNmcodeCd = dayProdStoreMapper.getUserBranchCdList(dayProdStoreVO);
            dayProdStoreVO.setUserHqNmcodeCd(userHqNmcodeCd);

            resultList = dayProdStoreMapper.getUserBranchComboList(dayProdStoreVO);
        }

        return resultList;
    }
}