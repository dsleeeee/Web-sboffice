package kr.co.solbipos.sys.link.omsLinkSample.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.omsLinkSample.service.ApiLinkVO;
import kr.co.solbipos.sys.link.omsLinkSample.service.OmsLinkSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @author 링크 개발실 개발1팀 이다솜
 * @version 1.0
 * <p>
 * Copyright (C) by LYNK CORP. All right reserved.
 * @Class Name : OmsLinkSampleServiceImpl.java
 * @Description : 시스템관리 > 연동 > OMS연동샘플
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  이다솜      최초생성
 * @since 2025.09.11
 */


@Service("omsLinkSampleService")
@Transactional
public class OmsLinkSampleServiceImpl implements OmsLinkSampleService {

    private final OmsLinkSampleMapper omsLinkSampleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public OmsLinkSampleServiceImpl(OmsLinkSampleMapper omsLinkSampleMapper) {
        this.omsLinkSampleMapper = omsLinkSampleMapper;
    }

    /** OMS연동샘플 API 호출 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getOmsLinkSampleReqList(ApiLinkVO apiLinkVO) {
        return omsLinkSampleMapper.getOmsLinkSampleReqList(apiLinkVO);
    }

    /** API 호출 로그 저장 */
    @Override
    public int saveApiLog (ApiLinkVO apiLinkVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        apiLinkVO.setRegDt(currentDt);
        apiLinkVO.setRegId(sessionInfoVO.getUserId());

        // 로그 저장
        omsLinkSampleMapper.saveApiLog(apiLinkVO);

        // 로그 저장 후 받은 seq 키값
        int seq = apiLinkVO.getSeq();

        return seq;
    }
}
