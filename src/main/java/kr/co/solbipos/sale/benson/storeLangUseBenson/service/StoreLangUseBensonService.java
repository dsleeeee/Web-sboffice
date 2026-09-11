package kr.co.solbipos.sale.benson.storeLangUseBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreLangUseBensonService.java
 * @Description : 벤슨 > 매장분석 > 다국어사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreLangUseBensonService {

    /** 다국어사용현황 조회 */
    List<DefaultMap<String>> getStoreLangUseBensonList(StoreLangUseBensonVO storeLangUseBensonVO, SessionInfoVO sessionInfoVO);
}
