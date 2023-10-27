package kr.co.solbipos.sale.prod.prodStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.prodStore.service.ProdStoreService;
import kr.co.solbipos.sale.prod.prodStore.service.ProdStoreVO;
import kr.co.solbipos.sale.prod.prodStore.service.impl.ProdStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdStoreServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 상품별 점포매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodStoreService")
@Transactional
public class ProdStoreServiceImpl implements ProdStoreService {
    private final ProdStoreMapper prodStoreMapper;
    private final PopupMapper popupMapper;

    public ProdStoreServiceImpl(ProdStoreMapper prodStoreMapper, PopupMapper popupMapper) {
        this.prodStoreMapper = prodStoreMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdStoreList(ProdStoreVO prodStoreVO, SessionInfoVO sessionInfoVO) {

        prodStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodStoreVO.getStoreCds(), 3900));
            prodStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodStoreMapper.getProdStoreList(prodStoreVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdStoreExcelList(ProdStoreVO prodStoreVO, SessionInfoVO sessionInfoVO) {

        prodStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodStoreVO.getStoreCds(), 3900));
            prodStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodStoreMapper.getProdStoreExcelList(prodStoreVO);
    }
}