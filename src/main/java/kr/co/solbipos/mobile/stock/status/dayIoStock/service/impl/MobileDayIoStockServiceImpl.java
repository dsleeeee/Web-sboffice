package kr.co.solbipos.mobile.stock.status.dayIoStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.dayIoStock.service.MobileDayIoStockService;
import kr.co.solbipos.mobile.stock.status.dayIoStock.service.MobileDayIoStockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MobileDayIoStockServiceImpl.java
 * @Description : (모바일)재고현황 > 일수불현황
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
@Service("MobileDayIoStockService")
@Transactional
public class MobileDayIoStockServiceImpl implements MobileDayIoStockService {
    
    private final MobileDayIoStockMapper mobileDayIoStockMapper;

    @Autowired
    public MobileDayIoStockServiceImpl(MobileDayIoStockMapper mobileDayIoStockMapper) {
        this.mobileDayIoStockMapper = mobileDayIoStockMapper;
    }

    /** 일수불현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> dayIostockList(MobileDayIoStockVO mobileDayIoStockVO, SessionInfoVO sessionInfoVO) {
        mobileDayIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileDayIoStockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileDayIoStockVO.getVendrCd()).equals("")) {
            mobileDayIoStockVO.setArrVendrCd(mobileDayIoStockVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(mobileDayIoStockVO.getOrgnFg() == "H" && mobileDayIoStockVO.getOrgnFg() != null) { // 본사권한
            list = mobileDayIoStockMapper.hqDayIostockList(mobileDayIoStockVO);
        }else { // 매장권한
            mobileDayIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());
            list = mobileDayIoStockMapper.storeDayIostockList(mobileDayIoStockVO);
        }
        return list;
    }

    /** 일수불현황 리스트(엑셀) 조회 */
    @Override
    public List<DefaultMap<String>> dayIostockExcelList(MobileDayIoStockVO mobileDayIoStockVO, SessionInfoVO sessionInfoVO) {
        mobileDayIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileDayIoStockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileDayIoStockVO.getVendrCd()).equals("")) {
            mobileDayIoStockVO.setArrVendrCd(mobileDayIoStockVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(mobileDayIoStockVO.getOrgnFg() == "H" && mobileDayIoStockVO.getOrgnFg() != null) { // 본사권한
            list = mobileDayIoStockMapper.hqDayIostockExcelList(mobileDayIoStockVO);
        }else { // 매장권한
            mobileDayIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());
            list = mobileDayIoStockMapper.storeDayIostockExcelList(mobileDayIoStockVO);
        }
        return list;
    }
}
