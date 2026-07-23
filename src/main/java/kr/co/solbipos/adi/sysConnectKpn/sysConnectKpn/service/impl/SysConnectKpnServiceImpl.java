package kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.service.SysConnectKpnService;
import kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.service.SysConnectKpnVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.omsLinkSample.service.ApiLinkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SysConnectKpnServiceImpl.java
 * @Description : 부가서비스 > 정산 > KPN시스템접속
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sysConnectKpnService")
@Transactional
public class SysConnectKpnServiceImpl implements SysConnectKpnService {

    private final SysConnectKpnMapper sysConnectKpnMapper;

    public SysConnectKpnServiceImpl(SysConnectKpnMapper sysConnectKpnMapper) {
        this.sysConnectKpnMapper = sysConnectKpnMapper;
    }

    /** KPN API 연동정보(URL/키/캐시토큰) 조회 */
    @Override
    public DefaultMap<Object> getKpnApiInfo(SysConnectKpnVO sysConnectKpnVO, SessionInfoVO sessionInfoVO) {

        return sysConnectKpnMapper.getKpnApiInfo(sysConnectKpnVO);
    }

    /** 신규 발급받은 KPN 토큰 정보 업데이트 */
    @Override
    public int updateKpnToken(SysConnectKpnVO sysConnectKpnVO, SessionInfoVO sessionInfoVO) {

        if (StringUtil.getOrBlank(sysConnectKpnVO.getModId()).equals("")) {
            sysConnectKpnVO.setModId(sessionInfoVO.getUserId());
        }

        return sysConnectKpnMapper.updateKpnToken(sysConnectKpnVO);
    }

    /** KPN API 호출 로그 저장 (TB_CM_API_LINK) */
    @Override
    public int saveApiLog(ApiLinkVO apiLinkVO, SessionInfoVO sessionInfoVO) {

        apiLinkVO.setRegDt(currentDateTimeString());
        apiLinkVO.setRegId(sessionInfoVO.getUserId());

        return sysConnectKpnMapper.saveApiLog(apiLinkVO);
    }
}
