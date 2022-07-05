package kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.AlimtalkTemplateVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AlimtalkTemplateMapper.java
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
@Mapper
@Repository
public interface AlimtalkTemplateMapper {

    /** 알림톡 템플릿관리 - 조회 */
    List<DefaultMap<Object>> getAlimtalkTemplateList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 전송유형 콤보박스 조회 */
    List<DefaultMap<Object>> getSendTypeCdComboList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 전송유형상세 콤보박스 조회 */
    List<DefaultMap<Object>> getSendTypeDtlCdComboList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 계정 콤보박스 조회 */
    List<DefaultMap<Object>> getGroupKeyComboList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 템플릿 카테고리(대분류) 콤보박스 조회 */
    List<DefaultMap<Object>> getTemplateClsCdLComboList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 템플릿 카테고리(중분류) 콤보박스 조회 */
    List<DefaultMap<Object>> getTemplateClsCdMComboList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 템플릿등록 팝업 - #{변수} 조회 */
    List<DefaultMap<Object>> getAlimtalkTemplateParamsList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 전체 #{변수} 컬럼 리스트 조회 */
    List<DefaultMap<String>> getAlimtalkTemplateParamsColList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 템플릿등록 팝업 - 저장 insert */
    int getAlimtalkTemplateRegisterSaveInsert(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 템플릿코드(자동채번) */
    String getAlimtalkTemplateTemplateNum(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 템플릿상세 팝업 - 조회 */
    DefaultMap<String> getAlimtalkTemplateDtlList(AlimtalkTemplateVO alimtalkTemplateVO);

    /** 알림톡 템플릿상세 팝업 - 버튼 조회 */
    List<DefaultMap<Object>> getAlimtalkTemplateDtlButtonsList(AlimtalkTemplateVO alimtalkTemplateVO);
}