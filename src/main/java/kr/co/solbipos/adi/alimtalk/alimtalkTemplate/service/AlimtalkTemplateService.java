package kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @Class Name : AlimtalkTemplateService.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 템플릿관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.06.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AlimtalkTemplateService {

    /** 알림톡 템플릿관리 - 조회 */
    List<DefaultMap<Object>> getAlimtalkTemplateList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 전송유형 콤보박스 조회 */
    List<DefaultMap<Object>> getSendTypeCdComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 전송유형상세 콤보박스 조회 */
    List<DefaultMap<Object>> getSendTypeDtlCdComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 계정 콤보박스 조회 */
    List<DefaultMap<Object>> getGroupKeyComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 템플릿 카테고리(대분류) 콤보박스 조회 */
    List<DefaultMap<Object>> getTemplateClsCdLComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 템플릿 카테고리(중분류) 콤보박스 조회 */
    List<DefaultMap<Object>> getTemplateClsCdMComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 템플릿등록 팝업 - #{변수} 조회 */
    List<DefaultMap<Object>> getAlimtalkTemplateParamsList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 전체 #{변수} 컬럼 리스트 조회 */
    List<DefaultMap<String>> getAlimtalkTemplateParamsColList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 템플릿등록 팝업 - 저장 */
//    int getAlimtalkTemplateRegisterSave(AlimtalkTemplateVO[] alimtalkTemplateVOs, SessionInfoVO sessionInfoVO);
    String getAlimtalkTemplateRegisterSave(AlimtalkTemplateVO[] alimtalkTemplateVOs, SessionInfoVO sessionInfoVO);

    /** 알림톡 템플릿등록 팝업 - 이미지 저장 */
//    boolean getAlimtalkTemplateImageFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo);
    String getAlimtalkTemplateImageFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo);

    /** 알림톡 템플릿상세 팝업 - 조회 */
    DefaultMap<String> getAlimtalkTemplateDtlList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 템플릿상세 팝업 - 버튼 조회 */
    List<DefaultMap<Object>> getAlimtalkTemplateDtlButtonsList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO);
}