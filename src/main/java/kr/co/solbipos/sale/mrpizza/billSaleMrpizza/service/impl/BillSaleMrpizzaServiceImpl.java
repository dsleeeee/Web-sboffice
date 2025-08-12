package kr.co.solbipos.sale.mrpizza.billSaleMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.mrpizza.billSaleMrpizza.service.BillSaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.billSaleMrpizza.service.BillSaleMrpizzaVO;
import kr.co.solbipos.sale.mrpizza.cardCoSaleMrpizza.service.CardCoSaleMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : BillSaleMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 영수별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("billSaleMrpizzaService")
@Transactional
public class BillSaleMrpizzaServiceImpl implements BillSaleMrpizzaService {

    private final BillSaleMrpizzaMapper billSaleMrpizzaMapper;
    private final PopupMapper popupMapper;

    public BillSaleMrpizzaServiceImpl(BillSaleMrpizzaMapper billSaleMrpizzaMapper, PopupMapper popupMapper) {
        this.billSaleMrpizzaMapper = billSaleMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /** 영수별매출 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getBillSaleMrpizzaList(BillSaleMrpizzaVO billSaleMrpizzaVO, SessionInfoVO sessionInfoVO) {

        billSaleMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(billSaleMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(billSaleMrpizzaVO.getStoreCds(), 3900));
            billSaleMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return billSaleMrpizzaMapper.getBillSaleMrpizzaList(billSaleMrpizzaVO);
    }
}
