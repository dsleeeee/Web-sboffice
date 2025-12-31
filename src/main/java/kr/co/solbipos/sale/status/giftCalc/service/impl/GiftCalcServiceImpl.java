package kr.co.solbipos.sale.status.giftCalc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.giftCalc.service.GiftCalcService;
import kr.co.solbipos.sale.status.giftCalc.service.GiftCalcVO;
import kr.co.solbipos.sale.status.prod.pos.service.ProdPosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : GiftCalcServiceImpl.java
 * @Description : 맘스터치 > 매출분석2 > 지류상품권 정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.08.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.08.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("giftCalcService")
@Transactional
public class GiftCalcServiceImpl implements GiftCalcService {
    private final GiftCalcMapper giftCalcMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public GiftCalcServiceImpl(GiftCalcMapper giftCalcMapper, PopupMapper popupMapper) {
        this.giftCalcMapper = giftCalcMapper;
        this.popupMapper = popupMapper;
    }

    /** 지류상품권 정산 - 조회 */
    @Override
    public List<DefaultMap<Object>> getGiftCalcList(GiftCalcVO giftCalcVO, SessionInfoVO sessionInfoVO) {

        giftCalcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        giftCalcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(giftCalcVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(giftCalcVO.getStoreCds(), 3900));
            giftCalcVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (giftCalcVO.getStoreHqBrandCd() == "" || giftCalcVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = giftCalcVO.getUserBrands().split(",");
                giftCalcVO.setUserBrandList(userBrandList);
            }
        }

        // 포스번호 셋팅
        if(!StringUtil.getOrBlank(giftCalcVO.getPosNos()).equals("")) {
            ProdPosVO prodPosVO = new ProdPosVO();
            prodPosVO.setArrSplitPosNo(CmmUtil.splitText(giftCalcVO.getPosNos(), 3900));
            giftCalcVO.setPosNoQuery(popupMapper.getSearchMultiPosRtn(prodPosVO));
        }


        return giftCalcMapper.getGiftCalcList(giftCalcVO);
    }

    /** 지류상품권 정산황 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getGiftCalcExcelList(GiftCalcVO giftCalcVO, SessionInfoVO sessionInfoVO) {

        giftCalcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        giftCalcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(giftCalcVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(giftCalcVO.getStoreCds(), 3900));
            giftCalcVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }


        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (giftCalcVO.getStoreHqBrandCd() == "" || giftCalcVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = giftCalcVO.getUserBrands().split(",");
                giftCalcVO.setUserBrandList(userBrandList);
            }
        }

        // 포스번호 셋팅
        if(!StringUtil.getOrBlank(giftCalcVO.getPosNos()).equals("")) {
            ProdPosVO prodPosVO = new ProdPosVO();
            prodPosVO.setArrSplitPosNo(CmmUtil.splitText(giftCalcVO.getPosNos(), 3900));
            giftCalcVO.setPosNoQuery(popupMapper.getSearchMultiPosRtn(prodPosVO));
        }

        return giftCalcMapper.getGiftCalcExcelList(giftCalcVO);
    }

    /** 지류상품권 정산 상세 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getGiftCalcDtlList(GiftCalcVO giftCalcVO, SessionInfoVO sessionInfoVO) {

        giftCalcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        giftCalcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (giftCalcVO.getStoreHqBrandCd() == "" || giftCalcVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = giftCalcVO.getUserBrands().split(",");
                giftCalcVO.setUserBrandList(userBrandList);
            }
        }

        // 포스번호 셋팅
        if(!StringUtil.getOrBlank(giftCalcVO.getPosNos()).equals("")) {
            ProdPosVO prodPosVO = new ProdPosVO();
            prodPosVO.setArrSplitPosNo(CmmUtil.splitText(giftCalcVO.getPosNos(), 3900));
            giftCalcVO.setPosNoQuery(popupMapper.getSearchMultiPosRtn(prodPosVO));
        }

        return giftCalcMapper.getGiftCalcDtlList(giftCalcVO);
    }
}