package kr.co.solbipos.sale.status.barcd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.barcd.service.BarcdService;
import kr.co.solbipos.sale.status.barcd.service.BarcdVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("barcdService")
public class BarcdServiceImpl implements BarcdService {
    private final BarcdMapper barcdMapper;
    private final MessageService messageService;

    @Autowired
    public BarcdServiceImpl(BarcdMapper barcdMapper, MessageService messageService) {
        this.barcdMapper = barcdMapper;
        this.messageService = messageService;
    }


    /** 영수증별매출상세현황 - 영수증별매출상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getBarcdList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO) {
    	barcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	barcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	
    	if (barcdVO.getStoreCd() != null && !"".equals(barcdVO.getStoreCd())) {
    		String[] arrStoreCd = barcdVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				barcdVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        return barcdMapper.getBarcdList(barcdVO);
    }


    /** 영수증별매출상세현황 - 영수증별매출상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getBarcdDtlList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO) {
    	barcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	barcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		
		if (barcdVO.getStoreCd() != null && !"".equals(barcdVO.getStoreCd())) {
    		String[] arrStoreCd = barcdVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				barcdVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        return barcdMapper.getBarcdDtlList(barcdVO);
	}
}
