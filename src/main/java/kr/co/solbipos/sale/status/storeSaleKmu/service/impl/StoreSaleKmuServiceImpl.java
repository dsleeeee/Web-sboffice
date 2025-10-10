package kr.co.solbipos.sale.status.storeSaleKmu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.storeSaleKmu.service.StoreSaleKmuService;
import kr.co.solbipos.sale.status.storeSaleKmu.service.StoreSaleKmuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : StoreSaleKmuServiceImpl.java
 * @Description : 국민대 > 매출관리 > 점소별매출일보 (국민대)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeSaleKmuService")
@Transactional
public class StoreSaleKmuServiceImpl implements StoreSaleKmuService {
    private final StoreSaleKmuMapper storeSaleKmuMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreSaleKmuServiceImpl(StoreSaleKmuMapper storeSaleKmuMapper, PopupMapper popupMapper) {
        this.storeSaleKmuMapper = storeSaleKmuMapper;
        this.popupMapper = popupMapper;
    }

    /** 점소별매출일보 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreSaleKmuList(StoreSaleKmuVO storeSaleKmuVO, SessionInfoVO sessionInfoVO) {

        storeSaleKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeSaleKmuVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSaleKmuVO.getStoreCds(), 3900));
            storeSaleKmuVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        storeSaleKmuVO.setArrStoreCol(storeSaleKmuVO.getStoreCds().split(",")); // 매장 array 값 세팅

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotStoreCol = "";
        String arrStoreCol[] = storeSaleKmuVO.getStoreCds().split(",");
        for(int i=0; i < arrStoreCol.length; i++) {
            pivotStoreCol += (pivotStoreCol.equals("") ? "" : ",") + "'"+arrStoreCol[i]+"'"+" AS STORE_"+arrStoreCol[i];
        }
        storeSaleKmuVO.setPivotStoreCol(pivotStoreCol);

        return storeSaleKmuMapper.getStoreSaleKmuList(storeSaleKmuVO);
    }
}