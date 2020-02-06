package kr.co.solbipos.sale.anals.store.brand.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.brand.service.StoreBrandService;
import kr.co.solbipos.sale.anals.store.brand.service.StoreBrandVO;

@Service("StoreBrandService")
public class StoreBrandServiceImpl implements StoreBrandService {
    private final StoreBrandMapper storeBrandMapper;
    private final MessageService messageService;

    @Autowired
    public StoreBrandServiceImpl(StoreBrandMapper storeBrandMapper, MessageService messageService) {
    	this.storeBrandMapper = storeBrandMapper;
        this.messageService = messageService;
    }

    /** 브랜드별 매출 - 브랜드별 매출 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreBrandList(StoreBrandVO storeBrandVO, SessionInfoVO sessionInfoVO) {
    	storeBrandVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return storeBrandMapper.getStoreBrandList(storeBrandVO);
    }
    
    /** 브랜드별 매출 - 정렬구분 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSortFgComboList(StoreBrandVO storeBrandVO, SessionInfoVO sessionInfoVO) {
        return storeBrandMapper.getSortFgComboList(storeBrandVO);
    }
}
