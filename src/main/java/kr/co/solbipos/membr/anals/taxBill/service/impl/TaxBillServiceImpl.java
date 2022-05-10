package kr.co.solbipos.membr.anals.taxBill.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillService;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TaxBillServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 세금계산서 발행 목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.13  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("taxBillService")
@Transactional
public class TaxBillServiceImpl implements TaxBillService {

    private final TaxBillMapper taxBillMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public TaxBillServiceImpl( TaxBillMapper taxBillMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.taxBillMapper = taxBillMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 세금계산서 발행 요청 목록 */
    @Override
    public List<DefaultMap<Object>> getTaxBillRequestList(TaxBillVO taxBillVO, SessionInfoVO sessionInfoVO) {

        taxBillVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        taxBillVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        taxBillVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        taxBillVO.setEmpNo(sessionInfoVO.getEmpNo());

        return taxBillMapper.getTaxBillRequestList(taxBillVO);
    }

}
