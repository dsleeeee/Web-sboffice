package kr.co.solbipos.stock.curr.storeCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.curr.storeCurr.service.StoreCurrService;
import kr.co.solbipos.stock.curr.storeCurr.service.StoreCurrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("storeCurrService")
public class StoreCurrServiceImpl implements StoreCurrService {
    private final StoreCurrMapper storeCurrMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public StoreCurrServiceImpl(StoreCurrMapper storeCurrMapper, PopupMapper popupMapper, MessageService messageService) {
        this.storeCurrMapper = storeCurrMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 현재고현황 - 현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCurrList(StoreCurrVO storeCurrVO, SessionInfoVO sessionInfoVO) {
    	
    	storeCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(storeCurrVO.getVendrCd()).equals("")) {
            storeCurrVO.setArrVendrCd(storeCurrVO.getVendrCd().split(","));
        }
        
        System.out.println("매장코드 출력: " + storeCurrVO.getStoreCd());
        // 매장 멀티 선택
        if(!StringUtil.getOrBlank(storeCurrVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeCurrVO.getStoreCd(), 3900));
            storeCurrVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }


        return storeCurrMapper.getStoreCurrList(storeCurrVO);
    }
}
