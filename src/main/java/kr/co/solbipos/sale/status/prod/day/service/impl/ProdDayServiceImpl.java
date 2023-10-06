package kr.co.solbipos.sale.status.prod.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayService;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdDayService")
public class ProdDayServiceImpl implements ProdDayService {
    private final ProdDayMapper prodDayMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ProdDayServiceImpl(ProdDayMapper prodDayMapper, PopupMapper popupMapper, MessageService messageService) {
    	this.prodDayMapper = prodDayMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 일자별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getProdDayList(ProdDayVO prodDayVO, SessionInfoVO sessionInfoVO) {

        prodDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodDayVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(prodDayVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodDayVO.getStoreCd(), 3900));
            prodDayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        
        return prodDayMapper.getProdDayList(prodDayVO);
    }

    /** 일자별탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdDayExcelList(ProdDayVO prodDayVO, SessionInfoVO sessionInfoVO) {

        prodDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodDayVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(prodDayVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodDayVO.getStoreCd(), 3900));
            prodDayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        
        return prodDayMapper.getProdDayExcelList(prodDayVO);
    }
    
}