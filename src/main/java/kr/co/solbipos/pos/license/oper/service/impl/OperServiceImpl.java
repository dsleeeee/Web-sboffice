package kr.co.solbipos.pos.license.oper.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.license.oper.service.OperService;
import kr.co.solbipos.pos.license.oper.service.OperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : OperServiceImpl.java
 * @Description : 포스관리 > 라이선스관리 > 운영현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.10.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */


@Service("operService")
@Transactional
public class OperServiceImpl implements OperService {
    private final OperMapper operMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public OperServiceImpl(OperMapper operMapper) {
        this.operMapper = operMapper;
    }

    /** 매출매장현황탭 - 러닝매장현황조회 */
    @Override
    public List<DefaultMap<Object>> getRunSaleStoreList(OperVO operVO, SessionInfoVO sessionInfoVO) {

        return operMapper.getRunSaleStoreList(operVO);
    }

    /** 매출매장현황탭 - 매출매장현황조회 */
    @Override
    public List<DefaultMap<Object>> getSaleStoreList(OperVO operVO, SessionInfoVO sessionInfoVO) {

        return operMapper.getSaleStoreList(operVO);
    }

    /** 대리점인증현황탭 - 대리점인증현황조회*/
    @Override
    public List<DefaultMap<Object>> getAgencyAuthList(OperVO operVO, SessionInfoVO sessionInfoVO) {

        return operMapper.getAgencyAuthList(operVO);
    }

}

