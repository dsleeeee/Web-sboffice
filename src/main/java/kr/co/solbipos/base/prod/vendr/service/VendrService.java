package kr.co.solbipos.base.prod.vendr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TouchkeyService.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  노해민      최초생성
 *
 * @author NHN한국사이버결제 KCP 노해민
 * @since 2018. 08.08
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VendrService {

    /** 거래처 목록 조회
     * @param vendrVO
     * @return List
     */
    List<DefaultMap<String>> list(VendrVO vendrVO, SessionInfoVO sessionInfoVO);

    /** 거래처 상세정보 조회
     * @param vendrVO
     * @return DefaultMap
     */
    DefaultMap<String> dtlInfo(VendrVO vendrVO, SessionInfoVO sessionInfoVO);

    /** 거래처 등록
    * @param vendrVO
    * @return int
    */
    int save(VendrVO vendrVO, SessionInfoVO sessionInfoVO);

    /** 본사 수정
    * @param vendrVO
    * @return int
    */
    int modify(VendrVO vendrVO, SessionInfoVO sessionInfoVO);

    /** 취급상품 조회
     * @param vendrVO
     * @return List
     */
    List<DefaultMap<String>> vendrProdList(VendrVO vendrVO, SessionInfoVO sessionInfoVO);

    /** 미취급상품 조회
     * @param vendrVO
     * @return List
     */
    List<DefaultMap<String>> prodList(VendrVO vendrVO, SessionInfoVO sessionInfoVO);

    /** 미취급상품 등록
    * @param vendrVO
    * @return int
    */
    int modifyProd(VendrVO[] vendrVO, SessionInfoVO sessionInfoVO);

    /** 취급상품 삭제
    * @param vendrVO
    * @return int
    */
    int deleteProd(VendrVO[] vendrVO, SessionInfoVO sessionInfoVO);

}
