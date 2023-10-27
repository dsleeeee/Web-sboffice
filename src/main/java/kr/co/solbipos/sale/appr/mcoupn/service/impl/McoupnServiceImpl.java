package kr.co.solbipos.sale.appr.mcoupn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.appr.mcoupn.service.McoupnService;
import kr.co.solbipos.sale.appr.mcoupn.service.McoupnVO;
import kr.co.solbipos.sale.appr.mcoupn.service.impl.McoupnMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

/**
 * @Class Name : McoupnServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 모바일쿠폰 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mcoupnService")
@Transactional
public class McoupnServiceImpl implements McoupnService {
    private final McoupnMapper mcoupnMapper;
    private final PopupMapper popupMapper;

    public McoupnServiceImpl(McoupnMapper mcoupnMapper, PopupMapper popupMapper) {
        this.mcoupnMapper = mcoupnMapper;
        this.popupMapper = popupMapper;
    }

    /** 모바일쿠폰입금관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMcoupnList(McoupnVO mcoupnVO, SessionInfoVO sessionInfoVO) {

        mcoupnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            mcoupnVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(mcoupnVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mcoupnVO.getStoreCds(), 3900));
            mcoupnVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mcoupnMapper.getMcoupnList(mcoupnVO);
    }

    /** 모바일쿠폰입금관리 - 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMcoupnExcelList(McoupnVO mcoupnVO, SessionInfoVO sessionInfoVO) {

        mcoupnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            mcoupnVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(mcoupnVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mcoupnVO.getStoreCds(), 3900));
            mcoupnVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mcoupnMapper.getMcoupnExcelList(mcoupnVO);
    }

}