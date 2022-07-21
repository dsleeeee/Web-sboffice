package kr.co.solbipos.sale.anals.store.rank.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankService;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("StoreRankService")
public class StoreRankServiceImpl implements StoreRankService {
    private final StoreRankMapper storeRankMapper;
    private final MessageService messageService;

    @Autowired
    public StoreRankServiceImpl(StoreRankMapper storeRankMapper, MessageService messageService) {
    	this.storeRankMapper = storeRankMapper;
        this.messageService = messageService;
    }

    /** 매장순위 - 매장순위 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getStoreRankList(StoreRankVO storeRankVO, SessionInfoVO sessionInfoVO) {

        storeRankVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeRankVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeRankVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(storeRankVO.getStoreCd()).equals("")) {
        	storeRankVO.setArrStoreCd(storeRankVO.getStoreCd().split(","));
        }
    	
        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        
        if(storeRankVO.getChkPay().equals("Y")) {
            List<DefaultMap<String>> payCd = storeRankMapper.getPayColList(storeRankVO);
            	sQuery1 +=", N.PAY0" + "\n";
            	sQuery2 +=", NVL(SUM(TSDP.TOT_PAY_AMT),0) AS PAY0" + "\n";
            for(int i = 0; i < payCd.size(); i++) {
            	int j = Integer.parseInt(payCd.get(i).get("payCd"));
            	sQuery1 +=", N.PAY" + j + "\n";
            	sQuery2 +=",  NVL(SUM(CASE WHEN TSDP.PAY_CD = "+ j + " THEN TSDP.TOT_PAY_AMT ELSE NULL END),0) AS PAY" + j +  "\n";
            }
        }else {
        	sQuery1 +=", N.PAY0" + "\n";
        	sQuery1 +=", N.PAY1" + "\n";
        	sQuery1 +=", N.PAY2" + "\n";
        	sQuery1 +=", N.PAY3" + "\n";
        	sQuery2 +=", NVL(SUM(TSDP.TOT_PAY_AMT),0) AS PAY0" + "\n";
        	sQuery2 +=", NVL(SUM(CASE WHEN TSDP.PAY_CD = '01' THEN TSDP.TOT_PAY_AMT ELSE NULL END),0) AS PAY1" + "\n";
        	sQuery2 +=", NVL(SUM(CASE WHEN TSDP.PAY_CD = '02' THEN TSDP.TOT_PAY_AMT ELSE NULL END),0) AS PAY2" + "\n";	
        	sQuery2 +=", NVL(SUM(CASE WHEN TSDP.PAY_CD != '01' AND TSDP.PAY_CD != '02' THEN TSDP.TOT_PAY_AMT ELSE NULL END),0) AS PAY3" + "\n";	
            
        }
               
        storeRankVO.setsQuery1(sQuery1);
        storeRankVO.setsQuery2(sQuery2);
        
        return storeRankMapper.getStoreRankList(storeRankVO);
    }
    
    /** 매장순위 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(StoreRankVO storeRankVO, SessionInfoVO sessionInfoVO) {
        return storeRankMapper.getPayColAllList(storeRankVO);
    }

    @Override
    public List<DefaultMap<String>> getStoreList(StoreRankVO storeRankVO, SessionInfoVO sessionInfoVO) {
        storeRankVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return storeRankMapper.getStoreList(storeRankVO);
    }


    @Override
    public int saveStoreIndexNo(StoreRankVO[] storeRankVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreRankVO storeRankVO : storeRankVOs) {
            storeRankVO.setRegDt(dt);
            storeRankVO.setRegId(sessionInfoVO.getUserId());
            storeRankVO.setModDt(dt);
            storeRankVO.setModId(sessionInfoVO.getUserId());

            result = storeRankMapper.saveStoreIndexNo(storeRankVO);
        }
        return result;
    }
}
