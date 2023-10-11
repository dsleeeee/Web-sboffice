package kr.co.solbipos.sale.anals.store.fg.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.fg.service.StoreFgService;
import kr.co.solbipos.sale.anals.store.fg.service.StoreFgVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.fg.service.StoreFgService;
import kr.co.solbipos.sale.anals.store.fg.service.StoreFgVO;

@Service("StoreFgService")
public class StoreFgServiceImpl implements StoreFgService {
    private final StoreFgMapper storeFgMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public StoreFgServiceImpl(StoreFgMapper storeFgMapper, PopupMapper popupMapper, MessageService messageService) {
    	this.storeFgMapper = storeFgMapper;
    	this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 매장형태별 - 매장형태별 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreFgList(StoreFgVO storeFgVO, SessionInfoVO sessionInfoVO) {
        storeFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeFgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(storeFgVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeFgVO.getStoreCd(), 3900));
            storeFgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	 
        return storeFgMapper.getStoreFgList(storeFgVO);
    }
    
}
