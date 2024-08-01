package kr.co.solbipos.mobile.stock.status.periodIoStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockService;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : MobilePeriodIoStockServiceImpl.java
 * @Description : (모바일)재고현황 > 기간수불현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MobilePeriodIoStockService")
@Transactional
public class MobilePeriodIoStockServiceImpl implements MobilePeriodIoStockService {

    private final MobilePeriodIoStockMapper mobilePeriodIoStockMapper;

    @Autowired
    public MobilePeriodIoStockServiceImpl(MobilePeriodIoStockMapper mobilePeriodIoStockMapper) {
        this.mobilePeriodIoStockMapper = mobilePeriodIoStockMapper;
    }

    /** 기간수불현황 - 기간수불현황 리스트 조회 */
    @SuppressWarnings("unlikely-arg-type")
    @Override
    public List<DefaultMap<String>> getPeriodIoStockList(MobilePeriodIoStockVO mobilePeriodIostockVO, SessionInfoVO sessionInfoVO) {

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobilePeriodIostockVO.getVendrCd()).equals("")) {
            mobilePeriodIostockVO.setArrVendrCd(mobilePeriodIostockVO.getVendrCd().split(","));
        }

        mobilePeriodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePeriodIostockVO.setStoreCd(sessionInfoVO.getStoreCd());

        if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
            return mobilePeriodIoStockMapper.getPeriodIoStockList(mobilePeriodIostockVO);
        } else {
            return mobilePeriodIoStockMapper.getPeriodIoStockStoreList(mobilePeriodIostockVO);
        }
    }

    /** 기간수불현황 - 기간수불현황 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPeriodIoStockProdDtlList(MobilePeriodIoStockVO mobilePeriodIostockVO,
                                                                SessionInfoVO sessionInfoVO) {

        mobilePeriodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePeriodIostockVO.setStoreCd(sessionInfoVO.getStoreCd());

        if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
            return mobilePeriodIoStockMapper.getPeriodIoStockProdDtlList(mobilePeriodIostockVO);
        } else {
            return mobilePeriodIoStockMapper.getPeriodIoStockProdStoreDtlList(mobilePeriodIostockVO);
        }
    }

    /** 기간수불현황 - 기간수불현황 엑셀 전체다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getPeriodIoStockExcelList(MobilePeriodIoStockVO mobilePeriodIostockVO,
                                                              SessionInfoVO sessionInfoVO) {
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobilePeriodIostockVO.getVendrCd()).equals("")) {
            mobilePeriodIostockVO.setArrVendrCd(mobilePeriodIostockVO.getVendrCd().split(","));
        }

        mobilePeriodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePeriodIostockVO.setStoreCd(sessionInfoVO.getStoreCd());

        if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
            return mobilePeriodIoStockMapper.getPeriodIoStockExcelList(mobilePeriodIostockVO);
        } else {
            return mobilePeriodIoStockMapper.getPeriodIoStockStoreExcelList(mobilePeriodIostockVO);
        }
    }
}
