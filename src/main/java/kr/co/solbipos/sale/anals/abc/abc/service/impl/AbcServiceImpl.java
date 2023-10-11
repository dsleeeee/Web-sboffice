package kr.co.solbipos.sale.anals.abc.abc.service.impl;

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
import kr.co.solbipos.sale.anals.abc.abc.service.AbcService;
import kr.co.solbipos.sale.anals.abc.abc.service.AbcVO;

@Service("AbcService")
public class AbcServiceImpl implements AbcService {
    private final AbcMapper abcMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public AbcServiceImpl(AbcMapper abcMapper, PopupMapper popupMapper, MessageService messageService) {
    	this.abcMapper = abcMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /**상픔ABC분석 - 상픔ABC분석 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getAbcList(AbcVO abcVO, SessionInfoVO sessionInfoVO) {
        abcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        abcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        abcVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(abcVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(abcVO.getStoreCd(), 3900));
            abcVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	 
        return abcMapper.getAbcList(abcVO);
    }
    
}
