package kr.co.solbipos.mobile.stock.status.dailyIoStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockService;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : MobileDailyIoStockServiceImpl.java
 * @Description : (모바일)재고현황 > 일자별수불현황
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
@Service("MobileDailyIoStockService")
@Transactional
public class MobileDailyIoStockServiceImpl implements MobileDailyIoStockService {

    private final MobileDailyIoStockMapper mobileDailyIoStockMapper;

    @Autowired
    public MobileDailyIoStockServiceImpl(MobileDailyIoStockMapper mobileDailyIoStockMapper) {
        this.mobileDailyIoStockMapper = mobileDailyIoStockMapper;
    }

    /** 일수불현황 - 일수불현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDailyIoStockList(MobileDailyIoStockVO mobileDailyIoStockVO, SessionInfoVO sessionInfoVO) {

        mobileDailyIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileDailyIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());

        return mobileDailyIoStockMapper.getDailyIoStockList(mobileDailyIoStockVO);
    }

    /** 일수불현황 - 일수불현황 리스트(엑셀) 조회 */
    @Override
    public List<DefaultMap<String>> getDailyIoStockExcelList(MobileDailyIoStockVO mobileDailyIoStockVO, SessionInfoVO sessionInfoVO) {

        mobileDailyIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileDailyIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());

        return mobileDailyIoStockMapper.getDailyIoStockExcelList(mobileDailyIoStockVO);
    }
}
