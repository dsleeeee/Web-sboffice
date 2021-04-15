package kr.co.solbipos.base.price.salePriceManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SalePriceManageService.java
 * @Description : 기초관리 > 가격관리 > 판매가관리(매장용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.04.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SalePriceManageService {

    /** 판매가관리 조회 */
    List<DefaultMap<Object>> getSalePriceManageList(SalePriceManageVO salePriceManageVO, SessionInfoVO sessionInfoVO);

    /** 판매가관리 저장 */
    int getSalePriceManageSave(SalePriceManageVO[] salePriceManageVOs, SessionInfoVO sessionInfoVO);
}