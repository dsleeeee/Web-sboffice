package kr.co.solbipos.stock.status.storeday.service.impl;

import java.util.List;

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
	    private final MessageService messageService;

	    @Autowired
	    public StoreDayServiceImpl(StoreDayMapper storeDayMapper, MessageService messageService) {
	        this.storeDayMapper = storeDayMapper;
	        this.messageService = messageService;
	    }

	/** 매장일수불 리스트 조회 */
	@Override
	public List<DefaultMap<String>> storeDayList(StoreDayVO storeDayVO, SessionInfoVO sessionInfoVO) {
		storeDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		storeDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		if (storeDayVO.getStoreCd() != null && !"".equals(storeDayVO.getStoreCd())) {
    		String[] arrStoreCd = storeDayVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				storeDayVO.setArrStoreCd(arrStoreCd);
    			}
    		}
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
}
