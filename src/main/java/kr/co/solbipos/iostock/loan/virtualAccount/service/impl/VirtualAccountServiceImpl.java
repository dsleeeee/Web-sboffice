package kr.co.solbipos.iostock.loan.virtualAccount.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.loan.virtualAccount.service.VirtualAccountService;
import kr.co.solbipos.iostock.loan.virtualAccount.service.VirtualAccountVO;
// API VO
import kr.co.solbipos.iostock.loan.virtualAccount.service.ApiVirtualAccountRegisterVO;
import kr.co.solbipos.iostock.loan.virtualAccount.service.ApiVirtualAccountRegisterReceiveVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : VirtualAccountServiceImpl.java
 * @Description : 수불관리 > 주문관리 > 가상계좌내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("virtualAccountService")
@Transactional
public class VirtualAccountServiceImpl implements VirtualAccountService {
    private final VirtualAccountMapper virtualAccountMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public VirtualAccountServiceImpl(VirtualAccountMapper virtualAccountMapper) {
        this.virtualAccountMapper = virtualAccountMapper;
    }

    /** 가상계좌내역 - 조회 */
    @Override
    public List<DefaultMap<Object>> getVirtualAccountList(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO) {

        virtualAccountVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return virtualAccountMapper.getVirtualAccountList(virtualAccountVO);
    }

    /** 가상계좌 키값 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVirtualAccountKeyColList(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO) {

        return virtualAccountMapper.getVirtualAccountKeyColList(virtualAccountVO);
    }

    /** 가상계좌 API URL 조회 */
    @Override
    public String getVirtualAccountApiTargetUrl(VirtualAccountVO virtualAccountVO) {

        return virtualAccountMapper.getVirtualAccountApiTargetUrl(virtualAccountVO);
    }

    /** 가상계좌 등록순번 조회(자동채번) */
    @Override
    public String getVirtualAccountReqSeq(VirtualAccountVO virtualAccountVO) {

        return virtualAccountMapper.getVirtualAccountReqSeq(virtualAccountVO);
    }

    /** 가상계좌 입금 생성 팝업 - 가상계좌 발급 저장 Insert */
    @Override
    public int getVirtualAccountRegisterSaveInsert(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        procCnt = virtualAccountMapper.getVirtualAccountRegisterSaveInsert(virtualAccountVO);

        return procCnt;
    }

    /** 가상계좌 입금 생성 팝업 - 가상계좌 발급 저장 update */
    @Override
    public int getVirtualAccountRegisterSaveUpdate(VirtualAccountVO virtualAccountVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        procCnt = virtualAccountMapper.getVirtualAccountRegisterSaveUpdate(virtualAccountVO);

        return procCnt;
    }

}