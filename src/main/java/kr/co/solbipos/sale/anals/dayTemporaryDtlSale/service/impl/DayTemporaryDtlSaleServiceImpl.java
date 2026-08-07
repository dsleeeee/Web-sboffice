package kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service.DayTemporaryDtlSaleService;
import kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service.DayTemporaryDtlSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name  : DayTemporaryDtlSaleServiceImpl.java
 * @Description : 맘스터치 > 매출분석2 > 일별 가승인 상세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("DayTemporaryDtlSaleService")
@Transactional
public class DayTemporaryDtlSaleServiceImpl implements DayTemporaryDtlSaleService {

    private final DayTemporaryDtlSaleMapper dayTemporaryDtlSaleMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public DayTemporaryDtlSaleServiceImpl(DayTemporaryDtlSaleMapper dayTemporaryDtlSaleMapper, PopupMapper popupMapper) {
        this.dayTemporaryDtlSaleMapper = dayTemporaryDtlSaleMapper;
        this.popupMapper = popupMapper;
    }

    /** 맘스터치 전용 - 매장별가승인매출 코드 조회 */
    @Override
    public List<DefaultMap<Object>> getTemporaryPayColList() {
        return dayTemporaryDtlSaleMapper.getTemporaryPayColList();
    }

    /** 일별 가승인 상세매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDayTemporaryDtlSaleList(DayTemporaryDtlSaleVO dayTemporaryDtlSaleVO, SessionInfoVO sessionInfoVO) {

        dayTemporaryDtlSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayTemporaryDtlSaleVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(dayTemporaryDtlSaleVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayTemporaryDtlSaleVO.getStoreCds(), 3900));
            dayTemporaryDtlSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (dayTemporaryDtlSaleVO.getStoreHqBrandCd() == "" || dayTemporaryDtlSaleVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (dayTemporaryDtlSaleVO.getUserBrands() != null && !"".equals(dayTemporaryDtlSaleVO.getUserBrands())) {
                    String[] userBrandList = dayTemporaryDtlSaleVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        dayTemporaryDtlSaleVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        // 매장별가승인매출 코드 목록(피벗 컬럼 생성용) 세팅
        dayTemporaryDtlSaleVO.setTemporaryPayColList(dayTemporaryDtlSaleMapper.getTemporaryPayColList());

        return dayTemporaryDtlSaleMapper.getDayTemporaryDtlSaleList(dayTemporaryDtlSaleVO);
    }
}
