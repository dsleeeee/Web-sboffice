package kr.co.solbipos.sale.status.side.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.side.service.SideService;
import kr.co.solbipos.sale.status.side.service.SideVO;
import kr.co.solbipos.sale.status.weight.weight.service.WeightService;
import kr.co.solbipos.sale.status.weight.weight.service.WeightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : SideServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 상품별(사이드)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.01.21  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.01.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sideService")
public class SideServiceImpl implements SideService {
    private final SideMapper sideMapper;
    private final MessageService messageService;

    @Autowired
    public SideServiceImpl(SideMapper sideMapper, MessageService messageService) {
        this.sideMapper = sideMapper;
        this.messageService = messageService;
    }

    /** 상품분류별 조회 */
    @Override
    public List<DefaultMap<String>> sideProdClass(SideVO sideVO, SessionInfoVO sessionInfoVO) {

        sideVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(sideVO.getStoreCd()).equals("")) {
            sideVO.setArrStoreCd(sideVO.getStoreCd().split(","));
        }
        return sideMapper.sideProdClass(sideVO);
    }

    @Override
    public List<DefaultMap<String>> sideProdClassExcel(SideVO sideVO, SessionInfoVO sessionInfoVO) {

        sideVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(sideVO.getStoreCd()).equals("")) {
            sideVO.setArrStoreCd(sideVO.getStoreCd().split(","));
        }
        return sideMapper.sideProdClassExcel(sideVO);
    }

}