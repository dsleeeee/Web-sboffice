package kr.co.solbipos.mobile.sale.status.orderStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.orderStatus.service.MobileOrderStatusService;
import kr.co.solbipos.mobile.sale.status.orderStatus.service.MobileOrderStatusVO;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.impl.OrderStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : MobileOrderStatusServiceImpl.java
 * @Description : 매출현황 > 주문현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.01  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("MobileOrderStatusService")
public class MobileOrderStatusServiceImpl implements MobileOrderStatusService {
    private final MobileOrderStatusMapper mobileOrderStatusMapper;
    private final MessageService messageService;

    @Autowired
    public MobileOrderStatusServiceImpl(MobileOrderStatusMapper mobileOrderStatusMapper, MessageService messageService) {
        this.mobileOrderStatusMapper = mobileOrderStatusMapper;
        this.messageService = messageService;
    }

    /** 주문현황 - 조회 */
    @Override
    public List<DefaultMap<String>> getMobileOrderStatusList(MobileOrderStatusVO mobileOrderStatusVO, SessionInfoVO sessionInfoVO) {
        mobileOrderStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileOrderStatusVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileOrderStatusVO.setArrStoreCd(mobileOrderStatusVO.getSrchStoreCd().split(","));
        }
        return mobileOrderStatusMapper.getMobileOrderStatusList(mobileOrderStatusVO);
    }

    /** 주문현황 상세 팝업 - 조회 */
    @Override
    public List<DefaultMap<String>> getMobileOrderStatusDtlList(MobileOrderStatusVO mobileOrderStatusVO, SessionInfoVO sessionInfoVO) {
        mobileOrderStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileOrderStatusVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileOrderStatusVO.setArrStoreCd(mobileOrderStatusVO.getSrchStoreCd().split(","));
        }
        return mobileOrderStatusMapper.getMobileOrderStatusDtlList(mobileOrderStatusVO);
    }
}
