package kr.co.solbipos.sale.area.bizArea.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.area.bizArea.service.BizAreaService;
import kr.co.solbipos.sale.area.bizArea.service.BizAreaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : BizAreaServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 상권별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("bizAreaService")
@Transactional
public class BizAreaServiceImpl implements BizAreaService {
    private final BizAreaMapper bizAreaMapper;
    private final PopupMapper popupMapper;

    public BizAreaServiceImpl(BizAreaMapper bizAreaMapper, PopupMapper popupMapper) {
        this.bizAreaMapper = bizAreaMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getBizAreaList(BizAreaVO bizAreaVO, SessionInfoVO sessionInfoVO) {

        bizAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            bizAreaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(bizAreaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(bizAreaVO.getStoreCds(), 3900));
            bizAreaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return bizAreaMapper.getBizAreaList(bizAreaVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getBizAreaExcelList(BizAreaVO bizAreaVO, SessionInfoVO sessionInfoVO) {

        bizAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            bizAreaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(bizAreaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(bizAreaVO.getStoreCds(), 3900));
            bizAreaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return bizAreaMapper.getBizAreaExcelList(bizAreaVO);
    }
}