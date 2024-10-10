package kr.co.solbipos.base.prod.artiseeProdSpec.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ArtiseeProdSpecService.java
 * @Description : 보나비 > 상품관리 > 아티제-상품특성관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ArtiseeProdSpecService {

    /** 아티제-상품특성관리 - 조회 */
    List<DefaultMap<Object>> getArtiseeProdSpecList(ArtiseeProdSpecVO artiseeProdSpecVO, SessionInfoVO sessionInfoVO);

    /** 아티제-상품특성관리 - 적용 상품 조회 */
    List<DefaultMap<Object>> getArtiseeProdSpecProdList(ArtiseeProdSpecVO artiseeProdSpecVO, SessionInfoVO sessionInfoVO);

    /** 아티제-상품특성관리 - 미적용 상품 조회 */
    List<DefaultMap<Object>> getArtiseeProdSpecNoProdList(ArtiseeProdSpecVO artiseeProdSpecVO, SessionInfoVO sessionInfoVO);

    /** 아티제-상품특성관리 - 상품 저장 */
    int getArtiseeProdSpecProdSaveInsert(ArtiseeProdSpecVO[] artiseeProdSpecVOs, SessionInfoVO sessionInfoVO);

    /** 아티제-상품특성관리 - 상품 삭제 */
    int getArtiseeProdSpecProdSaveDelete(ArtiseeProdSpecVO[] artiseeProdSpecVOs, SessionInfoVO sessionInfoVO);
}