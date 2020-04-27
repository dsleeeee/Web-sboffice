package kr.co.solbipos.stock.curr.hqCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrService;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("hqCurrService")
public class HqCurrServiceImpl implements HqCurrService {
    private final HqCurrMapper hqCurrMapper;
    private final MessageService messageService;

    @Autowired
    public HqCurrServiceImpl(HqCurrMapper hqCurrMapper, MessageService messageService) {
        this.hqCurrMapper = hqCurrMapper;
        this.messageService = messageService;
    }

    /** 현재고현황 - 현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqCurrList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO) {
    	hqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(hqCurrVO.getVendrCd()).equals("")) {
            hqCurrVO.setArrVendrCd(hqCurrVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(hqCurrVO.getOrgnFg() == "H" && hqCurrVO.getOrgnFg() != null) { // 본사권한
			list = hqCurrMapper.getHqCurrList(hqCurrVO);
		}else { // 매장권한
			hqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = hqCurrMapper.getHqStoreCurrList(hqCurrVO);
		}
        return list;
    }

	@Override
	public List<DefaultMap<String>> gethqCurrDtlList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO) {
		//hqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//    	if( !StringUtil.getOrBlank(hqCurrVO.getHqOfficeCd()).equals("")) {
//			return hqCurrMapper.getHqCurrDtlList(hqCurrVO);
//		} else {
//			//hqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
//			return hqCurrMapper.getStoreCurrDtlList(hqCurrVO);
//		}
		hqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		if(hqCurrVO.getOrgnFg() == "H" && hqCurrVO.getOrgnFg() != null) { // 본사권한
    		return hqCurrMapper.getHqCurrDtlList(hqCurrVO);    		
    	} else {
    		return hqCurrMapper.getStoreCurrDtlList(hqCurrVO);
    	}
	}

	@Override
	public List<DefaultMap<String>> getHqCurrExcelList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO) {
		hqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(hqCurrVO.getVendrCd()).equals("")) {
            hqCurrVO.setArrVendrCd(hqCurrVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(hqCurrVO.getOrgnFg() == "H" && hqCurrVO.getOrgnFg() != null) { // 본사권한
			list = hqCurrMapper.getHqCurrExcelList(hqCurrVO);
		}else { // 매장권한
			hqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = hqCurrMapper.getHqStoreCurrExcelList(hqCurrVO);
		}
        return list;
	}

}
