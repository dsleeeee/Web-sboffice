package kr.co.solbipos.mobile.stock.status.monthIoStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.monthIoStock.service.MobileMonthIoStockService;
import kr.co.solbipos.mobile.stock.status.monthIoStock.service.MobileMonthIoStockVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : MobileMonthIoStockServiceImpl.java
 * @Description : (모바일)재고현황 > 월수불현황
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
@Service("MobileMonthIoStockService")
@Transactional
public class MobileMonthIoStockServiceImpl implements MobileMonthIoStockService {

    private final MobileMonthIoStockMapper mobileMonthIoStockMapper;

    public MobileMonthIoStockServiceImpl(MobileMonthIoStockMapper mobileMonthIoStockMapper) {
        this.mobileMonthIoStockMapper = mobileMonthIoStockMapper;
    }


    /** 월수불현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> monthIoStockList(MobileMonthIoStockVO mobileMonthIoStockVO, SessionInfoVO sessionInfoVO) {
        mobileMonthIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileMonthIoStockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileMonthIoStockVO.getVendrCd()).equals("")) {
            mobileMonthIoStockVO.setArrVendrCd(mobileMonthIoStockVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(mobileMonthIoStockVO.getOrgnFg() == "H" && mobileMonthIoStockVO.getOrgnFg() != null) { // 본사권한
            list = mobileMonthIoStockMapper.hqMonthIoStockList(mobileMonthIoStockVO);
        }else { // 매장권한
            list = mobileMonthIoStockMapper.storeMonthIoStockList(mobileMonthIoStockVO);
        }
        return list;
    }

    @Override
    public List<DefaultMap<String>> monthIoStockExcelList(MobileMonthIoStockVO mobileMonthIoStockVO, SessionInfoVO sessionInfoVO) {
        mobileMonthIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileMonthIoStockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileMonthIoStockVO.getVendrCd()).equals("")) {
            mobileMonthIoStockVO.setArrVendrCd(mobileMonthIoStockVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(mobileMonthIoStockVO.getOrgnFg() == "H" && mobileMonthIoStockVO.getOrgnFg() != null) { // 본사권한
            list = mobileMonthIoStockMapper.hqMonthIoStockExcelList(mobileMonthIoStockVO);
        }else { // 매장권한
            list = mobileMonthIoStockMapper.storeMonthIoStockExcelList(mobileMonthIoStockVO);
        }
        return list;
    }
}
