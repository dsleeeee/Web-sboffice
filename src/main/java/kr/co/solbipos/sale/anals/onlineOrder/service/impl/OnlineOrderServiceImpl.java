package kr.co.solbipos.sale.anals.onlineOrder.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.onlineOrder.service.OnlineOrderService;
import kr.co.solbipos.sale.anals.onlineOrder.service.OnlineOrderVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name  : OnlineOrderServiceImpl.java
 * @Description : 매출관리 > 매출분석 > 온라인주문확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("OnlineOrderService")
@Transactional
public class OnlineOrderServiceImpl implements OnlineOrderService {

    private final OnlineOrderMapper onlineOrderMapper;

    public OnlineOrderServiceImpl(OnlineOrderMapper onlineOrderMapper) {
        this.onlineOrderMapper = onlineOrderMapper;
    }

    /** 온라인주문확인 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchOnlineOrderList(OnlineOrderVO onlineOrderVO, SessionInfoVO sessionInfoVO) {
        return onlineOrderMapper.getSearchOnlineOrderList(onlineOrderVO);
    }
}
