package kr.co.solbipos.sale.status.prod.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayService;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdDayService")
public class ProdDayServiceImpl implements ProdDayService {
    private final ProdDayMapper prodDayMapper;
    private final MessageService messageService;

    @Autowired
    public ProdDayServiceImpl(ProdDayMapper prodDayMapper, MessageService messageService) {
    	this.prodDayMapper = prodDayMapper;
        this.messageService = messageService;
    }


    /** 상품별 매출 - 일자별 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdDayList(ProdDayVO prodDayVO, SessionInfoVO sessionInfoVO) {
  
    	prodDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(prodDayVO.getStoreCd()).equals("")) {
        	prodDayVO.setArrStoreCd(prodDayVO.getStoreCd().split(","));
        }
        
        return prodDayMapper.getProdDayList(prodDayVO);
    }

}
