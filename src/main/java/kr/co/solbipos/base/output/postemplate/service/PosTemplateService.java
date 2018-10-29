package kr.co.solbipos.base.output.postemplate.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PosTemplateService.java
 * @Description : 기초관리 > 출력물관리 > 포스출력물관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PosTemplateService {

    /** 출력물종류 목록 조회 */
    List<DefaultMap<String>> getPrintTypeList(PosTemplateVO posTemplateVO);

    /** 출력물코드 목록 조회 */
    List<DefaultMap<String>> getPrintCodeList(PosTemplateVO posTemplateVO);

    /** 출력물템플릿 목록 조회 */
    List<DefaultMap<String>> getPosTemplateList(PosTemplateVO posTemplateVO);

    /** 출력물템플릿 목록 저장 */
    int savePosTemplateList(PosTemplateVO[] posTemplateVOs, SessionInfoVO sessionInfoVO);

    /** 출력물템플릿 저장 */
    int savePosTemplate(PosTemplateVO posTemplateVO, SessionInfoVO sessionInfoVO);

    /** 출력물템플릿 실제출력물 적용 */
    int updatePosTemplatePrint(PosTemplateVO posTemplateVO, SessionInfoVO sessionInfoVO);

    /** 출력물템플릿 매장적용 */
    int applyToStoreTemplate(PosTemplateVO posTemplateVO, SessionInfoVO sessionInfoVO);

}
