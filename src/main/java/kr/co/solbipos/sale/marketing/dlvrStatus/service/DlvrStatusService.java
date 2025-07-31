package kr.co.solbipos.sale.marketing.dlvrStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : DlvrStatusService.java
 * @Description : 미스터피자 > 마케팅조회 > 배달비
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface DlvrStatusService {

    /** 배달비 - 조회 */
    List<DefaultMap<Object>> getDlvrStatusList(DlvrStatusVO dlvrStatusVO, SessionInfoVO sessionInfoVO);
}
