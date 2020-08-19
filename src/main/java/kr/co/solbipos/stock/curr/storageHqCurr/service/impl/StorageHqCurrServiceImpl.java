package kr.co.solbipos.stock.curr.storageHqCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.curr.storageHqCurr.service.StorageHqCurrService;
import kr.co.solbipos.stock.curr.storageHqCurr.service.StorageHqCurrVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("storageHqCurrService")
public class StorageHqCurrServiceImpl implements StorageHqCurrService {
    private final StorageHqCurrMapper storageHqCurrMapper;
    private final MessageService messageService;

    @Autowired
    public StorageHqCurrServiceImpl(StorageHqCurrMapper storageHqCurrMapper, MessageService messageService) {
        this.storageHqCurrMapper = storageHqCurrMapper;
        this.messageService = messageService;
    }

    /** 창고별현재고현황 - 창고별현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorageHqCurrList(StorageHqCurrVO storageHqCurrVO, SessionInfoVO sessionInfoVO) {
    	storageHqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(storageHqCurrVO.getVendrCd()).equals("")) {
        	storageHqCurrVO.setArrVendrCd(storageHqCurrVO.getVendrCd().split(","));
        }
        // 창고코드 리스트
        if(!StringUtil.getOrBlank(storageHqCurrVO.getStorageCd()).equals("")) {
        	storageHqCurrVO.setArrStorageCd(storageHqCurrVO.getStorageCd().split(","));
        }
        // 동적 쿼리 생성
    	if(!StringUtil.getOrBlank(storageHqCurrVO.getStorageCd()).equals("")) {
    		// 동적 컬럼 생성을 위한 쿼리 변수.
	    	String sQuery1 = "";
	    	for(int i=0; i<storageHqCurrVO.getArrStorageCd().length; i++) {
	    		String[] ArrStorageCd = storageHqCurrVO.getArrStorageCd();
	    		System.out.println("??? :: "+storageHqCurrVO.getUnitFg());
	    		if(storageHqCurrVO.getUnitFg().endsWith("0")) {
	    			sQuery1 += ", (B.CURR_QTY_"+ ArrStorageCd[i]+ "/ A.PO_UNIT_QTY) AS CURR_QTY_"+ ArrStorageCd[i] +"\n";
	    		}else {
	    			sQuery1 += ", B.CURR_QTY_"+ ArrStorageCd[i] +"\n";
	    		}
	    	}
	    	String sQuery2 = "";
	    	for(int i=0; i<storageHqCurrVO.getArrStorageCd().length; i++) {
	    		String[] ArrStorageCd = storageHqCurrVO.getArrStorageCd();
	    		sQuery2 += ", SUM(DECODE(B.STORAGE_CD,'"+ ArrStorageCd[i] +"',B.CURR_QTY,0))   AS   CURR_QTY_"+ ArrStorageCd[i] +"\n";
	    	}
	    	storageHqCurrVO.setsQuery1(sQuery1);
	    	storageHqCurrVO.setsQuery2(sQuery2);
    	}
        List<DefaultMap<String>> list;
        if(storageHqCurrVO.getOrgnFg() == "H" && storageHqCurrVO.getOrgnFg() != null) { // 본사권한
			list = storageHqCurrMapper.getStorageHqCurrList(storageHqCurrVO);
		}else { // 매장권한
			storageHqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = storageHqCurrMapper.getStorageStorageCurrList(storageHqCurrVO);
		}
        return list;
    }
    /** 창고별현재고현황 - 창고별현재고현황 창고 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorageList(StorageHqCurrVO storageHqCurrVO, SessionInfoVO sessionInfoVO) {
    	storageHqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	storageHqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        List<DefaultMap<String>> list;
        if(storageHqCurrVO.getOrgnFg() == "H" && storageHqCurrVO.getOrgnFg() != null) { // 본사권한
			list = storageHqCurrMapper.getStorageHqList(storageHqCurrVO);
		}else { // 매장권한
			storageHqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = storageHqCurrMapper.getStorageStorageList(storageHqCurrVO);
		}
        return list;
	}


    /** 창고별현재고현황 - 창고별현재고현황(엑셀) 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorageHqCurrExcelList(StorageHqCurrVO storageHqCurrVO, SessionInfoVO sessionInfoVO) {
    	storageHqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(storageHqCurrVO.getVendrCd()).equals("")) {
        	storageHqCurrVO.setArrVendrCd(storageHqCurrVO.getVendrCd().split(","));
        }
        // 창고코드 리스트
        if(!StringUtil.getOrBlank(storageHqCurrVO.getStorageCd()).equals("")) {
        	storageHqCurrVO.setArrStorageCd(storageHqCurrVO.getStorageCd().split(","));
        }
        // 동적 쿼리 생성
    	if(!StringUtil.getOrBlank(storageHqCurrVO.getStorageCd()).equals("")) {
    		// 동적 컬럼 생성을 위한 쿼리 변수.
	    	String sQuery1 = "";
	    	for(int i=0; i<storageHqCurrVO.getArrStorageCd().length; i++) {
	    		String[] ArrStorageCd = storageHqCurrVO.getArrStorageCd();
	    		if(storageHqCurrVO.getUnitFg().endsWith("0")) {
	    			sQuery1 += ", (B.CURR_QTY_"+ ArrStorageCd[i]+ "/ A.PO_UNIT_QTY) AS CURR_QTY_"+ ArrStorageCd[i] +"\n";
	    		}else {
	    			sQuery1 += ", B.CURR_QTY_"+ ArrStorageCd[i] +"\n";
	    		}
	    	}
	    	String sQuery2 = "";
	    	for(int i=0; i<storageHqCurrVO.getArrStorageCd().length; i++) {
	    		String[] ArrStorageCd = storageHqCurrVO.getArrStorageCd();
	    		sQuery2 += ", SUM(DECODE(B.STORAGE_CD,'"+ ArrStorageCd[i] +"',B.CURR_QTY,0))   AS   CURR_QTY_"+ ArrStorageCd[i] +"\n";
	    	}
	    	storageHqCurrVO.setsQuery1(sQuery1);
	    	storageHqCurrVO.setsQuery2(sQuery2);
    	}
        List<DefaultMap<String>> list;
        if(storageHqCurrVO.getOrgnFg() == "H" && storageHqCurrVO.getOrgnFg() != null) { // 본사권한
			list = storageHqCurrMapper.getStorageHqCurrExcelList(storageHqCurrVO);
		}else { // 매장권한
			storageHqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = storageHqCurrMapper.getStorageStorageCurrExcelList(storageHqCurrVO);
		}
        return list;
    }
	
}
