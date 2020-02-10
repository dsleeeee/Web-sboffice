package kr.co.solbipos.sale.status.prod.hour.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourService;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdHourService")
public class ProdHourServiceImpl implements ProdHourService {
    private final ProdHourMapper prodHourMapper;
    private final MessageService messageService;

    @Autowired
    public ProdHourServiceImpl(ProdHourMapper prodHourMapper, MessageService messageService) {
    	this.prodHourMapper = prodHourMapper;
        this.messageService = messageService;
    }


    /** 상품별 매출 - 시간대별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdHourList(ProdHourVO prodHourVO, SessionInfoVO sessionInfoVO) {

    	prodHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(prodHourVO.getStoreCd()).equals("")) {
        	prodHourVO.setArrStoreCd(prodHourVO.getStoreCd().split(","));
        }
        
        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery0 = "";
              
        // 매출 시간대 설정
        String isSaleHour = "";
        
        // 시간대 '전체' 선택 시
        if(prodHourVO.getSaleTime().equals("")){

        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('01') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY1" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('01') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT1" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('02') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY2" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('02') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT2" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('03') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY3" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('03') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT3" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('04') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY4" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('04') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT4" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('05') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY5" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('05') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT5" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('06') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY6" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('06') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT6" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('07') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY7" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('07') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT7" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('08') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY8" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('08') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT8" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('09') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY9" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('09') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT9" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('10') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY10" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('10') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT10" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('11') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY11" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('11') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT11" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('12') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY12" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('12') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT12" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('13') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY13" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('13') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT13" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('14') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY14" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('14') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT14" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('15') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY15" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('15') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT15" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('16') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY16" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('16') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT16" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('17') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY17" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('17') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT17" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('18') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY18" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('18') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT18" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('19') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY19" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('19') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT19" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('20') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY20" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('20') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT20" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('21') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY21" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('21') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT21" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('22') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY22" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('22') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT22" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('23') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY23" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('23') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT23" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('24') THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY24" + "\n";
            sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ('24') THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT24" + "\n";
        	
        	
        }else {
        	// 매출 시간대 설정
        	if(prodHourVO.getSaleTime().equals("1")){
        		isSaleHour = "1";
            }else if(prodHourVO.getSaleTime().equals("2")){
            	isSaleHour = "2";
            }else if(prodHourVO.getSaleTime().equals("3")){
            	isSaleHour = "3";
            }else if(prodHourVO.getSaleTime().equals("4")){
            	isSaleHour = "4";
            }else if(prodHourVO.getSaleTime().equals("5")){
            	isSaleHour = "5";
            }else if(prodHourVO.getSaleTime().equals("6")){
            	isSaleHour = "6";
            }else if(prodHourVO.getSaleTime().equals("7")){
            	isSaleHour = "7";
            }else if(prodHourVO.getSaleTime().equals("8")){
            	isSaleHour = "8";
            }else if(prodHourVO.getSaleTime().equals("9")){
            	isSaleHour = "9";
            }else if(prodHourVO.getSaleTime().equals("10")){
            	isSaleHour = "10";
            }else if(prodHourVO.getSaleTime().equals("11")){
            	isSaleHour = "11";
            }else if(prodHourVO.getSaleTime().equals("12")){
            	isSaleHour = "12";
            }else if(prodHourVO.getSaleTime().equals("13")){
            	isSaleHour = "13";
            }else if(prodHourVO.getSaleTime().equals("14")){
            	isSaleHour = "14";
            }else if(prodHourVO.getSaleTime().equals("15")){
            	isSaleHour = "15";
            }else if(prodHourVO.getSaleTime().equals("16")){
            	isSaleHour = "16";
            }else if(prodHourVO.getSaleTime().equals("17")){
            	isSaleHour = "17";
            }else if(prodHourVO.getSaleTime().equals("18")){
            	isSaleHour = "18";
            }else if(prodHourVO.getSaleTime().equals("19")){
            	isSaleHour = "19";
            }else if(prodHourVO.getSaleTime().equals("20")){
            	isSaleHour = "20";
            }else if(prodHourVO.getSaleTime().equals("21")){
            	isSaleHour = "21";
            }else if(prodHourVO.getSaleTime().equals("22")){
            	isSaleHour = "22";
            }else if(prodHourVO.getSaleTime().equals("23")){
            	isSaleHour = "23";
		    }else if(prodHourVO.getSaleTime().equals("24")){
		    	isSaleHour = "24";
		    }
        	
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ("+ isSaleHour +") THEN TSDP.TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY_"+ isSaleHour +"" + "\n";
        	sQuery0 +=", SUM(CASE WHEN TSDT.SALE_HOUR IN ("+ isSaleHour +") THEN TSDP.TOT_SALE_AMT ELSE 0 END) AS TOT_SALE_AMT_"+ isSaleHour +"" + "\n";
        }
        
        prodHourVO.setsQuery1(sQuery0);
        return prodHourMapper.getProdHourList(prodHourVO);
    }

}
