package kr.co.solbipos.stock.status.storeperiod.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
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
    private final PopupMapper popupMapper;

	@Autowired
	public StorePeriodServiceImpl(StorePeriodMapper storePeriodMapper, PopupMapper popupMapper) {
		this.storePeriodMapper = storePeriodMapper;
        this.popupMapper = popupMapper;
	}

	/** 매장기간수불 - 매장기간수불 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePeriodList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO) {
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(storePeriodVO.getVendrCd()).equals("")) {
        	storePeriodVO.setArrVendrCd(storePeriodVO.getVendrCd().split(","));
        }
        
		storePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(storePeriodVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storePeriodVO.getStoreCd(), 3900));
            storePeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return storePeriodMapper.getStorePeriodList(storePeriodVO);
	}

	/** 매장기간수불 - 매장기간수불 상품코드 선택 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePeriodDtlList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO) {
		storePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return storePeriodMapper.getStorePeriodDtlList(storePeriodVO);
	}

	/** 매장기간수불 - 매장기간수불 수량 선택 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePeriodQtyDtlList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO) {
		storePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return storePeriodMapper.getStorePeriodQtyDtlList(storePeriodVO);
	}

	/** 매장기간수불 - 매장기간수불 엑셀 전체 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStoreperiodExcelList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO) {
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(storePeriodVO.getVendrCd()).equals("")) {
        	storePeriodVO.setArrVendrCd(storePeriodVO.getVendrCd().split(","));
        }
        
		storePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(storePeriodVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storePeriodVO.getStoreCd(), 3900));
            storePeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }


        return storePeriodMapper.getStoreperiodExcelList(storePeriodVO);
	}

}
