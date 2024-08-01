package kr.co.solbipos.mobile.stock.status.storePeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.storePeriod.service.MobileStorePeriodService;
import kr.co.solbipos.mobile.stock.status.storePeriod.service.MobileStorePeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @Class Name : MobileStorePeriodServiceImpl.java
 * @Description : (모바일)재고현황 > 매장기간수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MobileStorePeriodService")
public class MobileStorePeriodServiceImpl implements MobileStorePeriodService {

    private final MobileStorePeriodMapper mobileStorePeriodMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public MobileStorePeriodServiceImpl(MobileStorePeriodMapper mobileStorePeriodMapper, PopupMapper popupMapper) {
        this.mobileStorePeriodMapper = mobileStorePeriodMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장기간수불 - 매장기간수불 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorePeriodList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO) {
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileStorePeriodVO.getVendrCd()).equals("")) {
            mobileStorePeriodVO.setArrVendrCd(mobileStorePeriodVO.getVendrCd().split(","));
        }

        mobileStorePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileStorePeriodVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileStorePeriodVO.getStoreCd(), 3900));
            mobileStorePeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobileStorePeriodMapper.getStorePeriodList(mobileStorePeriodVO);
    }

    /** 매장기간수불 - 매장기간수불 상품코드 선택 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorePeriodDtlList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO) {
        mobileStorePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mobileStorePeriodMapper.getStorePeriodDtlList(mobileStorePeriodVO);
    }

    /** 매장기간수불 - 매장기간수불 수량 선택 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorePeriodQtyDtlList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO) {
        mobileStorePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mobileStorePeriodMapper.getStorePeriodQtyDtlList(mobileStorePeriodVO);
    }

    /** 매장기간수불 - 매장기간수불 엑셀 전체 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreperiodExcelList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO) {
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileStorePeriodVO.getVendrCd()).equals("")) {
            mobileStorePeriodVO.setArrVendrCd(mobileStorePeriodVO.getVendrCd().split(","));
        }

        mobileStorePeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileStorePeriodVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileStorePeriodVO.getStoreCd(), 3900));
            mobileStorePeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }


        return mobileStorePeriodMapper.getStoreperiodExcelList(mobileStorePeriodVO);
    }
}
