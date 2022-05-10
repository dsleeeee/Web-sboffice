package kr.co.solbipos.sale.anals.goal.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.goal.service.GoalService;
import kr.co.solbipos.sale.anals.goal.service.GoalVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("GoalService")
public class GoalServiceImpl implements GoalService {
    private final GoalMapper goalMapper;
    private final MessageService messageService;

    @Autowired
    public GoalServiceImpl(GoalMapper goalMapper, MessageService messageService) {
    	this.goalMapper = goalMapper;
        this.messageService = messageService;
    }

    /**매출목표관리 - 일자별 목표대비 매출 리스트 조회   */
    @Override
	public List<DefaultMap<String>> getSaleGoalDayColList(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
    	goalVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	goalVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
    	 if(!StringUtil.getOrBlank(goalVO.getStoreCd()).equals("")) {
     		String[] arrStoreCd = goalVO.getStoreCd().split(",");
     		if (arrStoreCd.length > 0) {
     			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
     				goalVO.setArrStoreCd(arrStoreCd);
     			}
     		}
    	 }
    	 
    	return goalMapper.getSaleGoalDayColList(goalVO);
	}
    
    /**매출목표관리 - 일자별 목표대비 매출 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getSaleGoalDayList(GoalVO goalVO, SessionInfoVO sessionInfoVO) {

		goalVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		goalVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(goalVO.getStoreCd()).equals("")) {
    		String[] arrStoreCd = goalVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				goalVO.setArrStoreCd(arrStoreCd);
    			}
    			// 동적 컬럼 생성을 위한 쿼리 변수.
    	    	String sQuery1 = "";
    	    	String sQuery2 = "";
    	    	String startDate = goalVO.getStartDate();
	    		String[] list = goalVO.getArrStoreCd();
    	    	for(int i=0; i<goalVO.getArrStoreCd().length; i++) {
    	    		sQuery1 += ", NVL((SELECT SALE_GOAL_AMT FROM TB_SL_SALE_GOAL_MONTHLY WHERE SALE_GOAL_YM = '" +startDate+"' AND STORE_CD = '" + list[i] + "'),0) AS SALE_GOAL_MONTHLY_AMT_"+ i +"\n";
    	    		sQuery1 += ", (SELECT STORE_NM FROM TB_MS_STORE WHERE STORE_CD = '" + list[i] + "') AS STORE_NM_"+ i +"\n";
    	    		sQuery1 += ", NVL(SUM(M.SALE_GOAL_AMT_"+ i +"),0) AS SALE_GOAL_AMT_"+ i +"\n";
    	    		sQuery1 += ", NVL(SUM(M.TOT_SALE_AMT_"+ i +"),0)  AS TOT_SALE_AMT_"+ i +"\n";
    	    		sQuery1 += ", NVL(SUM(SUM(M.TOT_SALE_AMT_"+ i +")) OVER(ORDER BY M.SALE_GOAL_DATE ASC),0) AS ACC_"+ i +"\n";
    	    		sQuery1 += ", NVL(ROUND(NVL((1+(NVL(SUM(M.TOT_SALE_AMT_"+ i +"),0)-SUM(M.SALE_GOAL_AMT_"+ i +"))/DECODE(ABS(SUM(M.SALE_GOAL_AMT_"+ i +")),0,NULL,ABS(SUM(M.SALE_GOAL_AMT_"+ i +")))),0)*100,1),0) AS GOAL_ACHI_"+ i +"\n";
    	    		sQuery2 += ", NVL(SUM(DECODE(TSSGD.STORE_CD,'" + list[i] + "',TSSGD.SALE_GOAL_AMT,0)),0)   AS SALE_GOAL_AMT_"+ i +"\n";
    	    		sQuery2 += ", NVL(SUM(DECODE(TSSGD.STORE_CD,'" + list[i] + "',TSDT.TOT_SALE_AMT,0)),0)	AS TOT_SALE_AMT_"+ i +"\n";
    	    	}
    	    	goalVO.setsQuery1(sQuery1);
    	    	goalVO.setsQuery2(sQuery2);
    		}
    	}
        return goalMapper.getSaleGoalDayList(goalVO);
    }
    
    /**매출목표관리 - 월별 목표대비 매출 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getSaleGoalMonthList(GoalVO goalVO, SessionInfoVO sessionInfoVO) {

		goalVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		goalVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(goalVO.getStoreCd()).equals("")) {
        	goalVO.setArrStoreCd(goalVO.getStoreCd().split(","));
        }
    	        
        return goalMapper.getSaleGoalMonthList(goalVO);
    }
    
    /**매출목표관리 - 매장 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getSaleGoalStoreList(GoalVO goalVO, SessionInfoVO sessionInfoVO) {

		goalVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	goalVO.setStoreCd(sessionInfoVO.getStoreCd());
		goalVO.setEmpNo(sessionInfoVO.getEmpNo());

        return goalMapper.getSaleGoalStoreList(goalVO);
    }
    
    /**매출목표관리 - 매출목표 조회   */
    @Override
    public List<DefaultMap<String>> getSaleGoalList(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
  
    	goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	  	        
        return goalMapper.getSaleGoalList(goalVO);
    }
    
    /**매출목표관리 - 매출목표 상세조회   */
    @Override
    public List<DefaultMap<String>> getSaleGoalDtl1List(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
  
    	goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	  	        
        return goalMapper.getSaleGoalDtl1List(goalVO);
    }
    
    /**매출목표관리 - 매출목표 상세조회   */
    @Override
    public List<DefaultMap<String>> getSaleGoalDtl2List(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
  
    	goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	  	        
        return goalMapper.getSaleGoalDtl2List(goalVO);
    }
    
    /**매출목표관리 - 매출목표 등록   */
    @Override
    public int saveSaleGoalSave(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
    	
    	String currentDt = currentDateTimeString();

    	goalVO.setRegId(sessionInfoVO.getUserId());
    	goalVO.setRegDt(currentDt);
    	goalVO.setModId(sessionInfoVO.getUserId());
    	goalVO.setModDt(currentDt);
    	
    	int result = 0;
        try {
        	//MONTH 입력
        	 result = goalMapper.saveSaleGoalSave(goalVO);        	 
        	 try {
        		 for (int i = 1; i < 8; i++) {
        			 
        			 if(i == 1) {
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight1());
        				 goalVO.setSaleGoalWeightNo("1");
        			 }else if(i == 2) {
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight2());
        				 goalVO.setSaleGoalWeightNo("2");
         			 }else if(i == 3) {
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight3());
        				 goalVO.setSaleGoalWeightNo("3");
         			 }else if(i == 4) {
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight4());
        				 goalVO.setSaleGoalWeightNo("4");
         			 }else if(i == 5) {
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight5());
        				 goalVO.setSaleGoalWeightNo("5");
         			 }else if(i == 6) {
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight6());
        				 goalVO.setSaleGoalWeightNo("6");
         			 }else if(i == 7) {
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight7());
        				 goalVO.setSaleGoalWeightNo("7");
        			 }
        			 
        			
        		 //WEIGHT 입력 
        		 goalMapper.saveGoalWeight(goalVO);       		 
        		 }
        	 }catch(Exception e) {
        		 e.printStackTrace();
        	 }
        	 try {
        		 String goalYear  = goalVO.getSaleGoalYm().substring(0, 4);
        		 String goalMonth = goalVO.getSaleGoalYm().substring(4, 6);		
        		 
        		 int year = Integer.parseInt(goalYear);
        		 int month = Integer.parseInt(goalMonth);
        		 int days=0;
        		 
        		 if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) { 
        			 days=31; 
        		 }else if(month==2) { 
        			 if(year%4==0 && year%100!=0 || year%400==0) days=29; 
        			 else days=28; 
        		 }else if(month==4 || month==6 || month==9 || month==11) { 
        			 days=30; 
        	     }

        		  int yyyy = Integer.parseInt(goalYear);
        		  int mm = Integer.parseInt(goalMonth);
        		  int sun = 0;	//일
        		  int mon = 0;	//월
        		  int tue = 0;	//화
        		  int wed = 0;	//수
        		  int thu = 0;	//목
        		  int fri = 0;	//금
        		  int sat = 0;	//토
        		  Calendar cal = Calendar.getInstance();

        		  cal.set(yyyy, mm-1, 1);
        		  int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        		  for (int i=1; i<maxDate+1; i++){
        			   cal.clear();
        			   cal.set(yyyy, mm-1, i);
        			   if(cal.get(cal.DAY_OF_WEEK) == java.util.Calendar.SUNDAY) {    				   
        				   sun += 1;
        			   }
        			   if(cal.get(cal.DAY_OF_WEEK) == java.util.Calendar.MONDAY) {    				   
        				   mon += 1;
        			   }
        			   if(cal.get(cal.DAY_OF_WEEK) == java.util.Calendar.TUESDAY) {    				   
        				   tue += 1;
        			   }
        			   if(cal.get(cal.DAY_OF_WEEK) == java.util.Calendar.WEDNESDAY) {    				   
        				   wed += 1;
        			   }
        			   if(cal.get(cal.DAY_OF_WEEK) == java.util.Calendar.THURSDAY) {    				   
        				   thu += 1;
        			   }
        			   if(cal.get(cal.DAY_OF_WEEK) == java.util.Calendar.FRIDAY) {    				   
        				   fri += 1;
        			   }
        			   if(cal.get(cal.DAY_OF_WEEK) == java.util.Calendar.SATURDAY) {    				   
        				   sat += 1;
        			   }       			   
        			   cal.clear();
        		  }
        		  
        		  int total = (Integer.parseInt(goalVO.getSaleGoalWeight1())*sun)+(Integer.parseInt(goalVO.getSaleGoalWeight2())*mon)+(Integer.parseInt(goalVO.getSaleGoalWeight3())*tue)+(Integer.parseInt(goalVO.getSaleGoalWeight4())*wed)
        				  		+(Integer.parseInt(goalVO.getSaleGoalWeight5())*thu)+(Integer.parseInt(goalVO.getSaleGoalWeight6())*fri)+(Integer.parseInt(goalVO.getSaleGoalWeight7())*sat);
        		  
        		 goalVO.setSaleGoalWeightNoTot(String.valueOf(total));


        		 for (int i = 1; i <= days; i++) {
        			 //DAILY 입력
        			 
        			 String goaldate = goalVO.getSaleGoalYm()+String.format("%02d", i);;
        	
        			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd") ;
        			 java.util.Date nDate = dateFormat.parse(goaldate) ;
        			     
        			 Calendar calDate = Calendar.getInstance() ;
        			 calDate.setTime(nDate);
        			     
        			    int dayNum = calDate.get(Calendar.DAY_OF_WEEK) ;
        			 
        			 if(dayNum == 1){
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight1());
        			 }else if(dayNum == 2){
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight2());
        			 }else if(dayNum == 3){
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight3());
        			 }else if(dayNum == 4){
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight4());
        			 }else if(dayNum == 5){
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight5());
        			 }else if(dayNum == 6){
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight6());
        			 }else if(dayNum == 7){
        				 goalVO.setSaleGoalWeight(goalVO.getSaleGoalWeight7());
        			 }
        			 
        			 if(i == days) {
        				 goalVO.setSaleEndDateYn("Y");
        				 goalVO.setSaleEndDate(goaldate);
        			 }else {
        				 goalVO.setSaleEndDateYn("N");
        				 goalVO.setSaleEndDate(goaldate);
        			 }
        			 
        			 goalVO.setSaleGoalDate(goaldate);
        			 
        			 goalMapper.saveGoalDaily(goalVO);        		
        		 }
 
    
        	 }catch(Exception e) {
        		 e.printStackTrace();
        	 }
        } catch(Exception e) {
             e.printStackTrace();
        }
        
        return result;
    }
    
    
    /**매출목표관리 - 매출목표 상세등록   */
    @Override
    public int saveSaleGoalgoalDeatilSave(GoalVO[] goalVOs, SessionInfoVO sessionInfoVO) {
    	int 	result 		= 0;
     	String currentDt = currentDateTimeString();


    	 for(GoalVO goalVO : goalVOs){
    		 goalVO.setRegId(sessionInfoVO.getUserId());
    	     goalVO.setRegDt(currentDt);
    	     goalVO.setModId(sessionInfoVO.getUserId());
    	     goalVO.setModDt(currentDt);
    	     
    	     result += goalMapper.saveSaleGoalgoalDeatilSave(goalVO);
    	 }
		return result;
    }
    
    /**매출목표관리 - 매출목표금액TOT 저장   */
    @Override
    public int saveSaleGoalAmtTotSave(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
    	
    	String currentDt = currentDateTimeString();

    	goalVO.setRegId(sessionInfoVO.getUserId());
    	goalVO.setRegDt(currentDt);
    	goalVO.setModId(sessionInfoVO.getUserId());
    	goalVO.setModDt(currentDt);
    	
    	int result = 0;
        try {
        	 result = goalMapper.saveSaleGoalAmtTotSave(goalVO);        	 
        	
        } catch(Exception e) {
             e.printStackTrace();
        }
        
        return result;
    }

    /**매출목표관리 - 일자별 목표대비 매출 엑셀리스트 조회   */
	@Override
	public List<DefaultMap<String>> getSaleGoalDayExcelList(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
		  
    	goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(goalVO.getStoreCd()).equals("")) {
    		String[] arrStoreCd = goalVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				goalVO.setArrStoreCd(arrStoreCd);
    			}
    			// 동적 컬럼 생성을 위한 쿼리 변수.
    	    	String sQuery1 = "";
    	    	String sQuery2 = "";
    	    	String startDate = goalVO.getStartDate();
	    		String[] list = goalVO.getArrStoreCd();
    	    	for(int i=0; i<goalVO.getArrStoreCd().length; i++) {
    	    		sQuery1 += ", NVL((SELECT SALE_GOAL_AMT FROM TB_SL_SALE_GOAL_MONTHLY WHERE SALE_GOAL_YM = '" +startDate+"' AND STORE_CD = '" + list[i] + "'),0) AS SALE_GOAL_MONTHLY_AMT_"+ i +"\n";
    	    		sQuery1 += ", (SELECT STORE_NM FROM TB_MS_STORE WHERE STORE_CD = '" + list[i] + "') AS STORE_NM_"+ i +"\n";
    	    		sQuery1 += ", NVL(SUM(M.SALE_GOAL_AMT_"+ i +"),0) AS SALE_GOAL_AMT_"+ i +"\n";
    	    		sQuery1 += ", NVL(SUM(M.TOT_SALE_AMT_"+ i +"),0)  AS TOT_SALE_AMT_"+ i +"\n";
    	    		sQuery1 += ", NVL(SUM(SUM(M.TOT_SALE_AMT_"+ i +")) OVER(ORDER BY M.SALE_GOAL_DATE ASC),0) AS ACC_"+ i +"\n";
    	    		sQuery1 += ", NVL(ROUND(NVL((1+(NVL(SUM(M.TOT_SALE_AMT_"+ i +"),0)-SUM(M.SALE_GOAL_AMT_"+ i +"))/DECODE(ABS(SUM(M.SALE_GOAL_AMT_"+ i +")),0,NULL,ABS(SUM(M.SALE_GOAL_AMT_"+ i +")))),0)*100,1),0) AS GOAL_ACHI_"+ i +"\n";
    	    		sQuery2 += ", NVL(SUM(DECODE(TSSGD.STORE_CD,'" + list[i] + "',TSSGD.SALE_GOAL_AMT,0)),0)   AS SALE_GOAL_AMT_"+ i +"\n";
    	    		sQuery2 += ", NVL(SUM(DECODE(TSSGD.STORE_CD,'" + list[i] + "',TSDT.TOT_SALE_AMT,0)),0)	AS TOT_SALE_AMT_"+ i +"\n";
    	    	}
    	    	goalVO.setsQuery1(sQuery1);
    	    	goalVO.setsQuery2(sQuery2);
    		}
    	}
        return goalMapper.getSaleGoalDayExcelList(goalVO);
	}

	/**매출목표관리 - 월별 목표대비 매출 엑셀리스트 조회   */
	@Override
	public List<DefaultMap<String>> getSaleGoalMonthExcelList(GoalVO goalVO, SessionInfoVO sessionInfoVO) {
		  
    	goalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(goalVO.getStoreCd()).equals("")) {
        	goalVO.setArrStoreCd(goalVO.getStoreCd().split(","));
        }
    	        
        return goalMapper.getSaleGoalMonthExcelList(goalVO);
	}
}
