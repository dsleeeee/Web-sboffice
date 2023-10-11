package kr.co.solbipos.sale.anals.store.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.prod.service.StoreProdService;
import kr.co.solbipos.sale.anals.store.prod.service.StoreProdVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StoreProdService")
public class StoreProdServiceImpl implements StoreProdService {
    private final StoreProdMapper storeProdMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public StoreProdServiceImpl(StoreProdMapper storeProdMapper, PopupMapper popupMapper, MessageService messageService) {
    	this.storeProdMapper = storeProdMapper;
    	this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 매장상품순위 - 매장상품순위 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreProdList(StoreProdVO storeProdVO, SessionInfoVO sessionInfoVO) {

        storeProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeProdVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(storeProdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeProdVO.getStoreCd(), 3900));
            storeProdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	      
        return storeProdMapper.getStoreProdList(storeProdVO);
    }

    /** 매장상품순위 - 매장상품순위 리스트(엑셀) 조회   */
    @Override
    public List<DefaultMap<String>> getStoreProdExcelList(StoreProdVO storeProdVO, SessionInfoVO sessionInfoVO) {

        storeProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	storeProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeProdVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(storeProdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeProdVO.getStoreCd(), 3900));
            storeProdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	      
        return storeProdMapper.getStoreProdExcelList(storeProdVO);
    }
}
