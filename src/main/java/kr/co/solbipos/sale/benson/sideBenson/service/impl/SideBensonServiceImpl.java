package kr.co.solbipos.sale.benson.sideBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.sideBenson.service.SideBensonService;
import kr.co.solbipos.sale.benson.sideBenson.service.SideBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : SideBensonServiceImpl.java
 * @Description : 벤슨 > 매출현황2 > 상품별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승        최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sideBensonService")
public class SideBensonServiceImpl implements SideBensonService {
    private final SideBensonMapper sideBensonMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public SideBensonServiceImpl(SideBensonMapper sideBensonMapper, PopupMapper popupMapper, MessageService messageService) {
        this.sideBensonMapper = sideBensonMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 상품분류별 조회 */
    @Override
    public List<DefaultMap<String>> sideProdClass(SideBensonVO sideBensonVO, SessionInfoVO sessionInfoVO) {

        sideBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(sideBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(sideBensonVO.getStoreCd(), 3900));
            sideBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (sideBensonVO.getProdClassCd() != null && !"".equals(sideBensonVO.getProdClassCd())) {
            String[] prodCdList = sideBensonVO.getProdClassCd().split(",");
            sideBensonVO.setArrProdClassCd(prodCdList);
        }

        return sideBensonMapper.sideProdClass(sideBensonVO);
    }

    @Override
    public List<DefaultMap<String>> sideProdClassExcel(SideBensonVO sideBensonVO, SessionInfoVO sessionInfoVO) {

        sideBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(sideBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(sideBensonVO.getStoreCd(), 3900));
            sideBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (sideBensonVO.getProdClassCd() != null && !"".equals(sideBensonVO.getProdClassCd())) {
            String[] prodCdList = sideBensonVO.getProdClassCd().split(",");
            sideBensonVO.setArrProdClassCd(prodCdList);
        }

        return sideBensonMapper.sideProdClassExcel(sideBensonVO);
    }

}
