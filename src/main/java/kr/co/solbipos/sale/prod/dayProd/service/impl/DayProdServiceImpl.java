package kr.co.solbipos.sale.prod.dayProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : DayProdServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayProdService")
@Transactional
public class DayProdServiceImpl implements DayProdService {
    private final DayProdMapper dayProdMapper;

    public DayProdServiceImpl(DayProdMapper dayProdMapper) {
        this.dayProdMapper = dayProdMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdList(DayProdVO dayProdVO, SessionInfoVO sessionInfoVO) {

        dayProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayProdVO.getStoreCds().split(",");
        dayProdVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (dayProdVO.getProdCds() != null && !"".equals(dayProdVO.getProdCds())) {
            String[] prodCdList = dayProdVO.getProdCds().split(",");
            dayProdVO.setProdCdList(prodCdList);
        }

        return dayProdMapper.getDayProdList(dayProdVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdExcelList(DayProdVO dayProdVO, SessionInfoVO sessionInfoVO) {

        dayProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayProdVO.getStoreCds().split(",");
        dayProdVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (dayProdVO.getProdCds() != null && !"".equals(dayProdVO.getProdCds())) {
            String[] prodCdList = dayProdVO.getProdCds().split(",");
            dayProdVO.setProdCdList(prodCdList);
        }

        return dayProdMapper.getDayProdExcelList(dayProdVO);
    }

    /** 사용자별 브랜드 조회(콤보박스용) */
    @Override
    public List<DefaultMap<Object>> getUserBrandComboList(DayProdVO dayProdVO, SessionInfoVO sessionInfoVO) {

        dayProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayProdVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 브랜드 사용 조회
            String userBrands = dayProdMapper.getUserBrandCdList(dayProdVO);
            dayProdVO.setUserBrands(userBrands);

            if (dayProdVO.getUserBrands() != null && !"".equals(dayProdVO.getUserBrands())) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdVO.getUserBrands().split(",");
                dayProdVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdMapper.getUserBrandComboList(dayProdVO);
    }

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getUserHqNmcodeComboList(SessionInfoVO sessionInfoVO, String nmcodeGrpCd) {

        DayProdVO dayProdVO = new DayProdVO();
        dayProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayProdVO.setUserId(sessionInfoVO.getUserId());
        dayProdVO.setNmcodeGrpCd(nmcodeGrpCd);

        List<DefaultMap<Object>> resultList = new ArrayList<DefaultMap<Object>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 코드별 공통코드 조회
            String userHqNmcodeCd = dayProdMapper.getUserHqNmcodeCdList(dayProdVO);
            dayProdVO.setUserHqNmcodeCd(userHqNmcodeCd);

            resultList = dayProdMapper.getUserHqNmcodeComboList(dayProdVO);
        }

        return resultList;
    }

    /** 사용자별 지사 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getUserBranchComboList(SessionInfoVO sessionInfoVO) {

        DayProdVO dayProdVO = new DayProdVO();
        dayProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            dayProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayProdVO.setUserId(sessionInfoVO.getUserId());

        List<DefaultMap<Object>> resultList = new ArrayList<DefaultMap<Object>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            // 사사용자별 지사 조회
            String userHqNmcodeCd = dayProdMapper.getUserBranchCdList(dayProdVO);
            dayProdVO.setUserHqNmcodeCd(userHqNmcodeCd);

            resultList = dayProdMapper.getUserBranchComboList(dayProdVO);
        }

        return resultList;
    }
}