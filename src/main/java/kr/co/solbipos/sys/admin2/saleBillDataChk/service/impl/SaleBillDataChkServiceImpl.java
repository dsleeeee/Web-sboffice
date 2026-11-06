package kr.co.solbipos.sys.admin2.saleBillDataChk.service.impl;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin2.saleBillDataChk.service.SaleBillDataChkService;
import kr.co.solbipos.sys.admin2.saleBillDataChk.service.SaleBillDataChkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class Name : SaleBillDataChkServiceImpl.java
 * @Description : 시스템관리 > 관리자기능2 > 매출자료검증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.11.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("saleBillDataChkService")
@Transactional
public class SaleBillDataChkServiceImpl implements SaleBillDataChkService {
    private final SaleBillDataChkMapper saleBillDataChkMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleBillDataChkServiceImpl(SaleBillDataChkMapper saleBillDataChkMapper) {
        this.saleBillDataChkMapper = saleBillDataChkMapper;
    }

    /** 매출자료검증 - 영수증 1건 정합성 체크 */
    @Override
    public SaleBillDataChkVO getSaleBillDataChk(SaleBillDataChkVO saleBillDataChkVO, SessionInfoVO sessionInfoVO) {

        // 프로시저 OUT 파라미터(결과코드, 결과메시지)가 VO 에 세팅됨
        saleBillDataChkMapper.getSaleBillDataChk(saleBillDataChkVO);

        return saleBillDataChkVO;
    }
}
