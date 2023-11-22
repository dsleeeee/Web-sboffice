package kr.co.solbipos.base.multilingual.captionMsg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.multilingual.captionMsg.service.CaptionMsgVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CaptionMsgMapper.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(기능키/메시지)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface CaptionMsgMapper {

    /** 화면구분 콤보박스 조회 */
    List<DefaultMap<String>> getCaptionMsgGrpComboList(CaptionMsgVO captionMsgVO);

    /** 화면구분 선택에 따른 기능키/메시지 탭 리스트 조회 */
    List<DefaultMap<String>> getCaptionMsgList(CaptionMsgVO captionMsgVO);

    /** 기능키 or 메시지코드 중복체크 */
    String chkCaptionMsgId(CaptionMsgVO captionMsgVO);

    /** 기능키/메시지 저장 */
    int saveCaptionMsg(CaptionMsgVO captionMsgVO);

    /** 기능키/메시지 삭제 */
    int deleteCaptionMsg(CaptionMsgVO captionMsgVO);

    /** 화면구분등록 탭 리스트 조회 */
    List<DefaultMap<String>> getCaptionMsgGrpList(CaptionMsgVO captionMsgVO);

    /** 화면구분등록 탭 상세 조회 */
    DefaultMap<String> getCaptionMsgGrpDtl(CaptionMsgVO captionMsgVO);

    /** 화면구분코드 채번 */
    String getCaptionImgCd(CaptionMsgVO captionMsgVO);

    /** 화면구분 신규 등록 */
    int saveCaptionMsgGrp(CaptionMsgVO captionMsgVO);

    /** 화면구분 수정 */
    int updateCaptionMsgGrp(CaptionMsgVO captionMsgVO);

    /** 화면구분 삭제 */
    int deleteCaptionMsgGrp(CaptionMsgVO captionMsgVO);

}
