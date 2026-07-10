package kr.co.solbipos.sale.anals.abcBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : AbcBensonService.java
 * @Description : 벤슨 > 매출분석 > 상품 ABC분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AbcBensonService {

    /**상픔ABC분석 - 상픔ABC분석 리스트 조회   */
    List<DefaultMap<String>> getAbcBensonList(AbcBensonVO abcBensonVO, SessionInfoVO sessionInfoVO);
}
