package kr.co.solbipos.sale.status.corner.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.dayOfWeek.service.CornerDayOfWeekService;
import kr.co.solbipos.sale.status.corner.dayOfWeek.service.CornerDayOfWeekVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cornerDayOfWeekService")
public class CornerDayOfWeekServiceImpl implements CornerDayOfWeekService {
    private final CornerDayOfWeekMapper cornerDayOfWeekMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public CornerDayOfWeekServiceImpl(CornerDayOfWeekMapper cornerDayOfWeekMapper, PopupMapper popupMapper, MessageService messageService) {
        this.cornerDayOfWeekMapper = cornerDayOfWeekMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 코너별 매출 - 요일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCornerDayOfWeekList(CornerDayOfWeekVO cornerDayOfWeekVO, SessionInfoVO sessionInfoVO) {
    	cornerDayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	if (cornerDayOfWeekVO.getCornrCd() != null && !"".equals(cornerDayOfWeekVO.getCornrCd())) {
    		String[] arrCornrCd = cornerDayOfWeekVO.getCornrCd().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				cornerDayOfWeekVO.setArrCornrCd(arrCornrCd);
    				cornerDayOfWeekVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(cornerDayOfWeekVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(cornerDayOfWeekVO.getStoreCd(), 3900));
                cornerDayOfWeekVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
    	
    	if(!StringUtil.getOrBlank(cornerDayOfWeekVO.getArrCornrCd()).equals("")) {
    		// 동적 컬럼 생성을 위한 쿼리 변수.
        	String sQuery1 = "";
        	for(int i=0; i<cornerDayOfWeekVO.getArrCornrCd().length; i++) {
        		String[] list = cornerDayOfWeekVO.getArrCornrCd();
        		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.REAL_SALE_AMT END),'0') REAL_SALE_AMT"+ i +"\n";
        		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.TOT_SALE_QTY END),'0') SALE_QTY"+ i +"\n";
        	}
        	cornerDayOfWeekVO.setsQuery1(sQuery1);
    	}
        return cornerDayOfWeekMapper.getCornerDayOfWeekList(cornerDayOfWeekVO);
    }

}
