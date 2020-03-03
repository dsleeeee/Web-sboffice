package kr.co.solbipos.sale.anals.abc.abc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.abc.abc.service.AbcService;
import kr.co.solbipos.sale.anals.abc.abc.service.AbcVO;

@Service("AbcService")
public class AbcServiceImpl implements AbcService {
    private final AbcMapper abcMapper;
    private final MessageService messageService;

    @Autowired
    public AbcServiceImpl(AbcMapper abcMapper, MessageService messageService) {
    	this.abcMapper = abcMapper;
        this.messageService = messageService;
    }

    /**상픔ABC분석 - 상픔ABC분석 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getAbcList(AbcVO abcVO, SessionInfoVO sessionInfoVO) {
    	abcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	 if(!StringUtil.getOrBlank(abcVO.getStoreCd()).equals("")) {
         	abcVO.setArrStoreCd(abcVO.getStoreCd().split(","));
         }
    	 
        return abcMapper.getAbcList(abcVO);
    }
    
}
