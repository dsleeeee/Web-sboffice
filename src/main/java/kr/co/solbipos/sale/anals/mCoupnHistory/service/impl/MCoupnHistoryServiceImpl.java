package kr.co.solbipos.sale.anals.mCoupnHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.mCoupnHistory.service.MCoupnHistoryService;
import kr.co.solbipos.sale.anals.mCoupnHistory.service.MCoupnHistoryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MCoupnHistoryServiceImpl.java
 * @Description :  매출관리 > 매출분석 > 모바일쿠폰이력조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.24  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.24
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("mCoupnHistoryService")
@Transactional
public class MCoupnHistoryServiceImpl implements MCoupnHistoryService {

    private final MCoupnHistoryMapper mCoupnHistoryMapper;
    private final PopupMapper popupMapper;

    public MCoupnHistoryServiceImpl(MCoupnHistoryMapper mCoupnHistoryMapper, PopupMapper popupMapper) {
        this.mCoupnHistoryMapper = mCoupnHistoryMapper;
        this.popupMapper = popupMapper;
    }

    /** 모바일쿠폰이력조회 */
    @Override
    public List<DefaultMap<Object>> getMCoupnHistory(MCoupnHistoryVO mCoupnHistoryVO, SessionInfoVO sessionInfoVO) {

        mCoupnHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(mCoupnHistoryVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mCoupnHistoryVO.getStoreCd(), 3900));
            mCoupnHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mCoupnHistoryMapper.getMCoupnHistory(mCoupnHistoryVO);
    }
}
