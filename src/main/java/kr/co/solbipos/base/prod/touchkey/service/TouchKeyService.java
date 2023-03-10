package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TouchKeyService.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 * @ 2018.09.19  노현수      메소드정리/분리
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TouchKeyService {

    /** 상품목록 조회 : 판매터치키에서 사용 */
    List<DefaultMap<String>> getProductListForTouchKey(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 터치키 스타일코드 목록 조회 */
    List<DefaultMap<String>> getTouchKeyStyleCdList();

    /** 터치키 스타일 목록 조회 */
    List<DefaultMap<String>> getTouchKeyStyleList(TouchKeyStyleVO touchKeyStyleVO, SessionInfoVO sessionInfoVO);

    /** 터치키 분류 페이지별 스타일 코드 조회 */
    String getTouchKeyPageStyleCd(SessionInfoVO sessionInfoVO, String tukeyGrpCd);

    /** 판매터치키 XML 정보 조회 */
    String getTouchKeyXml(SessionInfoVO sessionInfoVO, String tukeyGrpCd);

    /** 판매터치키 저장 상품정보 조회 */
    List<DefaultMap<String>> getTouchKeyProdInfoList(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 판매터치키 저장 */
    Result saveTouchkey(SessionInfoVO sessionInfoVO, String xml, String tukeyGrpCd, String tukeyGrpNm);

    /** 터치키미적용 상품 */
    List<DefaultMap<String>> getNoTouchKey(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 매장수정허용분류_조회 */
    List<DefaultMap<String>> getStoreModGrpList(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 매장수정허용분류_저장 */
    int saveStoreModGrp(TouchKeyVO[] touchKeyVOs, SessionInfoVO sessionInfoVO);

    /** 터치키그룹_조회 */
    List<DefaultMap<String>> getGrpList(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 매장수정허용분류_저장 */
    int saveGrpNm(TouchKeyVO[] touchKeyVOs, SessionInfoVO sessionInfoVO);

    /** 분류 삭제 전 매장수정허용분류 체크 */
    int getDeleteClassChk(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(TouchKeyVO TouchKeyVO, SessionInfoVO sessionInfoVO);

    /** 터치키 매장적용 */
    int saveTouchKeyToStore(TouchKeyVO[] TouchKeyVOs, SessionInfoVO sessionInfoVO);

    /** 판매터치키 그룹 조회 */
    List<DefaultMap<String>> getTouchKeyGrp(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 터치키그룹 복사 */
    Result copyTouchKeyGrp(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);
    
    /** 터치키 초기화 */
    int deleteTouchKey(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 판매 터치키 포스에 조회 ENVST4038 */
    List<DefaultMap<String>> getTouchKeyEnv(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO);

    /** 판매 터치키 포스에 적용 ENVST4038 */
    int saveTouchKeyEnv(TouchKeyVO[] TouchKeyVOs, SessionInfoVO sessionInfoVO);

}
