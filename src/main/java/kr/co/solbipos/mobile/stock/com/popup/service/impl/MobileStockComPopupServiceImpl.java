package kr.co.solbipos.mobile.stock.com.popup.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.com.popup.service.MobileStockComPopupService;
import kr.co.solbipos.mobile.stock.com.popup.service.MobileStockComPopupVO;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockVO;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : MobileStockComPopupServiceImpl.java
 * @Description : (모바일)재고현황공통팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.30  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MobileStockComPopupService")
public class MobileStockComPopupServiceImpl implements MobileStockComPopupService {
    
    private final MobileStockComPopupMapper mobileStockComPopupMapper;

    @Autowired
    public MobileStockComPopupServiceImpl(MobileStockComPopupMapper mobileStockComPopupMapper) {
        this.mobileStockComPopupMapper = mobileStockComPopupMapper;
    }

    /** 일자별수불현황 - 일자별수불현황 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDailyIoStockInfoList(MobileDailyIoStockVO mobileDailyIoStockVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

        System.out.println("일수불 mobileDailyIoStockVO.getIoOccrFg:"+ mobileDailyIoStockVO.getIoOccrFg());
        mobileDailyIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileDailyIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());
        if("S".equals(mobileDailyIoStockVO.getOrgnFg())) {
            if(mobileDailyIoStockVO.getIoOccrFg().equals("storeInQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListStoreInQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("storeOutQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListStoreOutQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("purchsInQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListPurchsInQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("purchsOutQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListPurchsOutQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("moveInQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListMoveInQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("moveOutQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListMoveOutQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("disuseQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListDisuseQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("adjQty")){
                list = mobileStockComPopupMapper.getDailyIoStockStoreInfoListAdjQty(mobileDailyIoStockVO);
            }
        }else {
            if(mobileDailyIoStockVO.getIoOccrFg().equals("vendrInQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListVendrInQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("vendrOutQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListVendrOutQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("hqOutQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListHqOutQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("hqInQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListHqInQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("storeMoveInQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListStoreMoveInQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("storeMoveOutQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListStoreMoveOutQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("disuseQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListDisuseQty(mobileDailyIoStockVO);
            } else if(mobileDailyIoStockVO.getIoOccrFg().equals("adjQty")){
                list = mobileStockComPopupMapper.getDailyIoStockHqInfoListAdjQty(mobileDailyIoStockVO);
            }
        }
        return list;
    }

    /** 각 상품코드별 팝업 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCmmProdCodeDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO, SessionInfoVO sessionInfoVO) {
        mobilePeriodIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if( sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ") && mobilePeriodIoStockVO.getStoreCd().length() == 0) {
            return mobileStockComPopupMapper.getCmmProdCodeHqDtlList(mobilePeriodIoStockVO);
        } else {
            return mobileStockComPopupMapper.getCmmProdCodeStoreDtlList(mobilePeriodIoStockVO);
        }
    }

    @Override
    public List<DefaultMap<String>> getCmmQtyDtlList(MobilePeriodIoStockVO mobilePeriodIostockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

        System.out.println("공통 mobilePeriodIostockVO.getColCode:"+mobilePeriodIostockVO.getColCode());
        mobilePeriodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(mobilePeriodIostockVO.getColCode().equals("vendrInQty")){
            // 본사입고
            list = mobileStockComPopupMapper.getVendrInQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("vendrOutQty")){
            // 업체반출
            list = mobileStockComPopupMapper.getVendrOutQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("hqOutQty")){
            // 본사출고
            list = mobileStockComPopupMapper.getHqOutQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("hqInQty")){
            // 본사반입
            list = mobileStockComPopupMapper.getHqInQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("storeMoveInQty") || mobilePeriodIostockVO.getColCode().equals("moveInQty")){
            // 매장이입
            list = mobileStockComPopupMapper.getMoveInQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("storeMoveOutQty") || mobilePeriodIostockVO.getColCode().equals("moveOutQty")){
            // 매장이출
            list = mobileStockComPopupMapper.getMoveOutQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("disuseQty")){
            // 재고폐기
            list = mobileStockComPopupMapper.getDisuseQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("adjQty")){
            // 재고조정
            list = mobileStockComPopupMapper.getAdjQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("storeInQty")){
            // 매장입고
            list = mobileStockComPopupMapper.getStoreInQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("storeOutQty")){
            // 매장반품
            list = mobileStockComPopupMapper.getStoreOutQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("purchsInQty")){
            // 사입입고
            list = mobileStockComPopupMapper.getPurchsInQtyDtlList(mobilePeriodIostockVO);
        } else  if(mobilePeriodIostockVO.getColCode().equals("purchsOutQty")){
            // 사입반품
            list = mobileStockComPopupMapper.getPurchsOutQtyDtlList(mobilePeriodIostockVO);
        } else if(mobilePeriodIostockVO.getColCode().equals("storeSaleQty")){
            // 매장판매
            list = mobileStockComPopupMapper.getStoreSaleQtyDtlList(mobilePeriodIostockVO);
        }
        return list;
    }

    /** 거래처 선택모듈 리스트 조회  */
    @Override
    public List<DefaultMap<String>> getVendrList(MobileStockComPopupVO mobileStockComPopupVO, SessionInfoVO sessionInfoVO) {
        mobileStockComPopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileStockComPopupVO.setStoreCd(sessionInfoVO.getStoreCd());
        mobileStockComPopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        return mobileStockComPopupMapper.getVendrList(mobileStockComPopupVO);
    }
}
