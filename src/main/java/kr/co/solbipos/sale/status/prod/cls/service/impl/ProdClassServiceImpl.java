package kr.co.solbipos.sale.status.prod.cls.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassService;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdClassService")
public class ProdClassServiceImpl implements ProdClassService {
    private final ProdClassMapper prodClassMapper;
    private final MessageService messageService;

    @Autowired
    public ProdClassServiceImpl(ProdClassMapper prodClassMapper, MessageService messageService) {
    	this.prodClassMapper = prodClassMapper;
        this.messageService = messageService;
    }


    /** 상품별 매출 - 분류별 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdClassList(ProdClassVO prodClassVO, SessionInfoVO sessionInfoVO) {
  
    	prodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(prodClassVO.getStoreCd()).equals("")) {
        	prodClassVO.setArrStoreCd(prodClassVO.getStoreCd().split(","));
        }
    	
        return prodClassMapper.getProdClassList(prodClassVO);
    }

}
