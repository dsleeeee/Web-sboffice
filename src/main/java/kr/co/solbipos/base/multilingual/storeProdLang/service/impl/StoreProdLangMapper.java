package kr.co.solbipos.base.multilingual.storeProdLang.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.multilingual.storeProdLang.service.StoreProdLangVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreProdLangMapper.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(상품)(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.25  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreProdLangMapper {

    /** 상품명 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreProdNmList(StoreProdLangVO storeProdLangVO);

    /** 상품명 영문, 중문, 일문 저장(매장) */
    int saveStoreProdNm(StoreProdLangVO storeProdLangVO);

    /** 상품설명 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreProdInfoList(StoreProdLangVO storeProdLangVO);

    /** 상품설명 영문, 중문, 일문 저장(매장) */
    int saveStoreProdInfo(StoreProdLangVO storeProdLangVO);
}
