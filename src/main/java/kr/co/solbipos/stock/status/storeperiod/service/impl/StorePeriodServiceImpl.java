package kr.co.solbipos.stock.status.storeperiod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.storeperiod.service.StorePeriodService;
import kr.co.solbipos.stock.status.storeperiod.service.StorePeriodVO;

@Service("StorePeriodService")
public class StorePeriodServiceImpl implements StorePeriodService {
	private final StorePeriodMapper storePeriodMapper;

	@Autowired
	public StorePeriodServiceImpl(StorePeriodMapper storePeriodMapper) {
		this.storePeriodMapper = storePeriodMapper;
	}
	
	@Override
	public List<DefaultMap<String>> getStorePeriodList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO) {
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(storePeriodVO.getVendrCd()).equals("")) {
        	storePeriodVO.setArrVendrCd(storePeriodVO.getVendrCd().split(","));
        }
        
		storePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		if(!StringUtil.getOrBlank(storePeriodVO.getStoreCd()).equals("")) {
			storePeriodVO.setArrStoreCd(storePeriodVO.getStoreCd().split(","));
        }

        return storePeriodMapper.getStorePeriodList(storePeriodVO);
	}

	@Override
	public List<DefaultMap<String>> getStorePeriodDtlList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO) {
		storePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return storePeriodMapper.getStorePeriodDtlList(storePeriodVO);
	}

	@Override
	public List<DefaultMap<String>> getStorePeriodQtyDtlList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO) {
		storePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return storePeriodMapper.getStorePeriodQtyDtlList(storePeriodVO);
	}

}
