package kr.co.solbipos.sale.status.prod.cls.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassService;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdClassService")
public class ProdClassServiceImpl implements ProdClassService {
    private final ProdClassMapper prodClassMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ProdClassServiceImpl(ProdClassMapper prodClassMapper, MessageService messageService, PopupMapper popupMapper) {
    	this.prodClassMapper = prodClassMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 분류별상품탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getProdClassList(ProdClassVO prodClassVO, SessionInfoVO sessionInfoVO) {
        prodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodClassVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(prodClassVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodClassVO.getStoreCd(), 3900));
            prodClassVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	
        return prodClassMapper.getProdClassList(prodClassVO);
    }
    
    /** 분류별상품탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdClassExcelList(ProdClassVO prodClassVO, SessionInfoVO sessionInfoVO) {
        prodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodClassVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(prodClassVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodClassVO.getStoreCd(), 3900));
            prodClassVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	
        return prodClassMapper.getProdClassExcelList(prodClassVO);
    }
}