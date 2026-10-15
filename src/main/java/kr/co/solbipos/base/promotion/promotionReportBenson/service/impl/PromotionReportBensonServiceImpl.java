package kr.co.solbipos.base.promotion.promotionReportBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.promotionReportBenson.service.PromotionReportBensonService;
import kr.co.solbipos.base.promotion.promotionReportBenson.service.PromotionReportBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PromotionReportBensonServiceImpl.java
 * @Description : 벤슨 > 프로모션관리 > 프로모션정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("promotionReportBensonService")
@Transactional
public class PromotionReportBensonServiceImpl implements PromotionReportBensonService {
    private final PromotionReportBensonMapper promotionReportBensonMapper;
    private final PopupMapper popupMapper;

    public PromotionReportBensonServiceImpl(PromotionReportBensonMapper promotionReportBensonMapper, PopupMapper popupMapper) {
        this.promotionReportBensonMapper = promotionReportBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPromotionReportBensonList(PromotionReportBensonVO promotionReportBensonVO, SessionInfoVO sessionInfoVO) {
        promotionReportBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionReportBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            promotionReportBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(promotionReportBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(promotionReportBensonVO.getStoreCds(), 3900));
            promotionReportBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 프로모션 array 값 세팅
        String[] promotionCds = promotionReportBensonVO.getPromotionCds().split(",");
        promotionReportBensonVO.setPromotionCdList(promotionCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (promotionReportBensonVO.getStoreHqBrandCd() == "" || promotionReportBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = promotionReportBensonVO.getUserBrands().split(",");
                promotionReportBensonVO.setUserBrandList(userBrandList);
            }
        }

        return promotionReportBensonMapper.getPromotionReportBensonList(promotionReportBensonVO);
    }

    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getPromotionReportBensonExcelList(PromotionReportBensonVO promotionReportBensonVO, SessionInfoVO sessionInfoVO) {
        promotionReportBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionReportBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            promotionReportBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(promotionReportBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(promotionReportBensonVO.getStoreCds(), 3900));
            promotionReportBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 프로모션 array 값 세팅
        String[] promotionCds = promotionReportBensonVO.getPromotionCds().split(",");
        promotionReportBensonVO.setPromotionCdList(promotionCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (promotionReportBensonVO.getStoreHqBrandCd() == "" || promotionReportBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = promotionReportBensonVO.getUserBrands().split(",");
                promotionReportBensonVO.setUserBrandList(userBrandList);
            }
        }

        return promotionReportBensonMapper.getPromotionReportBensonExcelList(promotionReportBensonVO);
    }

    /** 프로모션정산 상세 조회 */
    @Override
    public List<DefaultMap<Object>> getPromotionReportBensonDtlList(PromotionReportBensonVO promotionReportBensonVO, SessionInfoVO sessionInfoVO) {
        promotionReportBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionReportBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            promotionReportBensonVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionReportBensonMapper.getPromotionReportBensonDtlList(promotionReportBensonVO);
    }
}
