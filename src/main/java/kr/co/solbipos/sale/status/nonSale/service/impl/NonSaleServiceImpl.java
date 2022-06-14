package kr.co.solbipos.sale.status.nonSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.nonSale.service.NonSaleService;
import kr.co.solbipos.sale.status.nonSale.service.NonSaleVO;
import kr.co.solbipos.sale.status.offAdd.service.impl.OffAddMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : NonSaleServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 보증금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.05.16  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.05.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("nonSaleService")
public class NonSaleServiceImpl implements NonSaleService {
    private final NonSaleMapper nonSaleMapper;
    private final MessageService messageService;

    @Autowired
    public NonSaleServiceImpl(OffAddMapper offAddMapper, NonSaleMapper nonSaleMapper, MessageService messageService) {
        this.nonSaleMapper = nonSaleMapper;
        this.messageService = messageService;
    }

    /** 일별 조회 */
    @Override
    public List<DefaultMap<String>> getNonSaleDayList(NonSaleVO nonSaleVO, SessionInfoVO sessionInfoVO) {

        nonSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        nonSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        nonSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(nonSaleVO.getStoreCd()).equals("")) {
            nonSaleVO.setArrStoreCd(nonSaleVO.getStoreCd().split(","));
        }

        return nonSaleMapper.getNonSaleDayList(nonSaleVO);
    }

    @Override
    public List<DefaultMap<String>> getNonSaleDayExcelList(NonSaleVO nonSaleVO, SessionInfoVO sessionInfoVO) {

        nonSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        nonSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        nonSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(nonSaleVO.getStoreCd()).equals("")) {
            nonSaleVO.setArrStoreCd(nonSaleVO.getStoreCd().split(","));
        }

        return nonSaleMapper.getNonSaleDayExcelList(nonSaleVO);
    }

    @Override
    public List<DefaultMap<String>> getCupRefundList(NonSaleVO nonSaleVO, SessionInfoVO sessionInfoVO) {

        nonSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        nonSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        nonSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(nonSaleVO.getStoreCd()).equals("")) {
            nonSaleVO.setArrStoreCd(nonSaleVO.getStoreCd().split(","));
        }
        return nonSaleMapper.getCupRefundList(nonSaleVO);
    }

    @Override
    public List<DefaultMap<String>> getCupRefundExcelList(NonSaleVO nonSaleVO, SessionInfoVO sessionInfoVO) {

        nonSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        nonSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        nonSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(nonSaleVO.getStoreCd()).equals("")) {
            nonSaleVO.setArrStoreCd(nonSaleVO.getStoreCd().split(","));
        }
        return nonSaleMapper.getCupRefundExcelList(nonSaleVO);
    }

}