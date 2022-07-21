package kr.co.solbipos.stock.curr.dayCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.curr.dayCurr.service.DayCurrService;
import kr.co.solbipos.stock.curr.dayCurr.service.DayCurrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dayCurrService")
public class DayCurrServiceImpl implements DayCurrService {
    private final DayCurrMapper dayCurrMapper;
    private final MessageService messageService;

    @Autowired
    public DayCurrServiceImpl(DayCurrMapper dayCurrMapper, MessageService messageService) {
        this.dayCurrMapper = dayCurrMapper;
        this.messageService = messageService;
    }

    /** 현재고현황 - 현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayCurrList(DayCurrVO dayCurrVO, SessionInfoVO sessionInfoVO) {
    	dayCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(dayCurrVO.getVendrCd()).equals("")) {
            dayCurrVO.setArrVendrCd(dayCurrVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(dayCurrVO.getOrgnFg() == "H" && dayCurrVO.getOrgnFg() != null) { // 본사권한
			list = dayCurrMapper.getDayCurrList(dayCurrVO);
		}else { // 매장권한
			dayCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = dayCurrMapper.getHqStoreCurrList(dayCurrVO);
		}
        return list;
    }

	/** 현재고현황 - 현재고현황 엑셀 전체 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getDayCurrExcelList(DayCurrVO dayCurrVO, SessionInfoVO sessionInfoVO) {
		dayCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(dayCurrVO.getVendrCd()).equals("")) {
            dayCurrVO.setArrVendrCd(dayCurrVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(dayCurrVO.getOrgnFg() == "H" && dayCurrVO.getOrgnFg() != null) { // 본사권한
			list = dayCurrMapper.getDayCurrExcelList(dayCurrVO);
		}else { // 매장권한
			dayCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = dayCurrMapper.getHqStoreCurrExcelList(dayCurrVO);
		}
        return list;
	}

}
