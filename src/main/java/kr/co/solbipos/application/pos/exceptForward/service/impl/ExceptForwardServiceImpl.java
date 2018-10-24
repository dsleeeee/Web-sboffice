package kr.co.solbipos.application.pos.exceptForward.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.pos.exceptForward.service.ExceptForwardService;
import kr.co.solbipos.application.pos.exceptForward.service.ExcpForwardProductVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : ExceptForwardServiceImpl.java
 * @Description : POS 화면에서 예외출고 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.14  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("exceptForwardService")
public class ExceptForwardServiceImpl implements ExceptForwardService{

    private final ExceptForwardMapper mapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public ExceptForwardServiceImpl(ExceptForwardMapper mapper, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
    }

    /** 예외출고 대상상품 목록 조회*/
    @Override
    public List<DefaultMap<String>> getExcpForwardProduct(ExcpForwardProductVO productVO,
        SessionInfoVO sessionInfoVO) {

        productVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 목록 조회
        List<DefaultMap<String>> reultList = mapper.getExcpForwardProduct(productVO);

        return reultList;
    }
}
