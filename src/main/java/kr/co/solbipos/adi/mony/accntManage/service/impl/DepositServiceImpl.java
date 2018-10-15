package kr.co.solbipos.adi.mony.accntManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.mony.accntManage.service.DepositService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;


/**
 * @Class Name : DepositServiceImpl.java
 * @Description : 부가서비스 > 금전처리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.12  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class DepositServiceImpl implements DepositService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DepositMapper mapper;

    /** 계정 조회 */
    @Override
    public List<DefaultMap<String>> getDepositAccntList(AccntVO accntVO, SessionInfoVO sessionInfoVO) {

        accntVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        return mapper.getDepositAccntList(accntVO);
    }
}
