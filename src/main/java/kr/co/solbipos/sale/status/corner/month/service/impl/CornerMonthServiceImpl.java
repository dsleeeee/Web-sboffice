package kr.co.solbipos.sale.status.corner.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.month.service.CornerMonthService;
import kr.co.solbipos.sale.status.corner.month.service.CornerMonthVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cornerMonthService")
public class CornerMonthServiceImpl implements CornerMonthService {
    private final CornerMonthMapper cornerMonthMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public CornerMonthServiceImpl(CornerMonthMapper cornerMonthMapper, PopupMapper popupMapper, MessageService messageService) {
        this.cornerMonthMapper = cornerMonthMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 코너별 매출현황 - 월별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCornerMonthList(CornerMonthVO cornerMonthVO, SessionInfoVO sessionInfoVO) {
    	cornerMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	if (cornerMonthVO.getCornrCd() != null && !"".equals(cornerMonthVO.getCornrCd())) {
    		String[] arrCornrCd = cornerMonthVO.getCornrCd().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				cornerMonthVO.setArrCornrCd(arrCornrCd);
    				cornerMonthVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(cornerMonthVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(cornerMonthVO.getStoreCd(), 3900));
                cornerMonthVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
    	
    	if(!StringUtil.getOrBlank(cornerMonthVO.getArrCornrCd()).equals("")) {
    		// 동적 컬럼 생성을 위한 쿼리 변수.
        	String sQuery1 = "";
        	for(int i=0; i<cornerMonthVO.getArrCornrCd().length; i++) {
        		String[] list = cornerMonthVO.getArrCornrCd();
        		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.REAL_SALE_AMT END),'0') REAL_SALE_AMT"+ i +"\n";
        		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.TOT_SALE_QTY END),'0') SALE_QTY"+ i +"\n";
        	}
        	cornerMonthVO.setsQuery1(sQuery1);
    	}
        return cornerMonthMapper.getCornerMonthList(cornerMonthVO);
    }


    /** 코너별 매출현황 - 월별 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCornerMonthExcelList(CornerMonthVO cornerMonthVO, SessionInfoVO sessionInfoVO) {
    	cornerMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	if (cornerMonthVO.getCornrCd() != null && !"".equals(cornerMonthVO.getCornrCd())) {
    		String[] arrCornrCd = cornerMonthVO.getCornrCd().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				cornerMonthVO.setArrCornrCd(arrCornrCd);
    				cornerMonthVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(cornerMonthVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(cornerMonthVO.getStoreCd(), 3900));
                cornerMonthVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
    	
    	if(!StringUtil.getOrBlank(cornerMonthVO.getArrCornrCd()).equals("")) {
    		// 동적 컬럼 생성을 위한 쿼리 변수.
        	String sQuery1 = "";
        	for(int i=0; i<cornerMonthVO.getArrCornrCd().length; i++) {
        		String[] list = cornerMonthVO.getArrCornrCd();
        		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.REAL_SALE_AMT END),'0') REAL_SALE_AMT"+ i +"\n";
        		sQuery1 += ", NVL(SUM(CASE STORE_CD||'||'||CORNR_CD WHEN '" + list[i] + "' THEN TSDCP.TOT_SALE_QTY END),'0') SALE_QTY"+ i +"\n";
        	}
        	cornerMonthVO.setsQuery1(sQuery1);
    	}
        return cornerMonthMapper.getCornerMonthExcelList(cornerMonthVO);
	}

}
