package kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DcDetailMrpizzaService.java
 * @Description : 미스터피자 > 마케팅조회 > 할인세부내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.30  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface DcDetailMrpizzaService {

    /** 할인세부내역 - 전체점포 탭 리스트 조회 */
    List<DefaultMap<Object>> getDcDetailMrpizzaAllStoreList(DcDetailMrpizzaVO dcDetailMrpizzaVO, SessionInfoVO sessionInfoVO);
}
