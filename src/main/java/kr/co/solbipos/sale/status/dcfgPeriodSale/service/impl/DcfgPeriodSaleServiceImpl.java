package kr.co.solbipos.sale.status.dcfgPeriodSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.dcfgPeriodSale.service.DcfgPeriodSaleService;
import kr.co.solbipos.sale.status.dcfgPeriodSale.service.DcfgPeriodSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DcfgPeriodSaleServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.14  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.06.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dcfgPeriodSaleService")
@Transactional
public class DcfgPeriodSaleServiceImpl implements DcfgPeriodSaleService {

    private final DcfgPeriodSaleMapper dcfgPeriodSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DcfgPeriodSaleServiceImpl(DcfgPeriodSaleMapper dcfgPeriodSaleMapper) {
        this.dcfgPeriodSaleMapper = dcfgPeriodSaleMapper;
    }

    /**
     * 할인구분기간상세 리스트 조회
     * @param dcfgPeriodSaleVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getDcfgPeriodSaleList(DcfgPeriodSaleVO dcfgPeriodSaleVO, SessionInfoVO sessionInfoVO) {

        dcfgPeriodSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dcfgPeriodSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            if(!StringUtil.getOrBlank(dcfgPeriodSaleVO.getStoreCd()).equals("")) {
                dcfgPeriodSaleVO.setArrStoreCd(dcfgPeriodSaleVO.getStoreCd().split(","));
            }
        }

        // 할인유형
        if(!StringUtil.getOrBlank(dcfgPeriodSaleVO.getDcCd()).equals("")) {
            dcfgPeriodSaleVO.setArrDcCd(dcfgPeriodSaleVO.getDcCd().split(","));
        }

        return dcfgPeriodSaleMapper.getDcfgPeriodSaleList(dcfgPeriodSaleVO);
    }
}
