package kr.co.solbipos.stock.status.storeday.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.storeday.service.StoreDayService;
import kr.co.solbipos.stock.status.storeday.service.StoreDayVO;


@Service("StoreDayService")
public class StoreDayServiceImpl implements StoreDayService {
	 private final StoreDayMapper storeDayMapper;
     private final PopupMapper popupMapper;
     private final MessageService messageService;

    @Autowired
        public StoreDayServiceImpl(StoreDayMapper storeDayMapper, PopupMapper popupMapper, MessageService messageService) {
        this.storeDayMapper = storeDayMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

	/** 매장일수불 리스트 조회 */
	@Override
	public List<DefaultMap<String>> storeDayList(StoreDayVO storeDayVO, SessionInfoVO sessionInfoVO) {
		storeDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		storeDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(storeDayVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeDayVO.getStoreCd(), 3900));
            storeDayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

		if (storeDayVO.getVendrCd() != null && !"".equals(storeDayVO.getVendrCd())) {
    		String[] arrVendrCd = storeDayVO.getVendrCd().split(",");
    		if (arrVendrCd.length > 0) {
    			if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
    				storeDayVO.setArrVendrCd(arrVendrCd);
    			}
    		}
		}

		return storeDayMapper.storeDayList(storeDayVO);
	}

	/** 매장일수불 엑셀 전체 리스트 조회 */
	@Override
	public List<DefaultMap<String>> storeDayExcelList(StoreDayVO storeDayVO, SessionInfoVO sessionInfoVO) {
		storeDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		storeDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(storeDayVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeDayVO.getStoreCd(), 3900));
            storeDayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

		if (storeDayVO.getVendrCd() != null && !"".equals(storeDayVO.getVendrCd())) {
    		String[] arrVendrCd = storeDayVO.getVendrCd().split(",");
    		if (arrVendrCd.length > 0) {
    			if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
    				storeDayVO.setArrVendrCd(arrVendrCd);
    			}
    		}
		}

		return storeDayMapper.storeDayExcelList(storeDayVO);
	}
}
