package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AlimtalkSendTypeMapper.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송유형
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface AlimtalkSendTypeMapper {

    /** 알림톡 키값 리스트 조회 */
    List<DefaultMap<String>> getAlimtalkKeyColList(SessionInfoVO sessionInfoVO);

    /** 알림톡 템플릿 치환값 리스트 조회 */
    List<DefaultMap<String>> getAlimtalkTemplateChangeKeyColList(SessionInfoVO sessionInfoVO);

    /** 알림톡 전송유형 - 계정정보 체크 조회 */
    DefaultMap<String> getAlimtalkIdRegisterChk(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 전송유형 - 전송유형 조회 */
    List<DefaultMap<Object>> getAlimtalkSendTypeList(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 전송유형 - 전송유형 상세 조회 */
    List<DefaultMap<Object>> getAlimtalkSendTypeDetailList(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 전송유형 설정에 등록된 템플릿 조회 */
    String getAlimtalkSendTypeTemplateCd(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 첫번째 템플릿 조회 */
    List<DefaultMap<String>> getTemplateCd(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 전송유형 - 전송유형 상세 저장 merge */
    int getAlimtalkSendTypeDetailSaveMerge(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 전송유형 - 템플릿 상세 조회 */
    DefaultMap<String> getAlimtalkSendTypeDetailTemplate(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 전송유형 - 템플릿 상세 저장 merge */
    int getAlimtalkSendTypeDetailTemplateSaveMerge(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 전송유형 - 템플릿 목록 조회 */
    List<DefaultMap<Object>> getAlimtalkSendTypeDetailTemplateList(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 계정등록 팝업 - 계정정보 체크 조회 */
    DefaultMap<String> getAlimtalkIdRegisterAllChk(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 계정등록 팝업 - 그룹-계정정보 체크 조회 */
    DefaultMap<String> getAlimtalkRegisterGroupChk(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 계정등록 팝업 - 사업자 카테고리 조회 */
    List<DefaultMap<Object>> getCategoryCodeComboList(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 계정등록 팝업 - 알림톡 계정정보 저장 insert */
    int getAlimtalkSenderSaveInsert(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 계정등록 팝업 - 알림톡 계정정보 저장 update */
    int getAlimtalkSenderSaveUpdate(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 알림톡 계정등록 팝업 - 알림톡 그룹-계정정보 저장 insert */
    int getAlimtalkSenderGroupSaveInsert(AlimtalkSendTypeVO alimtalkSendTypeVO);

    /** 잔여금액 조회 */
    DefaultMap<Object> getAlimtalkSmsAmtList(AlimtalkSendTypeVO alimtalkSendTypeVO);
}