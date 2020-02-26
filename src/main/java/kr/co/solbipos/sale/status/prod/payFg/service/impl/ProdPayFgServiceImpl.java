package kr.co.solbipos.sale.status.prod.payFg.service.impl;

import kr.co.common.data.domain.CustomComboVO;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgService;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdPayFgService")
public class ProdPayFgServiceImpl implements ProdPayFgService {
    private final ProdPayFgMapper prodPayFgMapper;
    private final MessageService messageService;

    @Autowired
    public ProdPayFgServiceImpl(ProdPayFgMapper prodPayFgMapper, MessageService messageService) {
    	this.prodPayFgMapper = prodPayFgMapper;
        this.messageService = messageService;
    }


    /** 상품별 매출 - 결제수단별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdPayFgList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO) {

    	prodPayFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
        if(!StringUtil.getOrBlank(prodPayFgVO.getStoreCd()).equals("")) {
        	prodPayFgVO.setArrStoreCd(prodPayFgVO.getStoreCd().split(","));
        }
    	
        // 결제수단별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        
        List<DefaultMap<String>> payCd = prodPayFgMapper.getPayColList(prodPayFgVO);

        for(int i = 0; i < payCd.size(); i++) {
        	String j = payCd.get(i).get("payCd");
        	sQuery1 +=", NVL(SUM(A.PAY" + j + "), 0 ) AS PAY" + j +  "\n";
        	sQuery2 +=", CASE WHEN D.PAY_CD = "+ j + " THEN SUM(D.PAY_AMT) ELSE NULL END AS PAY" + j +  "\n";
        }
          
        prodPayFgVO.setsQuery1(sQuery1);
        prodPayFgVO.setsQuery2(sQuery2);
        
        return prodPayFgMapper.getProdPayFgList(prodPayFgVO);
    }
    
    /** 상품별 매출 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO) {
        return prodPayFgMapper.getPayColList(prodPayFgVO);
    }
}