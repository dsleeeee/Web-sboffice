package kr.co.solbipos.sale.prod.prodRankStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.prodRankStore.service.ProdRankStoreService;
import kr.co.solbipos.sale.prod.prodRankStore.service.ProdRankStoreVO;
import kr.co.solbipos.sale.prod.prodRankStore.service.impl.ProdRankStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdRankStoreServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 상품판매순위(점포)
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
@Service("prodRankStoreService")
@Transactional
public class ProdRankStoreServiceImpl implements ProdRankStoreService {
    private final ProdRankStoreMapper prodRankStoreMapper;
    private final PopupMapper popupMapper;

    public ProdRankStoreServiceImpl(ProdRankStoreMapper prodRankStoreMapper, PopupMapper popupMapper) {
        this.prodRankStoreMapper = prodRankStoreMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdRankStoreList(ProdRankStoreVO prodRankStoreVO, SessionInfoVO sessionInfoVO) {

        prodRankStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodRankStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodRankStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodRankStoreVO.getStoreCds(), 3900));
            prodRankStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodRankStoreMapper.getProdRankStoreList(prodRankStoreVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdRankStoreExcelList(ProdRankStoreVO prodRankStoreVO, SessionInfoVO sessionInfoVO) {

        prodRankStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodRankStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodRankStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodRankStoreVO.getStoreCds(), 3900));
            prodRankStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodRankStoreMapper.getProdRankStoreExcelList(prodRankStoreVO);
    }
}