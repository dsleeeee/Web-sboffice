package kr.co.solbipos.base.prod.prodImg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdImgMapper.java
 * @Description : 기초관리 - 상품관리 - 메뉴 순위 표시 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdImgMapper {

    /**  상품이미지관리 - 상품목록조회 */
    List<DefaultMap<String>> getProdList(ProdImgVO prodImgVO);

    /**  상품이미지관리 - 상품이미지조회 */
    List<DefaultMap<String>> getProdImg(ProdImgVO prodImgVO);
}
