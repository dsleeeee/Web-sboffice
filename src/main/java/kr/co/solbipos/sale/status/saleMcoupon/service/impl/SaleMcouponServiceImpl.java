package kr.co.solbipos.sale.status.saleMcoupon.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.saleMcoupon.service.SaleMcouponService;
import kr.co.solbipos.sale.status.saleMcoupon.service.SaleMcouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : SaleMcouponServiceImpl.java
 * @Description : 맘스터치 > 매출분석2 > 모바일쿠폰 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.04.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("saleMcouponService")
@Transactional
public class SaleMcouponServiceImpl implements SaleMcouponService {
    private final SaleMcouponMapper saleMcouponMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleMcouponServiceImpl(SaleMcouponMapper saleMcouponMapper) { this.saleMcouponMapper = saleMcouponMapper; }

    /** 모바일쿠폰 현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleMcouponList(SaleMcouponVO saleMcouponVO, SessionInfoVO sessionInfoVO) {

        saleMcouponVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleMcouponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = saleMcouponVO.getStoreCds().split(",");
        saleMcouponVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (saleMcouponVO.getStoreHqBrandCd() == "" || saleMcouponVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = saleMcouponVO.getUserBrands().split(",");
                saleMcouponVO.setUserBrandList(userBrandList);
            }
        }

        return saleMcouponMapper.getSaleMcouponList(saleMcouponVO);
    }

    /** 모바일쿠폰 현황 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleMcouponExcelList(SaleMcouponVO saleMcouponVO, SessionInfoVO sessionInfoVO) {

        saleMcouponVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleMcouponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = saleMcouponVO.getStoreCds().split(",");
        saleMcouponVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (saleMcouponVO.getStoreHqBrandCd() == "" || saleMcouponVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = saleMcouponVO.getUserBrands().split(",");
                saleMcouponVO.setUserBrandList(userBrandList);
            }
        }

        return saleMcouponMapper.getSaleMcouponExcelList(saleMcouponVO);
    }

    /** 모바일쿠폰 현황 상세 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleMcouponDtlList(SaleMcouponVO saleMcouponVO, SessionInfoVO sessionInfoVO) {

        saleMcouponVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleMcouponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return saleMcouponMapper.getSaleMcouponDtlList(saleMcouponVO);
    }
}