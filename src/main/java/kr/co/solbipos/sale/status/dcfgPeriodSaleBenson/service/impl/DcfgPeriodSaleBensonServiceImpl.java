package kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service.DcfgPeriodSaleBensonService;
import kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service.DcfgPeriodSaleBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DcfgPeriodSaleBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.20
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("dcfgPeriodSaleBensonService")
@Transactional
public class DcfgPeriodSaleBensonServiceImpl implements DcfgPeriodSaleBensonService {

    private final DcfgPeriodSaleBensonMapper dcfgPeriodSaleBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DcfgPeriodSaleBensonServiceImpl(DcfgPeriodSaleBensonMapper dcfgPeriodSaleBensonMapper, PopupMapper popupMapper) {
        this.dcfgPeriodSaleBensonMapper = dcfgPeriodSaleBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 할인구분기간상세 리스트 조회
     * @param dcfgPeriodSaleBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getDcfgPeriodSaleBensonList(DcfgPeriodSaleBensonVO dcfgPeriodSaleBensonVO, SessionInfoVO sessionInfoVO) {

        dcfgPeriodSaleBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dcfgPeriodSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (!StringUtil.getOrBlank(dcfgPeriodSaleBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dcfgPeriodSaleBensonVO.getStoreCd(), 3900));
            dcfgPeriodSaleBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dcfgPeriodSaleBensonMapper.getDcfgPeriodSaleBensonList(dcfgPeriodSaleBensonVO);
    }
}
