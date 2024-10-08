package kr.co.solbipos.sale.dlvr.dlvrRate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.dlvr.dlvrRate.service.DlvrRateService;
import kr.co.solbipos.sale.dlvr.dlvrRate.service.DlvrRateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DlvrRateServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 배달방문비중
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dlvrRateService")
@Transactional
public class DlvrRateServiceImpl implements DlvrRateService {
    private final DlvrRateMapper dlvrRateMapper;
    private final PopupMapper popupMapper;

    public DlvrRateServiceImpl(DlvrRateMapper dlvrRateMapper, PopupMapper popupMapper) {
        this.dlvrRateMapper = dlvrRateMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDlvrRateList(DlvrRateVO dlvrRateVO, SessionInfoVO sessionInfoVO) {

        dlvrRateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dlvrRateVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dlvrRateVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dlvrRateVO.getStoreCds(), 3900));
            dlvrRateVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dlvrRateMapper.getDlvrRateList(dlvrRateVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDlvrRateExcelList(DlvrRateVO dlvrRateVO, SessionInfoVO sessionInfoVO) {

        dlvrRateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dlvrRateVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dlvrRateVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dlvrRateVO.getStoreCds(), 3900));
            dlvrRateVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dlvrRateMapper.getDlvrRateExcelList(dlvrRateVO);
    }

}