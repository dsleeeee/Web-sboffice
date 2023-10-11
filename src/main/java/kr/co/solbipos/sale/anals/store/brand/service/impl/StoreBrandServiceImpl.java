package kr.co.solbipos.sale.anals.store.brand.service.impl;

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
import kr.co.solbipos.sale.anals.store.brand.service.StoreBrandService;
import kr.co.solbipos.sale.anals.store.brand.service.StoreBrandVO;

@Service("StoreBrandService")
public class StoreBrandServiceImpl implements StoreBrandService {
    private final StoreBrandMapper storeBrandMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public StoreBrandServiceImpl(StoreBrandMapper storeBrandMapper, PopupMapper popupMapper, MessageService messageService) {
    	this.storeBrandMapper = storeBrandMapper;
    	this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 브랜드별 매출 - 브랜드별 매출 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreBrandList(StoreBrandVO storeBrandVO, SessionInfoVO sessionInfoVO) {
        storeBrandVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeBrandVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeBrandVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(storeBrandVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeBrandVO.getStoreCd(), 3900));
            storeBrandVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	
        return storeBrandMapper.getStoreBrandList(storeBrandVO);
    }
    
    /** 브랜드별 매출 - 정렬구분 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSortFgComboList(StoreBrandVO storeBrandVO, SessionInfoVO sessionInfoVO) {
        return storeBrandMapper.getSortFgComboList(storeBrandVO);
    }
}
