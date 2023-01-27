package kr.co.solbipos.sale.anals.store.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthService;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StoreMonthService")
public class StoreMonthServiceImpl implements StoreMonthService {
    private final StoreMonthMapper storeMonthMapper;
    private final MessageService messageService;

    @Autowired
    public StoreMonthServiceImpl(StoreMonthMapper storeMonthMapper, MessageService messageService) {
    	this.storeMonthMapper = storeMonthMapper;
        this.messageService = messageService;
    }

    /** 매장월별순위 - 매장월별순위 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreMonthList(StoreMonthVO storeMonthVO, SessionInfoVO sessionInfoVO) {

    	storeMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        
        String arrayStoreCd = "";
        String[] array = storeMonthVO.getStoreCd().split(",");
        for(int i=0; i<array.length;i++) {
        	if(i > 0) {
        		arrayStoreCd += ",'"+array[i]+"'";
        	}else {
        		arrayStoreCd += "'"+array[i]+"'";
        	}
        }
        
        
    	List<DefaultMap<String>> date = storeMonthMapper.getMonthColList(storeMonthVO);
        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        String sQuery3 = "";
        
        String k = storeMonthVO.getRowNum();
        String m = storeMonthVO.getHqOfficeCd();
        String storeCd = storeMonthVO.getStoreCd();

        if(date.size() > 1) {
	        for(int i = 0; i < date.size(); i++) {
	        	
		        String j = date.get(i).get("month");
	        
		    	sQuery1 +=", MAX(M.STORE_CD"+ j +") AS STORE_CD" + j + "\n";
		    	sQuery1 +=", MAX(M.STORE_NM"+ j +") AS STORE_NM" + j + "\n";
		    	sQuery1 +=", MAX(M.INDEX_NO"+ j +") AS INDEX_NO" + j + "\n";
		    	sQuery1 +=", MAX(M.REAL_SALE_AMT"+ j +") AS REAL_SALE_AMT" + j + "\n";
		    	sQuery1 +=", MAX(M.BILL_CNT"+ j +") AS BILL_CNT" + j + "\n";
		    	sQuery1 +=", ROUND(SUM(REAL_SALE_AMT"+ j +")/DECODE(SUM(M.BILL_CNT"+ j +"),0, NULL, SUM(BILL_CNT" + j +")),0) AS BILL_UPRC" + j + "\n";
		    	sQuery1 +=", ROUND(RATIO_TO_REPORT(SUM(REAL_SALE_AMT" + j + ")) OVER(), 2)  *100 AS STORE_RAT" + j + "\n";
		    	
		    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.STORE_CD,'') AS STORE_CD" + j + "\n";
		    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.STORE_NM,'') AS STORE_NM" + j + "\n";
		    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.INDEX_NO,'') AS INDEX_NO" + j + "\n";
		    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.REAL_SALE_AMT,'') AS REAL_SALE_AMT" + j + "\n";
		    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.BILL_CNT,'') AS BILL_CNT" + j + "\n";
		    	
		    	if(i != 0) {
		    	sQuery3 +=" UNION ALL" + "\n";
		    	}
		    	sQuery3 +=" SELECT"+"'"+ j +"'"+" GBN, M.RN, M.STORE_CD, M.STORE_NM, M.INDEX_NO, M.REAL_SALE_AMT, M.BILL_CNT" + "\n";
		        sQuery3 +=" FROM (" + "\n";
		    	sQuery3 +=" SELECT TSDT.STORE_CD" + "\n";
		    	sQuery3 +=", TMS.STORE_NM, TMSI.INDEX_NO, SUM(TSDT.REAL_SALE_AMT) AS REAL_SALE_AMT, SUM(TSDT.REAL_SALE_CNT) AS BILL_CNT, RANK() OVER (ORDER BY SUM(TSDT.REAL_SALE_AMT) DESC)  RN" + "\n";
		    	sQuery3 +=" FROM TB_SL_MONTHLY_TOTAL TSDT,TB_MS_STORE TMS, TB_MS_STORE_INFO TMSI WHERE TSDT.STORE_CD = TMS.STORE_CD AND TMSI.STORE_CD (+) = TMS.STORE_CD " + "\n";
		    	sQuery3 +=" AND TSDT.HQ_OFFICE_CD = " + "'"+ m +"'" + "\n";
		    	sQuery3 +=" AND TSDT.SALE_YM     = " + j + "\n";
		    	if(!storeCd.equals("")) {
		    		sQuery3 +=" AND TSDT.STORE_CD IN  (" + arrayStoreCd + ")" + "\n";
		    	}
		    	if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
					sQuery3 +="AND (" +
							"'N' = (SELECT NVL(MAX(ENVST_VAL), 'N') FROM TB_HQ_ENVST WHERE HQ_OFFICE_CD = '" + sessionInfoVO.getHqOfficeCd() + "' AND ENVST_CD = '0001')" +
							"OR TSDT.STORE_CD IN (SELECT STORE_CD  FROM TB_HQ_EMPLOYEE_STORE_V08 WHERE HQ_OFFICE_CD = '" + sessionInfoVO.getHqOfficeCd() + "' AND EMP_NO = '" + sessionInfoVO.getEmpNo() + "')" +
							")";
				}
		    	sQuery3 +=" GROUP BY TSDT.STORE_CD, TMS.STORE_NM, TMSI.INDEX_NO ) M WHERE RN <= " + k + "\n";
	        }        
	        storeMonthVO.setsQuery1(sQuery1);
	        storeMonthVO.setsQuery2(sQuery2);
	        storeMonthVO.setsQuery3(sQuery3);
        }else {
       		        	
        	String j = date.get(0).get("month");
		    
	    	sQuery1 +=", MAX(M.STORE_CD"+ j +") AS STORE_CD" + j + "\n";
	    	sQuery1 +=", MAX(M.STORE_NM"+ j +") AS STORE_NM" + j + "\n";
	    	sQuery1 +=", MAX(M.INDEX_NO"+ j +") AS INDEX_NO" + j + "\n";
	    	sQuery1 +=", MAX(M.REAL_SALE_AMT"+ j +") AS REAL_SALE_AMT" + j + "\n";
	    	sQuery1 +=", MAX(M.BILL_CNT"+ j +") AS BILL_CNT" + j + "\n";
	    	sQuery1 +=", ROUND(SUM(REAL_SALE_AMT"+ j +")/DECODE(SUM(M.BILL_CNT"+ j +"),0, NULL, SUM(BILL_CNT" + j +")),0) AS BILL_UPRC" + j + "\n";
	    	sQuery1 +=", ROUND(RATIO_TO_REPORT(SUM(REAL_SALE_AMT" + j + ")) OVER(), 4) *100 AS STORE_RAT" + j + "\n";
	    	
	    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.STORE_CD,'') AS STORE_CD" + j + "\n";
	    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.STORE_NM,'') AS STORE_NM" + j + "\n";
	    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.INDEX_NO,'') AS INDEX_NO" + j + "\n";
	    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.REAL_SALE_AMT,'') AS REAL_SALE_AMT" + j + "\n";
	    	sQuery2 +=", DECODE(M.GBN,"+ j +",M.BILL_CNT,'') AS BILL_CNT" + j + "\n";
	    	
	    	sQuery3 +=" SELECT"+"'"+ j +"'"+" GBN, M.RN, M.STORE_CD, M.STORE_NM, M.INDEX_NO, M.REAL_SALE_AMT, M.BILL_CNT" + "\n";
	        sQuery3 +=" FROM (" + "\n";
	    	sQuery3 +=" SELECT TSDT.STORE_CD" + "\n";
	    	sQuery3 +=", TMS.STORE_NM, TMSI.INDEX_NO, SUM(TSDT.REAL_SALE_AMT) AS REAL_SALE_AMT, SUM(TSDT.REAL_SALE_CNT) AS BILL_CNT, RANK() OVER (ORDER BY SUM(TSDT.REAL_SALE_AMT) DESC)  RN" + "\n";
	    	sQuery3 +=" FROM TB_SL_MONTHLY_TOTAL TSDT,TB_MS_STORE TMS, TB_MS_STORE_INFO TMSI WHERE TSDT.STORE_CD = TMS.STORE_CD AND TMSI.STORE_CD (+) = TMS.STORE_CD " + "\n";
	    	sQuery3 +=" AND TSDT.HQ_OFFICE_CD = " + "'"+ m +"'" + "\n";
	    	sQuery3 +=" AND TSDT.SALE_YM     = " + j + "\n";
	    	if(!storeCd.equals("")) {
	    		sQuery3 +=" AND TSDT.STORE_CD IN  (" + arrayStoreCd + ")" + "\n";
	    	}
			if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
				sQuery3 +="AND (" +
						"'N' = (SELECT NVL(MAX(ENVST_VAL), 'N') FROM TB_HQ_ENVST WHERE HQ_OFFICE_CD = '" + sessionInfoVO.getHqOfficeCd() + "' AND ENVST_CD = '0001')" +
						"OR TSDT.STORE_CD IN (SELECT STORE_CD  FROM TB_HQ_EMPLOYEE_STORE_V08 WHERE HQ_OFFICE_CD = '" + sessionInfoVO.getHqOfficeCd() + "' AND EMP_NO = '" + sessionInfoVO.getEmpNo() + "')" +
						")";
			}
	    	sQuery3 +=" GROUP BY TSDT.STORE_CD, TMS.STORE_NM, TMSI.INDEX_NO ) M WHERE RN <= " + k + "\n";
	    	
	    	storeMonthVO.setSaleDate(j);
	        storeMonthVO.setsQuery1(sQuery1);
	        storeMonthVO.setsQuery2(sQuery2);
	        storeMonthVO.setsQuery3(sQuery3);
        }
        
        return storeMonthMapper.getStoreMonthList(storeMonthVO);
    }
    
    /** 매장월별순위 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMonthColList(StoreMonthVO storeMonthVO, SessionInfoVO sessionInfoVO) {
        return storeMonthMapper.getMonthColList(storeMonthVO);
    }
}
