package kr.co.solbipos.base.prod.artiseeProdSpec.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.artiseeProdSpec.service.ArtiseeProdSpecVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ArtiseeProdSpecMapper.java
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
@Mapper
@Repository
public interface ArtiseeProdSpecMapper {

    /** 아티제-상품특성관리 - 조회 */
    List<DefaultMap<Object>> getArtiseeProdSpecList(ArtiseeProdSpecVO artiseeProdSpecVO);

    /** 아티제-상품특성관리 - 적용 상품 조회 */
    List<DefaultMap<Object>> getArtiseeProdSpecProdList(ArtiseeProdSpecVO artiseeProdSpecVO);

    /** 아티제-상품특성관리 - 미적용 상품 조회 */
    List<DefaultMap<Object>> getArtiseeProdSpecNoProdList(ArtiseeProdSpecVO artiseeProdSpecVO);

    /** 아티제-상품특성관리 - 상품 저장 */
    int getArtiseeProdSpecProdSaveInsert(ArtiseeProdSpecVO artiseeProdSpecVO);

    /** 아티제-상품특성관리 - 상품 삭제 */
    int getArtiseeProdSpecProdSaveDelete(ArtiseeProdSpecVO artiseeProdSpecVO);
}
