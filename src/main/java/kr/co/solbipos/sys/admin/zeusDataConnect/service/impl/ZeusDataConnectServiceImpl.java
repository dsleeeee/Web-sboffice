package kr.co.solbipos.sys.admin.zeusDataConnect.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sys.admin.zeusDataConnect.service.ZeusDataConnectService;
import kr.co.solbipos.sys.admin.zeusDataConnect.service.ZeusDataConnectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ZeusDataConnectServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > 제우스데이터연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.03.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.03.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("zeusDataConnectService")
@Transactional
public class ZeusDataConnectServiceImpl implements ZeusDataConnectService {
    private final ZeusDataConnectMapper zeusDataConnectMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ZeusDataConnectServiceImpl(ZeusDataConnectMapper zeusDataConnectMapper) {
        this.zeusDataConnectMapper = zeusDataConnectMapper;
    }

    /** 제우스데이터연동 - 제우스 PKG 호출 01 */
    @Override
    public String getZeusPkg01(ZeusDataConnectVO zeusDataConnectVO, SessionInfoVO sessionInfoVO) {

        String procResult = "";

        zeusDataConnectMapper.getZeusPkg01(zeusDataConnectVO);

        procResult = zeusDataConnectVO.getResult();

        return procResult;
    }

    /** 제우스데이터연동 - 제우스 PKG 호출 02 */
    @Override
    public String getZeusPkg02(ZeusDataConnectVO zeusDataConnectVO, SessionInfoVO sessionInfoVO) {

        String procResult = "";

        zeusDataConnectMapper.getZeusPkg02(zeusDataConnectVO);

        procResult = zeusDataConnectVO.getResult();

        return procResult;
    }

    /** 제우스데이터연동 - 제우스->링크 데이터연동 */
    @Override
    public String getZeusPkg01Call(ZeusDataConnectVO zeusDataConnectVO, SessionInfoVO sessionInfoVO) {

        String procResult = "";

        zeusDataConnectMapper.getZeusPkg01Call(zeusDataConnectVO);

        procResult = zeusDataConnectVO.getResult();

        return procResult;
    }

    /** 제우스데이터연동 - 연동신청처리 */
    @Override
    public String getZeusPkg02Call(ZeusDataConnectVO zeusDataConnectVO, SessionInfoVO sessionInfoVO) {

        String procResult = "";

        zeusDataConnectMapper.getZeusPkg02Call(zeusDataConnectVO);

        procResult = zeusDataConnectVO.getResult();

        return procResult;
    }

    /** 제우스데이터연동 - 조회 */
    @Override
    public List<DefaultMap<Object>> getZeusDataConnectList(ZeusDataConnectVO zeusDataConnectVO, SessionInfoVO sessionInfoVO) {

        return zeusDataConnectMapper.getZeusDataConnectList(zeusDataConnectVO);
    }
}