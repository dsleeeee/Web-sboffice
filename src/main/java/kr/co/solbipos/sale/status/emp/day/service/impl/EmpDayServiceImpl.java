package kr.co.solbipos.sale.status.emp.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayService;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EmpDayService")
public class EmpDayServiceImpl implements EmpDayService {
    private final EmpDayMapper empDayMapper;
    private final MessageService messageService;

    @Autowired
    public EmpDayServiceImpl(EmpDayMapper empDayMapper, MessageService messageService) {
    	this.empDayMapper = empDayMapper;
        this.messageService = messageService;
    }

    /** 판매자별 매출 -일자별 리스트 조회  */
    @Override
    public List<DefaultMap<String>> getEmpDayList(EmpDayVO empDayVO, SessionInfoVO sessionInfoVO) {
  
    	empDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(empDayVO.getStoreCd()).equals("")) {
        	empDayVO.setArrStoreCd(empDayVO.getStoreCd().split(","));
        }
    	
        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        
        List<DefaultMap<String>> empNo = empDayMapper.getEmpMebList(empDayVO);

        for(int i = 0; i < empNo.size(); i++) {
        	String j = empNo.get(i).get("nmcodeCd");
        	String k = empNo.get(i).get("storeCd");
        	sQuery1 +=", NVL(SUM(A.REAL_SALE_AMT" + i + "), 0) AS REAL_SALE_AMT" + i +  "\n";
        	sQuery1 +=", NVL(SUM(A.BILL_CNT" + i + "), 0) AS BILL_CNT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN SUM(TSDE.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN SUM(TSDE.BILL_CNT) ELSE NULL END AS BILL_CNT" + i +  "\n";     	
        }
        
        empDayVO.setsQuery1(sQuery1);
        empDayVO.setsQuery2(sQuery2);
        
        return empDayMapper.getEmpDayList(empDayVO);
    }
    /** 판매자별 매출 -일자별 리스트(엑셀) 조회  */
    @Override
    public List<DefaultMap<String>> getEmpDayExcelList(EmpDayVO empDayVO, SessionInfoVO sessionInfoVO) {
  
    	empDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(empDayVO.getStoreCd()).equals("")) {
        	empDayVO.setArrStoreCd(empDayVO.getStoreCd().split(","));
        }
    	
        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        
        List<DefaultMap<String>> empNo = empDayMapper.getEmpMebList(empDayVO);

        for(int i = 0; i < empNo.size(); i++) {
        	String j = empNo.get(i).get("nmcodeCd");
        	String k = empNo.get(i).get("storeCd");
        	sQuery1 +=", NVL(SUM(A.REAL_SALE_AMT" + i + "), 0) AS REAL_SALE_AMT" + i +  "\n";
        	sQuery1 +=", NVL(SUM(A.BILL_CNT" + i + "), 0) AS BILL_CNT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN SUM(TSDE.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN SUM(TSDE.BILL_CNT) ELSE NULL END AS BILL_CNT" + i +  "\n";     	
        }
        
        empDayVO.setsQuery1(sQuery1);
        empDayVO.setsQuery2(sQuery2);
        
        return empDayMapper.getEmpDayExcelList(empDayVO);
    }
    
    /** 판매자별 매출 -판매자 리스트 조회  */
    @Override
    public List<DefaultMap<String>> getEmpMebList(EmpDayVO empDayVO, SessionInfoVO sessionInfoVO) {
  
    	empDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(empDayVO.getStoreCd()).equals("")) {
        	empDayVO.setArrStoreCd(empDayVO.getStoreCd().split(","));
        }
    	
        return empDayMapper.getEmpMebList(empDayVO);
    }
}
