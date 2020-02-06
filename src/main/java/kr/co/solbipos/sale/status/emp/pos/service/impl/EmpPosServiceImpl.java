package kr.co.solbipos.sale.status.emp.pos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.pos.service.EmpPosService;
import kr.co.solbipos.sale.status.emp.pos.service.EmpPosVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EmpPosService")
public class EmpPosServiceImpl implements EmpPosService {
    private final EmpPosMapper empPosMapper;
    private final MessageService messageService;

    @Autowired
    public EmpPosServiceImpl(EmpPosMapper empPosMapper, MessageService messageService) {
    	this.empPosMapper = empPosMapper;
        this.messageService = messageService;
    }

    /** 판매자별 매출 -포스별 리스트 조회  */
    @Override
    public List<DefaultMap<String>> getEmpPosList(EmpPosVO empPosVO, SessionInfoVO sessionInfoVO) {
  
    	empPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(empPosVO.getStoreCd()).equals("")) {
        	empPosVO.setArrStoreCd(empPosVO.getStoreCd().split(","));
        }
    	
        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        
        List<DefaultMap<String>> empNo = empPosMapper.getEmpMebList(empPosVO);

        for(int i = 0; i < empNo.size(); i++) {
        	String j = empNo.get(i).get("nmcodeCd");
        	String k = empNo.get(i).get("storeCd");
        	sQuery1 +=", SUM(A.REAL_SALE_AMT" + i + ") AS REAL_SALE_AMT" + i +  "\n";
        	sQuery1 +=", SUM(A.BILL_CNT" + i + ") AS BILL_CNT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN SUM(TSDE.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
        	sQuery2 +=", CASE WHEN TSDE.STORE_CD =" + "'"+k+"'" + " AND TSDE.EMP_NO = " + "'"+j+"'" + " THEN COUNT(TSDE.BILL_NO) ELSE NULL END AS BILL_CNT" + i +  "\n";      	
        }
        
        empPosVO.setsQuery1(sQuery1);
        empPosVO.setsQuery2(sQuery2);
        
        return empPosMapper.getEmpPosList(empPosVO);
    }
    
    /** 판매자별 매출 -판매자 리스트 조회  */
    @Override
    public List<DefaultMap<String>> getEmpMebList(EmpPosVO empPosVO, SessionInfoVO sessionInfoVO) {
  
    	empPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(empPosVO.getStoreCd()).equals("")) {
        	empPosVO.setArrStoreCd(empPosVO.getStoreCd().split(","));
        }
        
        return empPosMapper.getEmpMebList(empPosVO);
    }
}
