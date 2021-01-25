package kr.co.solbipos.sale.status.pos.dayPeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.dayPeriod.service.PosDayPeriodService;
import kr.co.solbipos.sale.status.pos.dayPeriod.service.PosDayPeriodVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posDayPeriodService")
public class PosDayPeriodServiceImpl implements PosDayPeriodService {
    private final PosDayPeriodMapper posDayPeriodMapper;
    private final MessageService messageService;

    @Autowired
    public PosDayPeriodServiceImpl(PosDayPeriodMapper posDayPeriodMapper, MessageService messageService) {
        this.posDayPeriodMapper = posDayPeriodMapper;
        this.messageService = messageService;
    }

	/** 설정기간별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayPeriodList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO) {

    	posDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	posDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

    	if (posDayPeriodVO.getStoreCd() != null && !"".equals(posDayPeriodVO.getStoreCd())) {
    		String[] arrStoreCd = posDayPeriodVO.getStoreCd().split(",");
	        if (arrStoreCd.length > 0) {
	    		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
	    			posDayPeriodVO.setArrStoreCd(arrStoreCd);
	    		}
	    	}
    	}

        return posDayPeriodMapper.getPosDayPeriodList(posDayPeriodVO);
    }

	/** 설정기간별탭 - 엑셀 조회 */
	@Override
	public List<DefaultMap<String>> getPosDayPeriodExcelList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO) {

		posDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		posDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		if (posDayPeriodVO.getStoreCd() != null && !"".equals(posDayPeriodVO.getStoreCd())) {
			String[] arrStoreCd = posDayPeriodVO.getStoreCd().split(",");
			if (arrStoreCd.length > 0) {
				if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
					posDayPeriodVO.setArrStoreCd(arrStoreCd);
				}
			}
		}

		return posDayPeriodMapper.getPosDayPeriodExcelList(posDayPeriodVO);
	}

	/** 설정기간별탭 - 상세 조회 */
	@Override
	public List<DefaultMap<String>> getPosDayPeriodDtlList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO) {

		posDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	posDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		if (posDayPeriodVO.getStoreCd() != null && !"".equals(posDayPeriodVO.getStoreCd())) {
    		String[] arrStoreCd = posDayPeriodVO.getStoreCd().split(",");
	        if (arrStoreCd.length > 0) {
	    		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
	    			posDayPeriodVO.setArrStoreCd(arrStoreCd);
	    		}
	    	}
    	}

        return posDayPeriodMapper.getPosDayPeriodDtlList(posDayPeriodVO);
	}

	/** 설정기간별탭 - 상세 엑셀 조회 */
	@Override
	public List<DefaultMap<String>> getPosDayPeriodDtlExcelList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO) {

		posDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	posDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		if (posDayPeriodVO.getStoreCd() != null && !"".equals(posDayPeriodVO.getStoreCd())) {
    		String[] arrStoreCd = posDayPeriodVO.getStoreCd().split(",");
	        if (arrStoreCd.length > 0) {
	    		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
	    			posDayPeriodVO.setArrStoreCd(arrStoreCd);
	    		}
	    	}
    	}

        return posDayPeriodMapper.getPosDayPeriodDtlExcelList(posDayPeriodVO);
	}

}