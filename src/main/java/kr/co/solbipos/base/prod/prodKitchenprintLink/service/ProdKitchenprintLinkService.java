package kr.co.solbipos.base.prod.prodKitchenprintLink.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;

import java.util.List;

/**
 * @Class Name : ProdKitchenprintLinkService.java
 * @Description : 기초관리 > 상품관리 > 상품-매장주방프린터연결
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.09  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2020.02.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdKitchenprintLinkService {

    /**
     * 상품정보 조회
     */
    List<DefaultMap<String>> getProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 연결된 프린터
     */
    List<DefaultMap<String>> getLinkedList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 연결된 프린터 연결 해제
     */
    int unlinkPrter(ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 안연결된 프린터
     */
    List<DefaultMap<String>> getUnlinkList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 안연결된 프린터 연결
     */
    int linkedPrter(ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 프린터그룹 조회
     */
    List<DefaultMap<String>> getPrinterGroupList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 프린터그룹 저장
     */
    int savePrinterGroup(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO);

    /**
     * 매핑상품 조회
     */
    List<DefaultMap<String>> getProdMapping(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 상품 조회
     */
    List<DefaultMap<String>> getGroupProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 상품 매핑 저장
     */
    int saveProdMapping(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO);

    /**
     * 매핑프린터 조회
     */
    List<DefaultMap<String>> getPrinterMapping(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 프린터 조회
     */
    List<DefaultMap<String>> getPrinterList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 프린터 매핑 저장
     */
    int savePrinterMapping(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO);

    /** 매장-상품 탭 - 조회 */
    List<DefaultMap<Object>> getStoreProdKitchenprintLinkList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /** 매장-상품 탭 - 등록 상품 조회 */
    List<DefaultMap<Object>> getStoreProdKitchenprintLinkProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /** 매장-상품 탭 - 미등록 상품 조회 */
    List<DefaultMap<Object>> getStoreProdKitchenprintLinkNoProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);

    /** 매장-상품 탭 - 상품 저장 */
    int getStoreProdKitchenprintLinkSave(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO);

    /** 매장-상품 탭 - 전체등록 저장 */
    int getStoreProdKitchenprintLinkAllRegistSave(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO);
}