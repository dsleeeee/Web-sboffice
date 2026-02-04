package kr.co.solbipos.dlvr.info.dlvrAgencyLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.dlvrAgencyLink.service.DlvrAgencyLinkService;
import kr.co.solbipos.dlvr.info.dlvrAgencyLink.service.DlvrAgencyLinkVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class Name : DlvrAgencyLinkServiceImpl.java
 * @Description : 배달관리 - 배달정보 - 배달대행사 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.14  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

@Service("dlvrAgencyLinkService")
@Transactional
public class DlvrAgencyLinkServiceImpl implements DlvrAgencyLinkService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrAgencyLinkMapper dlvrAgencyLinkMapper;

    /**
    * Constructor Injection
    */
   @Autowired
   public DlvrAgencyLinkServiceImpl(DlvrAgencyLinkMapper dlvrAgencyLinkMapper) {
       this.dlvrAgencyLinkMapper = dlvrAgencyLinkMapper;
   }

    /** 개발/운영 Api URL 조회 */
    @Override
    public DefaultMap<Object> getApiUrl(DlvrAgencyLinkVO dlvrAgencyLinkVO, SessionInfoVO sessionInfoVO) {

        dlvrAgencyLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        return dlvrAgencyLinkMapper.getApiUrl(dlvrAgencyLinkVO);
    }
}
