package kr.co.solbipos.stock.com.popup.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.stock.com.popup.service.StockComPopupService;
import kr.co.solbipos.stock.com.popup.service.StockComPopupVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

@Service("StockComPopupService")
public class StockComPopupServiceImpl implements StockComPopupService {
    private final StockComPopupMapper stockComPopupMapper;
    private final MessageService messageService;

    @Autowired
    public StockComPopupServiceImpl(StockComPopupMapper stockComPopupMapper, MessageService messageService) {
    	this.stockComPopupMapper = stockComPopupMapper;
        this.messageService = messageService;
    }

	/** 일자별수불현황 - 일자별수불현황 상세 리스트 조회 */
    @Override
	public List<DefaultMap<String>> getDailyIoStockInfoList(DailyIoStockVO dailyIoStockVO, SessionInfoVO sessionInfoVO) {

		List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
		
		System.out.println("일수불 dailyIoStockVO.getIoOccrFg:"+ dailyIoStockVO.getIoOccrFg());
		dailyIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		dailyIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());
		if("S".equals(dailyIoStockVO.getOrgnFg())) {
			if(dailyIoStockVO.getIoOccrFg().equals("storeInQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListStoreInQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("storeOutQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListStoreOutQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("purchsInQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListPurchsInQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("purchsOutQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListPurchsOutQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("moveInQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListMoveInQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("moveOutQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListMoveOutQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("disuseQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListDisuseQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("adjQty")){
				list = stockComPopupMapper.getDailyIoStockStoreInfoListAdjQty(dailyIoStockVO);
			}
		}else {
			if(dailyIoStockVO.getIoOccrFg().equals("vendrInQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListVendrInQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("vendrOutQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListVendrOutQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("hqOutQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListHqOutQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("hqInQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListHqInQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("storeMoveInQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListStoreMoveInQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("storeMoveOutQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListStoreMoveOutQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("disuseQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListDisuseQty(dailyIoStockVO);
			} else if(dailyIoStockVO.getIoOccrFg().equals("adjQty")){
				list = stockComPopupMapper.getDailyIoStockHqInfoListAdjQty(dailyIoStockVO);
			}
		}
		return list;
    }

	/** 현재고현황 - 현재고현황 본사 상세 리스트 조회 */
    @Override
	public List<DefaultMap<String>> getCmmStockStatusList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO) {

    	hqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	if( sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return stockComPopupMapper.getCmmStockStatusList(hqCurrVO);
		} else {
			hqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			return stockComPopupMapper.getCmmStoreStockStatusList(hqCurrVO);
		}
    }

    @Override
	public List<DefaultMap<String>> getCmmStoreStockStatusList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO) {
		hqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return stockComPopupMapper.getCmmStoreStockStatusList(hqCurrVO);
	}

	/** 각 상품코드별 팝업 리스트 조회 */
    @Override
	public List<DefaultMap<String>> getCmmProdCodeDtlList(PeriodIostockVO periodIostockVO,
			SessionInfoVO sessionInfoVO) {
    	periodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		if( sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ") && periodIostockVO.getStoreCd().length() == 0) {
			return stockComPopupMapper.getCmmProdCodeHqDtlList(periodIostockVO);
		} else {
			return stockComPopupMapper.getCmmProdCodeStoreDtlList(periodIostockVO);
		}
	}

	/** 각 수량별 팝업 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCmmQtyDtlList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO) {

		List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
		
		System.out.println("공통 periodIostockVO.getColCode:"+periodIostockVO.getColCode());
		periodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		if(periodIostockVO.getColCode().equals("vendrInQty")){
			// 본사입고
			list = stockComPopupMapper.getVendrInQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("vendrOutQty")){
			// 업체반출
			list = stockComPopupMapper.getVendrOutQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("hqOutQty")){
			// 본사출고
			list = stockComPopupMapper.getHqOutQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("hqInQty")){
			// 본사반입
			list = stockComPopupMapper.getHqInQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("storeMoveInQty") || periodIostockVO.getColCode().equals("moveInQty")){
			// 매장이입
			list = stockComPopupMapper.getMoveInQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("storeMoveOutQty") || periodIostockVO.getColCode().equals("moveOutQty")){
			// 매장이출
			list = stockComPopupMapper.getMoveOutQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("disuseQty")){
			// 재고폐기
			list = stockComPopupMapper.getDisuseQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("adjQty")){
			// 재고조정
			list = stockComPopupMapper.getAdjQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("storeInQty")){
			// 매장입고
			list = stockComPopupMapper.getStoreInQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("storeOutQty")){
			// 매장반품
			list = stockComPopupMapper.getStoreOutQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("purchsInQty")){
			// 사입입고
			list = stockComPopupMapper.getPurchsInQtyDtlList(periodIostockVO);
		} else  if(periodIostockVO.getColCode().equals("purchsOutQty")){
			// 사입반품
			list = stockComPopupMapper.getPurchsOutQtyDtlList(periodIostockVO);
		} else if(periodIostockVO.getColCode().equals("storeSaleQty")){
			// 매장판매
			list = stockComPopupMapper.getStoreSaleQtyDtlList(periodIostockVO);
		}
		return list;
	}

	/** 실사/조정/폐기 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCmmViewDtlList(StockManageViewVO stockManageViewVO,
			SessionInfoVO sessionInfoVO) {
		return stockComPopupMapper.getCmmViewDtlList(stockManageViewVO);
	}

	/** 실사/조정/폐기  - 창고선택모듈 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectStorageList(StockManageViewVO stockManageViewVO, SessionInfoVO sessionInfoVO) {
    	stockManageViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	stockManageViewVO.setStoreCd(sessionInfoVO.getStoreCd());
    	stockManageViewVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        return stockComPopupMapper.selectStorageList(stockManageViewVO);
    }
}
