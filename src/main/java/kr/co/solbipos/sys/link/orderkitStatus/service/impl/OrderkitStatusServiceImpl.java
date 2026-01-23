package kr.co.solbipos.sys.link.orderkitStatus.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.SaleAnalysisByProductVO;
import kr.co.solbipos.sys.link.orderkitStatus.service.OrderkitStatusService;
import kr.co.solbipos.sys.link.orderkitStatus.service.OrderkitStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin2.message.Message;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : OrderkitStatusServiceImpl.java
 * @Description : 시스템관리 > 연동 > 오더킷현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.21  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

@Service("orderkitStatusService")
@Transactional
public class OrderkitStatusServiceImpl implements OrderkitStatusService {

    private final OrderkitStatusMapper orderkitStatusMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public OrderkitStatusServiceImpl(OrderkitStatusMapper orderkitStatusMapper, MessageService messageService) {
        this.orderkitStatusMapper = orderkitStatusMapper;
        this.messageService = messageService;
    }

    /** 사용자현황 조회 */
    @Override
    public List<DefaultMap<Object>> getUserStatusList(OrderkitStatusVO orderkitStatusVO, SessionInfoVO sessionInfoVO) {
        return orderkitStatusMapper.getUserStatusList(orderkitStatusVO);
    }

    /** 접속현황 조회 */
    @Override
    public List<DefaultMap<Object>> getConnectStatusList(OrderkitStatusVO orderkitStatusVO, SessionInfoVO sessionInfoVO) {

        // 메뉴 리소스코드 조회([오더킷] - [오더킷] - [오더킷])
        String resrceCd = orderkitStatusMapper.getMenuResrceCd(orderkitStatusVO);
        orderkitStatusVO.setResrceCd(resrceCd);

        return orderkitStatusMapper.getConnectStatusList(orderkitStatusVO);
    }

    /** 연동구분(OMS, QR, NAVER)별 연동 상태 저장 */
    @Override
    public int insertAgencyStatus(OrderkitStatusVO orderkitStatusVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        orderkitStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderkitStatusVO.setStoreCd(sessionInfoVO.getStoreCd());
        orderkitStatusVO.setRegDt(currentDt);
        orderkitStatusVO.setRegId(sessionInfoVO.getUserId());
        orderkitStatusVO.setModDt(currentDt);
        orderkitStatusVO.setModId(sessionInfoVO.getUserId());

        result = orderkitStatusMapper.insertAgencyStatus(orderkitStatusVO);
        if ( result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }
}
