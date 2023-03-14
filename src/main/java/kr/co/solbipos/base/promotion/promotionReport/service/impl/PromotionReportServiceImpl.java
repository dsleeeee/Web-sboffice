package kr.co.solbipos.base.promotion.promotionReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.promotionReport.service.PromotionReportService;
import kr.co.solbipos.base.promotion.promotionReport.service.PromotionReportVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PromotionReportServiceImpl.java
 * @Description : 기초관리 > 프로모션관리 > 프로모션정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.02.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("promotionReportService")
@Transactional
public class PromotionReportServiceImpl implements PromotionReportService {
    private final PromotionReportMapper promotionReportMapper;

    public PromotionReportServiceImpl(PromotionReportMapper promotionReportMapper) {
        this.promotionReportMapper = promotionReportMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPromotionReportList(PromotionReportVO promotionReportVO, SessionInfoVO sessionInfoVO) {
        promotionReportVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            promotionReportVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = promotionReportVO.getStoreCds().split(",");
        promotionReportVO.setStoreCdList(storeCds);

        // 프로모션 array 값 세팅
        String[] promotionCds = promotionReportVO.getPromotionCds().split(",");
        promotionReportVO.setPromotionCdList(promotionCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (promotionReportVO.getStoreHqBrandCd() == "" || promotionReportVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = promotionReportVO.getUserBrands().split(",");
                promotionReportVO.setUserBrandList(userBrandList);
            }
        }

        return promotionReportMapper.getPromotionReportList(promotionReportVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getPromotionReportExcelList(PromotionReportVO promotionReportVO, SessionInfoVO sessionInfoVO) {
        promotionReportVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            promotionReportVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = promotionReportVO.getStoreCds().split(",");
        promotionReportVO.setStoreCdList(storeCds);

        // 프로모션 array 값 세팅
        String[] promotionCds = promotionReportVO.getPromotionCds().split(",");
        promotionReportVO.setPromotionCdList(promotionCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (promotionReportVO.getStoreHqBrandCd() == "" || promotionReportVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = promotionReportVO.getUserBrands().split(",");
                promotionReportVO.setUserBrandList(userBrandList);
            }
        }

        return promotionReportMapper.getPromotionReportExcelList(promotionReportVO);
    }

    /** 프로모션정산 상세 조회 */
    @Override
    public List<DefaultMap<Object>> getPromotionReportDtlList(PromotionReportVO promotionReportVO, SessionInfoVO sessionInfoVO) {
        promotionReportVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            promotionReportVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionReportMapper.getPromotionReportDtlList(promotionReportVO);
    }
}