package kr.co.solbipos.sale.anals.store.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.prod.service.StoreProdService;
import kr.co.solbipos.sale.anals.store.prod.service.StoreProdVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StoreProdService")
public class StoreProdServiceImpl implements StoreProdService {
    private final StoreProdMapper storeProdMapper;
    private final MessageService messageService;

    @Autowired
    public StoreProdServiceImpl(StoreProdMapper storeProdMapper, MessageService messageService) {
    	this.storeProdMapper = storeProdMapper;
        this.messageService = messageService;
    }

    /** 매장상품순위 - 매장상품순위 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreProdList(StoreProdVO storeProdVO, SessionInfoVO sessionInfoVO) {
  
    	storeProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(storeProdVO.getStoreCd()).equals("")) {
        	storeProdVO.setArrStoreCd(storeProdVO.getStoreCd().split(","));
        }
    	      
        return storeProdMapper.getStoreProdList(storeProdVO);
    }

}
