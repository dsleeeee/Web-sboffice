package kr.co.solbipos.sale.store.storeLangUse.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeLangUse.service.StoreLangUseService;
import kr.co.solbipos.sale.store.storeLangUse.service.StoreLangUseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreLangUseServiceImpl.java
 * @Description : 맘스터치 > 매장분석 > 다국어사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.25  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.04.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeLangUse")
public class StoreLangUseServiceImpl implements StoreLangUseService {

    private final StoreLangUseMapper storeLangUseMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public StoreLangUseServiceImpl(StoreLangUseMapper storeLangUseMapper, PopupMapper popupMapper) {
        this.storeLangUseMapper = storeLangUseMapper;
        this.popupMapper = popupMapper;
    }

    /** 다국어사용현황 조회 */
    @Override
    public List<DefaultMap<String>> getStoreLangUseList(StoreLangUseVO storeLangUseVO, SessionInfoVO sessionInfoVO) {
        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeLangUseVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeLangUseVO.getStoreCds(), 3900));
            storeLangUseVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeLangUseVO.getStoreHqBrandCd() == "" || storeLangUseVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeLangUseVO.getUserBrands().split(",");
                storeLangUseVO.setUserBrandList(userBrandList);
            }
        }

        return storeLangUseMapper.getStoreLangUseList(storeLangUseVO);
    }
}
