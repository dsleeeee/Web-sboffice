package kr.co.solbipos.sys.link.naverPlaceStatus.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.naverPlaceStatus.service.NaverPlaceStatusService;
import kr.co.solbipos.sys.link.naverPlaceStatus.service.NaverPlaceStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : NaverPlaceStatusServiceImpl.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("naverPlaceStatusService")
@Transactional
public class NaverPlaceStatusServiceImpl implements NaverPlaceStatusService {

    private final NaverPlaceStatusMapper naverPlaceStatusMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public NaverPlaceStatusServiceImpl(NaverPlaceStatusMapper naverPlaceStatusMapper, MessageService messageService) {
        this.naverPlaceStatusMapper = naverPlaceStatusMapper;
        this.messageService = messageService;
    }

    /** 사용자현황 조회 */
    @Override
    public List<DefaultMap<Object>> getUserStatusList(NaverPlaceStatusVO naverPlaceStatusVO, SessionInfoVO sessionInfoVO) {
        return naverPlaceStatusMapper.getUserStatusList(naverPlaceStatusVO);
    }

    /** 접속현황 조회 */
    @Override
    public List<DefaultMap<Object>> getConnectStatusList(NaverPlaceStatusVO naverPlaceStatusVO, SessionInfoVO sessionInfoVO) {

        // 메뉴 리소스코드 조회([네이버플레이스] - [네이버플레이스] - [네이버플레이스 플러스 연동])
        String resrceCd = naverPlaceStatusMapper.getMenuResrceCd(naverPlaceStatusVO);
        naverPlaceStatusVO.setResrceCd(resrceCd);

        return naverPlaceStatusMapper.getConnectStatusList(naverPlaceStatusVO);
    }

    /** 연동정보 초기화 */
    @Override
    public int naverPlaceStatusReset(NaverPlaceStatusVO naverPlaceStatusVO, SessionInfoVO sessionInfoVO){

        int result = 0;

        // 네.아.로 ID, 네이버플레이스 연동매장 삭제
        result = naverPlaceStatusMapper.deleteNaverLink(naverPlaceStatusVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // 네이버 동의항목 삭제
        result = naverPlaceStatusMapper.deleteNaverAgreement(naverPlaceStatusVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return result;
    }
}
