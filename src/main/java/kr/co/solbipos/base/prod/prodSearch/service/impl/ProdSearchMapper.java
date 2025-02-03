package kr.co.solbipos.base.prod.prodSearch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prodSearch.service.ProdSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdSearchMapper.java
 * @Description : 기초관리 - 상품관리2 - 상품정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13   권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdSearchMapper {

    /**
     * 상품 조회
     * @param prodSearchVO
     * @return List
     */
    List<DefaultMap<String>> getProdList(ProdSearchVO prodSearchVO);

    /**
     * 상품 조회(엑셀다운로드용)
     * @param prodSearchVO
     * @return List
     */
    List<DefaultMap<String>> getProdExcelList(ProdSearchVO prodSearchVO);

    /**
     * 상품 조회2
     * @param prodSearchVO
     * @return List
     */
    List<DefaultMap<String>> getProdList2(ProdSearchVO prodSearchVO);

    /**
     * 상품 조회2(엑셀다운로드용)
     * @param prodSearchVO
     * @return List
     */
    List<DefaultMap<String>> getProdExcelList2(ProdSearchVO prodSearchVO);
}