package kr.co.solbipos.sale.status.prepaidCardStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.prepaidCardStatus.service.PrepaidCardStatusService;
import kr.co.solbipos.sale.status.prepaidCardStatus.service.PrepaidCardStatusVO;
import kr.co.solbipos.sale.status.serviceTimeAvg.service.ServiceTimeAvgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PrepaidCardStatusServiceImpl.java
 * @Description :  맘스터치 > 매출분석2 > 선불카드현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.04.02  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.04.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("prepaidCardStatusService")
@Transactional
public class PrepaidCardStatusServiceImpl implements PrepaidCardStatusService {

    private final PrepaidCardStatusMapper prepaidCardStatusMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public PrepaidCardStatusServiceImpl(PrepaidCardStatusMapper prepaidCardStatusMapper, PopupMapper popupMapper) {
        this.prepaidCardStatusMapper = prepaidCardStatusMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 선불카드 충전 현황 - 조회
     * @param prepaidCardStatusVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getPrepaidCardChargeStatus(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO) {

        prepaidCardStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prepaidCardStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prepaidCardStatusVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prepaidCardStatusVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prepaidCardStatusVO.getStoreCds(), 3900));
            prepaidCardStatusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (prepaidCardStatusVO.getStoreHqBrandCd() == "" || prepaidCardStatusVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prepaidCardStatusVO.getUserBrands().split(",");
                prepaidCardStatusVO.setUserBrandList(userBrandList);
            }
        }

        return prepaidCardStatusMapper.getPrepaidCardChargeStatus(prepaidCardStatusVO);
    }

    /**
     * 선불카드 사용 현황 - 조회
     * @param prepaidCardStatusVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getPrepaidCardUseStatus(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO) {

        prepaidCardStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prepaidCardStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prepaidCardStatusVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prepaidCardStatusVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prepaidCardStatusVO.getStoreCds(), 3900));
            prepaidCardStatusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (prepaidCardStatusVO.getStoreHqBrandCd() == "" || prepaidCardStatusVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prepaidCardStatusVO.getUserBrands().split(",");
                prepaidCardStatusVO.setUserBrandList(userBrandList);
            }
        }

        return prepaidCardStatusMapper.getPrepaidCardUseStatus(prepaidCardStatusVO);
    }

    /**
     * 선불카드 충전 현황 - 상세 조회
     * @param prepaidCardStatusVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getPrepaidCardChargeStatusDtl(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO) {

        prepaidCardStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prepaidCardStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prepaidCardStatusVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        return prepaidCardStatusMapper.getPrepaidCardChargeStatusDtl(prepaidCardStatusVO);
    }

    /**
     * 선불카드 사용 현황 - 상세 조회
     * @param prepaidCardStatusVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getPrepaidCardUseStatusDtl(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO) {

        prepaidCardStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prepaidCardStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prepaidCardStatusVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        return prepaidCardStatusMapper.getPrepaidCardUseStatusDtl(prepaidCardStatusVO);
    }
}
