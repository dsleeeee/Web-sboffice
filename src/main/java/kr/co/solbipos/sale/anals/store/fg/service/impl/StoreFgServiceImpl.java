package kr.co.solbipos.sale.anals.store.fg.service.impl;

import java.util.List;

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
    private final MessageService messageService;

    @Autowired
    public StoreFgServiceImpl(StoreFgMapper storeFgMapper, MessageService messageService) {
    	this.storeFgMapper = storeFgMapper;
        this.messageService = messageService;
    }

    /** 매장형태별 - 매장형태별 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreFgList(StoreFgVO storeFgVO, SessionInfoVO sessionInfoVO) {
    	storeFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	 if(!StringUtil.getOrBlank(storeFgVO.getStoreCd()).equals("")) {
         	storeFgVO.setArrStoreCd(storeFgVO.getStoreCd().split(","));
         }
    	 
        return storeFgMapper.getStoreFgList(storeFgVO);
    }
    
}
