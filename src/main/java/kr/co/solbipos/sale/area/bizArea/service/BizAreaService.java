package kr.co.solbipos.sale.area.bizArea.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.area.bizArea.service.BizAreaVO;

import java.util.List;

/**
 * @Class Name : BizAreaService.java
 * @Description : 맘스터치 > 매출분석 > 상권별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface BizAreaService {

    /** 조회 */
    List<DefaultMap<Object>> getBizAreaList(BizAreaVO bizAreaVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getBizAreaExcelList(BizAreaVO bizAreaVO, SessionInfoVO sessionInfoVO);

}