package kr.co.solbipos.base.multilingual.prodLang.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.multilingual.prodLang.service.ProdLangVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdLangMapper.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(상품)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.12.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdLangMapper {

    /** 상품명 탭 리스트 조회 */
    List<DefaultMap<String>> getProdNmList(ProdLangVO prodLangVO);

    /** 상품명 영문, 중문, 일문 저장 */
    int saveProdNm(ProdLangVO prodLangVO);

    /** 상품설명 탭 리스트 조회 */
    List<DefaultMap<String>> getProdInfoList(ProdLangVO prodLangVO);

    /** 상품설명 영문, 중문, 일문 저장 */
    int saveProdInfo(ProdLangVO prodLangVO);
}
