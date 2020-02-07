package kr.co.solbipos.sale.status.emp.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.dayOfWeek.service.EmpDayOfWeekService;
import kr.co.solbipos.sale.status.emp.dayOfWeek.service.EmpDayOfWeekVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EmpDayOfWeekService")
public class EmpDayOfWeekServiceImpl implements EmpDayOfWeekService {
    private final EmpDayOfWeekMapper empDayOfWeekMapper;
    private final MessageService messageService;

    @Autowired
    public EmpDayOfWeekServiceImpl(EmpDayOfWeekMapper empDayOfWeekMapper, MessageService messageService) {
    	this.empDayOfWeekMapper = empDayOfWeekMapper;
        this.messageService = messageService;
    }

    /** 판매자별 매출 -요일별 리스트 조회  */
    @Override
    public List<DefaultMap<String>> getEmpDayOfWeekList(EmpDayOfWeekVO empDayOfWeekVO, SessionInfoVO sessionInfoVO) {
  
    	empDayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(empDayOfWeekVO.getStoreCd()).equals("")) {
        	empDayOfWeekVO.setArrStoreCd(empDayOfWeekVO.getStoreCd().split(","));
        }
    	
        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        
        List<DefaultMap<String>> empNo = empDayOfWeekMapper.getEmpMebList(empDayOfWeekVO);

        for(int i = 0; i < empNo.size(); i++) {
        	String j = empNo.get(i).get("nmcodeCd");
        	String k = empNo.get(i).get("storeCd");
        	sQuery1 +=", NVL(SUM(A.REAL_SALE_AMT" + i + "), 0) AS REAL_SALE_AMT" + i +  "\n";
        	sQuery1 +=", NVL(SUM(A.BILL_CNT" + i + "), 0) AS BILL_CNT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN SUM(TSDE.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN SUM(TSDE.BILL_CNT) ELSE NULL END AS BILL_CNT" + i +  "\n";      	
        }
        
        empDayOfWeekVO.setsQuery1(sQuery1);
        empDayOfWeekVO.setsQuery2(sQuery2);
        
        return empDayOfWeekMapper.getEmpDayOfWeekList(empDayOfWeekVO);
    }
    
}
