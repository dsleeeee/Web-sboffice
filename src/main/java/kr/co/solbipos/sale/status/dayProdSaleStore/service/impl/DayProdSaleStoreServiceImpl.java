package kr.co.solbipos.sale.status.dayProdSaleStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.dayProdSaleStore.service.DayProdSaleStoreService;
import kr.co.solbipos.sale.status.dayProdSaleStore.service.DayProdSaleStoreVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : DayProdSaleStoreServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 일별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.01.09  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2025.01.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayProdSaleStoreService")
public class DayProdSaleStoreServiceImpl implements DayProdSaleStoreService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DayProdSaleStoreMapper dayProdSaleStoreMapper;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public DayProdSaleStoreServiceImpl(DayProdSaleStoreMapper dayProdSaleStoreMapper, PopupMapper popupMapper) {
        this.dayProdSaleStoreMapper = dayProdSaleStoreMapper;
        this.popupMapper = popupMapper;
    }

    /** 일별상품매출현황(매장별) - 조회 */
    @Override
    public List<DefaultMap<String>> getDayProdSaleStoreList(DayProdSaleStoreVO dayProdSaleStoreVO, SessionInfoVO sessionInfoVO) {

       dayProdSaleStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdSaleStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdSaleStoreVO.getStoreCds(), 3900));
            dayProdSaleStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdSaleStoreVO.getProdClassCd() != null && !"".equals(dayProdSaleStoreVO.getProdClassCd())) {
            String[] prodCdList = dayProdSaleStoreVO.getProdClassCd().split(",");
            dayProdSaleStoreVO.setArrProdClassCd(prodCdList);
        }

        // 사이드분류 array 값 세팅
        if (dayProdSaleStoreVO.getSideProdClassCd() != null && !"".equals(dayProdSaleStoreVO.getSideProdClassCd())) {
            String[] sideProdCdList = dayProdSaleStoreVO.getSideProdClassCd().split(",");
            dayProdSaleStoreVO.setArrSideProdClassCd(sideProdCdList);
        }

        return dayProdSaleStoreMapper.getDayProdSaleStoreList(dayProdSaleStoreVO);
    }

    /** 일별상품매출현황(매장별) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getDayProdSaleStoreExcelList(DayProdSaleStoreVO dayProdSaleStoreVO, SessionInfoVO sessionInfoVO) {

        dayProdSaleStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdSaleStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdSaleStoreVO.getStoreCds(), 3900));
            dayProdSaleStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdSaleStoreVO.getProdClassCd() != null && !"".equals(dayProdSaleStoreVO.getProdClassCd())) {
            String[] prodCdList = dayProdSaleStoreVO.getProdClassCd().split(",");
            dayProdSaleStoreVO.setArrProdClassCd(prodCdList);
        }

        // 사이드분류 array 값 세팅
        if (dayProdSaleStoreVO.getSideProdClassCd() != null && !"".equals(dayProdSaleStoreVO.getSideProdClassCd())) {
            String[] sideProdCdList = dayProdSaleStoreVO.getSideProdClassCd().split(",");
            dayProdSaleStoreVO.setArrSideProdClassCd(sideProdCdList);
        }

        return dayProdSaleStoreMapper.getDayProdSaleStoreExcelList(dayProdSaleStoreVO);
    }

    /** 일별상품매출현황(매장별) - 상세 팝업 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayProdSaleStoreDtl(DayProdSaleStoreVO dayProdSaleStoreVO, SessionInfoVO sessionInfoVO) {
        return dayProdSaleStoreMapper.getDayProdSaleStoreDtl(dayProdSaleStoreVO);
    }
}
