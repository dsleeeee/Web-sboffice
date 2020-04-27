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
        String sQuery1 = "";
        String sQuery2 = "";
              
        // 매출 시간대 설정
        String isSaleHour = "";
        
        // 시간대 '전체' 선택 시
        if(prodHourVO.getSaleTime().equals("")){
        	for(int i = 1; i < 25; i++) {
        		sQuery1 +=", SUM(M.TOT_SALE_QTY"+ i +") AS TOT_SALE_QTY" + i + "\n";
        		sQuery1 +=", SUM(M.TOT_SALE_AMT"+ i +") AS TOT_SALE_AMT" + i + "\n";
        		sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ i +",C.SALE_CNT,0)) AS TOT_SALE_QTY" + i + "\n";
            	sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ i +",C.TOT_SALE_AMT,0)) AS TOT_SALE_AMT" + i + "\n";
        	}
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
        	sQuery1 +=", SUM(M.TOT_SALE_QTY"+ isSaleHour +") AS TOT_SALE_QTY"+ isSaleHour +"" + "\n";
    		sQuery1 +=", SUM(M.TOT_SALE_AMT"+ isSaleHour +") AS TOT_SALE_AMT"+ isSaleHour +"" + "\n";
    		sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ isSaleHour +",C.SALE_CNT,0)) AS TOT_SALE_QTY"+ isSaleHour +"" + "\n";
    		sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ isSaleHour +",C.TOT_SALE_AMT,0)) AS TOT_SALE_AMT"+ isSaleHour +"" + "\n";
        }
        
        prodHourVO.setsQuery1(sQuery1);
        prodHourVO.setsQuery2(sQuery2);
        return prodHourMapper.getProdHourList(prodHourVO);
    }
    
    /** 상품별 매출 - 시간대별 엑셀 다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getProdHourExcelList(ProdHourVO prodHourVO, SessionInfoVO sessionInfoVO) {

    	prodHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(prodHourVO.getStoreCd()).equals("")) {
        	prodHourVO.setArrStoreCd(prodHourVO.getStoreCd().split(","));
        }
        
        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";
              
        // 매출 시간대 설정
        String isSaleHour = "";
        
        // 시간대 '전체' 선택 시
        if(prodHourVO.getSaleTime().equals("")){
        	for(int i = 1; i < 25; i++) {
        		sQuery1 +=", SUM(M.TOT_SALE_QTY"+ i +") AS TOT_SALE_QTY" + i + "\n";
        		sQuery1 +=", SUM(M.TOT_SALE_AMT"+ i +") AS TOT_SALE_AMT" + i + "\n";
        		sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ i +",C.SALE_CNT,0)) AS TOT_SALE_QTY" + i + "\n";
            	sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ i +",C.TOT_SALE_AMT,0)) AS TOT_SALE_AMT" + i + "\n";
        	}
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
        	sQuery1 +=", SUM(M.TOT_SALE_QTY"+ isSaleHour +") AS TOT_SALE_QTY"+ isSaleHour +"" + "\n";
    		sQuery1 +=", SUM(M.TOT_SALE_AMT"+ isSaleHour +") AS TOT_SALE_AMT"+ isSaleHour +"" + "\n";
    		sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ isSaleHour +",C.SALE_CNT,0)) AS TOT_SALE_QTY"+ isSaleHour +"" + "\n";
    		sQuery2 +=", SUM(DECODE(C.SALE_HOUR,"+ isSaleHour +",C.TOT_SALE_AMT,0)) AS TOT_SALE_AMT"+ isSaleHour +"" + "\n";
        }
        
        prodHourVO.setsQuery1(sQuery1);
        prodHourVO.setsQuery2(sQuery2);
        return prodHourMapper.getProdHourExcelList(prodHourVO);
    }
}
