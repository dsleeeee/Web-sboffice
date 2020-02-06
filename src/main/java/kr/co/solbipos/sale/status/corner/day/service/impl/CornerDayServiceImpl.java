package kr.co.solbipos.sale.status.corner.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.day.service.CornerDayService;
import kr.co.solbipos.sale.status.corner.day.service.CornerDayVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cornerDayService")
public class CornerDayServiceImpl implements CornerDayService {
    private final CornerDayMapper cornerDayMapper;
    private final MessageService messageService;

    @Autowired
    public CornerDayServiceImpl(CornerDayMapper cornerDayMapper, MessageService messageService) {
        this.cornerDayMapper = cornerDayMapper;
        this.messageService = messageService;
    }


    /** 영수증별매출상세현황 - 영수증별매출상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCornerDayList(CornerDayVO cornerDayVO, SessionInfoVO sessionInfoVO) {
    	cornerDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	if(!StringUtil.getOrBlank(cornerDayVO.getArrCornrCd()).equals("")) {
    		
			// 동적 컬럼 생성을 위한 쿼리 변수.
	    	String sQuery1 = "";
	    	for(int i=0; i<cornerDayVO.getArrCornrCd().length; i++) {
	    		String[] list = cornerDayVO.getArrCornrCd();
	    		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.REAL_SALE_AMT END),'0') REAL_SALE_AMT"+ i +"\n";
	    		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.TOT_SALE_QTY END),'0') SALE_QTY"+ i +"\n";
	    	
	    	}
	    	cornerDayVO.setsQuery1(sQuery1);
    	}
    	return cornerDayMapper.getCornerDayList(cornerDayVO);
    }


    /** 코너별매출 - 매장 코너 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCornerNmList(CornerDayVO cornerDayVO, SessionInfoVO sessionInfoVO) {
		cornerDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return cornerDayMapper.getCornerNmList(cornerDayVO);
	}

}
