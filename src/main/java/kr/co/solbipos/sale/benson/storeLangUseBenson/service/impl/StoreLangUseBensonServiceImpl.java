package kr.co.solbipos.sale.benson.storeLangUseBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.storeLangUseBenson.service.StoreLangUseBensonService;
import kr.co.solbipos.sale.benson.storeLangUseBenson.service.StoreLangUseBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreLangUseBensonServiceImpl.java
 * @Description : 벤슨 > 매장분석 > 다국어사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeLangUseBenson")
public class StoreLangUseBensonServiceImpl implements StoreLangUseBensonService {

    private final StoreLangUseBensonMapper storeLangUseBensonMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public StoreLangUseBensonServiceImpl(StoreLangUseBensonMapper storeLangUseBensonMapper, PopupMapper popupMapper) {
        this.storeLangUseBensonMapper = storeLangUseBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 다국어사용현황 조회 */
    @Override
    public List<DefaultMap<String>> getStoreLangUseBensonList(StoreLangUseBensonVO storeLangUseBensonVO, SessionInfoVO sessionInfoVO) {
        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeLangUseBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeLangUseBensonVO.getStoreCds(), 3900));
            storeLangUseBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeLangUseBensonVO.getStoreHqBrandCd() == "" || storeLangUseBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeLangUseBensonVO.getUserBrands().split(",");
                storeLangUseBensonVO.setUserBrandList(userBrandList);
            }
        }

        return storeLangUseBensonMapper.getStoreLangUseBensonList(storeLangUseBensonVO);
    }
}
